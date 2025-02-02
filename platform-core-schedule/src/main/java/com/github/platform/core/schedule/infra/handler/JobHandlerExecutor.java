package com.github.platform.core.schedule.infra.handler;

import com.github.platform.core.common.utils.ApplicationContextHolder;
import com.github.platform.core.common.utils.StringUtils;
import com.github.platform.core.schedule.domain.constant.JobConstant;
import com.github.platform.core.schedule.domain.constant.JobDataEnum;
import com.github.platform.core.schedule.domain.context.SysJobLogContext;
import com.github.platform.core.schedule.domain.dto.SysJobDto;
import com.github.platform.core.schedule.domain.dto.SysJobLogDto;
import com.github.platform.core.schedule.domain.gateway.ISysJobGateway;
import com.github.platform.core.schedule.domain.gateway.ISysJobLogGateway;
import com.github.platform.core.schedule.infra.configuration.ScheduleManager;
import com.github.platform.core.standard.constant.StatusEnum;
import com.github.platform.core.standard.constant.SymbolConstant;
import com.github.platform.core.standard.util.ExceptionUtil;
import com.github.platform.core.standard.util.LocalDateTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.quartz.*;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.time.format.DateTimeFormatter;

/**
 * 任务执行器 IJobMonitorHandler 的执行器
 *
 * @DisallowConcurrentExecution 禁止并行执行同一个JobDetail
 * @PersistJobDataAfterExecution 表示当正常执行完Job后, JobDataMap中的数据应该被改动, 以被下一次调用时用
 * @author: yxkong
 * @date: 2023/8/21 7:18 PM
 * @version: 1.0
 */
@DisallowConcurrentExecution
@PersistJobDataAfterExecution
@Slf4j
public class JobHandlerExecutor extends QuartzJobBean {
    private static final String PARENT = "parent";

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        ISysJobLogGateway sysJobLogGateway = ApplicationContextHolder.getBean(ISysJobLogGateway.class);
        ISysJobGateway sysJobGateway = ApplicationContextHolder.getBean(ISysJobGateway.class);

        Long jobId = context.getMergedJobDataMap().getLong(JobDataEnum.ID.getKey());
        String executeUser = context.getMergedJobDataMap().getString(JobDataEnum.EXECUTE_USER.getKey());
        executeUser = StringUtils.isEmpty(executeUser) ? JobConstant.DEFAULT_USER : executeUser;

        SysJobDto jobDto = sysJobGateway.findById(jobId);
        if (!isJobExecutable(jobDto)) return;

        String executeId = getExecuteId(jobDto.getId(), context);
        Long logId = createExecutionLog(sysJobLogGateway, jobDto, executeUser, executeId, context.getRefireCount());

        Long startTime = System.currentTimeMillis();
        Pair<Boolean, String> executionResult = null;
        Exception executionException = null;

        try {
            IJobMonitorHandler jobHandler = resolveJobHandler(jobDto);
            executionResult = jobHandler.execute(jobDto);
        } catch (Exception e) {
            executionException = e;
        }

        updateLogResultAsync(sysJobLogGateway, logId, jobDto.getBeanName(), executionException, executionResult, executeUser, startTime);
        handleException(executionException, context.getRefireCount(), jobDto.getRetryCount(), jobDto.getRetryInterval());
        handleSubTasks(sysJobGateway, jobDto.getSubJobIds(), executionResult, executeUser, executeId);
    }

    private boolean isJobExecutable(SysJobDto jobDto) {
        if (!StatusEnum.ON.getStatus().equals(jobDto.getStatus())) {
            log.warn("任务已禁用: jobId={}, handlerName={}", jobDto.getId(), jobDto.getBeanName());
            return false;
        }

        boolean isInExecutionPeriod = LocalDateTimeUtil.isBetween(jobDto.getStartDate(), jobDto.getEndDate());
        if (!isInExecutionPeriod) {
            log.warn("任务不在有效时间范围内: jobId={}, handlerName={}, startDate={}, endDate={}",
                    jobDto.getId(), jobDto.getBeanName(), jobDto.getStartDate(), jobDto.getEndDate());
            return false;
        }

        return true;
    }

    private IJobMonitorHandler resolveJobHandler(SysJobDto jobDto) {
        if (jobDto.isCallBack()) {
            return ApplicationContextHolder.getBean(CallBackUrlJobHandler.class);
        }
        String beanName = jobDto.getBeanName();
        if (jobDto.isMultiInstance() && beanName.contains(SymbolConstant.colon)){
            beanName = beanName.substring(0, beanName.indexOf(SymbolConstant.colon));
        }
        return ApplicationContextHolder.getBean(beanName, IJobMonitorHandler.class);
    }

    private Long createExecutionLog(ISysJobLogGateway sysJobLogGateway, SysJobDto jobDto, String executeUser, String executeId, int refireCount) {
        try {
            SysJobLogDto logDto = sysJobLogGateway.insert(
                    SysJobLogContext.builder()
                            .jobId(jobDto.getId())
                            .beanName(jobDto.getBeanName())
                            .handlerParam(jobDto.getHandlerParam())
                            .executeId(executeId)
                            .executeNum(refireCount + 1)
                            .tenantId(jobDto.getTenantId())
                            .createBy(executeUser)
                            .startTime(LocalDateTimeUtil.dateTime())
                            .createTime(LocalDateTimeUtil.dateTime())
                            .build()
            );
            return logDto.getId();
        } catch (Exception e) {
            log.error("记录任务日志失败: jobName={}", jobDto.getBeanName(), e);
            return null;
        }
    }

    private void handleSubTasks(ISysJobGateway sysJobGateway, String subJobIds, Pair<Boolean, String> result, String executeUser, String executeId) {
        if (StringUtils.isEmpty(subJobIds) || !result.getKey()) {
            return;
        }
        log.warn("触发执行子任务: subJobIds={}, ", subJobIds);
        String[] ids = subJobIds.split(SymbolConstant.comma);
        executeUser = StringUtils.isNotEmpty(executeUser) && !executeUser.contains(JobConstant.PARENT)
                ? executeUser + SymbolConstant.colon + JobConstant.PARENT
                : executeUser;

        for (String id : ids) {
            try {
                SysJobDto subJob = sysJobGateway.findById(Long.valueOf(id));
                if (subJob != null) {
                    ScheduleManager scheduleManager = ApplicationContextHolder.getBean(ScheduleManager.class);
                    scheduleManager.triggerJob(subJob.getId(), subJob.getBeanName(), subJob.getHandlerParam(), executeUser, executeId,subJob.getCronExpression(),subJob.getRetryCount(),subJob.getRetryInterval());
                }
            } catch (SchedulerException e) {
                log.error("子任务触发失败: id={}", id, e);
            }
        }
    }

    private void handleException(Exception exception, int refireCount, int retryCount, int retryInterval) throws JobExecutionException {
        if (exception == null) return;

        if (refireCount >= retryCount) {
            throw new JobExecutionException(exception);
        }

        if (retryInterval > 0) {
            try {
                Thread.sleep(retryInterval);
            } catch (InterruptedException ignored) {
            }
        }

        throw new JobExecutionException(exception, true);
    }

    private static String getExecuteId(Long jobId, JobExecutionContext context) {
        String executeId = context.getMergedJobDataMap().getString(JobDataEnum.EXECUTE_ID.getKey());
        if (StringUtils.isEmpty(executeId)) {
            executeId = jobId + SymbolConstant.colon + LocalDateTimeUtil.dateTime(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        }

        if (context.getRefireCount() == 0) {
            context.getMergedJobDataMap().put(JobDataEnum.EXECUTE_ID.getKey(), executeId);
        }

        return executeId;
    }

    private void updateLogResultAsync(ISysJobLogGateway sysJobLogGateway, Long logId, String handlerName, Exception exception, Pair<Boolean, String> result, String executeUser, Long startTime) {
        if (logId == null) return;

        try {
            int status = (exception == null && result != null && result.getKey()) ? 1 : 0;
            String resultMessage = (exception != null) ? ExceptionUtil.getMessage(exception) : (result != null ? result.getValue() : "未知结果");

            SysJobLogContext context = SysJobLogContext.builder()
                    .id(logId)
                    .status(status)
                    .result(resultMessage)
                    .executeTime((int) (System.currentTimeMillis() - startTime))
                    .endDate(LocalDateTimeUtil.dateTime())
                    .updateBy(executeUser)
                    .updateTime(LocalDateTimeUtil.dateTime())
                    .build();

            sysJobLogGateway.updateAsync(context);
        } catch (Exception e) {
            log.error("更新任务日志失败: handlerName={}, logId={}", handlerName, logId, e);
        }
    }
}

