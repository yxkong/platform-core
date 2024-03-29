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
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

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
        //获取执行任务的相关数据
        Long jobId = context.getMergedJobDataMap().getLong(JobDataEnum.ID.getKey());
        SysJobDto jobDto = sysJobGateway.findById(jobId);
        if (!StatusEnum.ON.getStatus().equals(jobDto.getStatus())){
            if (log.isWarnEnabled()){
                log.warn("jobId:{} handlerName:{} 已经禁用",jobId,jobDto.getBeanName());
            }
            return;
        }
        boolean between = LocalDateTimeUtil.isBetween(jobDto.getStartDate(), jobDto.getEndDate());
        if (!between){
            if (log.isWarnEnabled()){
                log.warn("jobId:{} handlerName:{} 不在执行时间 startDate:{}  和  endDate:{} 之间",jobId,jobDto.getHandlerParam(),jobDto.getStartDate(),jobDto.getEndDate());
            }
            return;
        }
        String executeUser = context.getMergedJobDataMap().getString(JobDataEnum.EXECUTE_USER.getKey());
        //执行id，每次都会更新,到秒级
        String executeId = getExecuteId(jobId,context);
        Long logId = null;
        Pair<Boolean, String> pair = null;
        Exception exception = null;
        if (StringUtils.isEmpty(executeUser)){
            executeUser = JobConstant.DEFAULT_USER;
        }
        try {
            /**日志生成失败，不影响业务*/
            SysJobLogDto jobLogDto = sysJobLogGateway.insert(buildLogContext(jobId,jobDto.getBeanName(), jobDto.getHandlerParam(), executeUser,executeId, context.getRefireCount()));
            logId = jobLogDto.getId();
        }catch (Exception e){
            log.error("任务:{} 插入执行日志失败",jobDto.getBeanName(),e);
        }
        Long  start  = System.currentTimeMillis();
        try {
            IJobMonitorHandler scheduleHandler = null;
            if (jobDto.isCallBack()){
                scheduleHandler =  ApplicationContextHolder.getBean(CallBackUrlJobHandler.class);
            } else {
                scheduleHandler = ApplicationContextHolder.getBean(jobDto.getBeanName(), IJobMonitorHandler.class);
            }
            Assert.notNull(scheduleHandler, jobDto.getBeanName()+" scheduleHandler 为空，请排查是否实现");
            pair = scheduleHandler.execute(jobDto);
        } catch (Exception e){
            exception = e;
        }
        updateLogResultAsync(sysJobLogGateway,logId,jobDto.getBeanName(), exception, pair,executeUser, start);
        handlerException(exception, context.getRefireCount(), jobDto.getRetryCount(), jobDto.getRetryInterval());
        handlerSubTask(sysJobGateway,jobDto.getSubJobIds(),pair, executeUser,executeId);

    }

    /**
     * 处理子任务
     * @param sysJobGateway
     * @param subJobIds
     * @param pair
     * @param executeUser
     * @param executeId
     */
    private Pair<Boolean,String> handlerSubTask(ISysJobGateway sysJobGateway,String subJobIds, Pair<Boolean, String> pair, String executeUser, String executeId) {
        if (StringUtils.isEmpty(subJobIds) ){
            return Pair.of(false,"无子任务");
        }
        if (!pair.getKey()){
            return Pair.of(false,"父任务执行失败");
        }
        String[] ids = subJobIds.split(SymbolConstant.comma);
        if (StringUtils.isNotEmpty(executeUser) && !executeUser.contains(JobConstant.PARENT)){
            executeUser = executeUser+SymbolConstant.colon+JobConstant.PARENT;
        }
        for (String strId:ids){
            try {
                // TODO  父子任务是否需依赖执行结果，目前只是驱动执行，不做其他的处理，父子任务的executeId相同
                SysJobDto jobDto = sysJobGateway.findById(Long.valueOf(strId));
                if (Objects.isNull(jobDto)){
                    continue;
                }
                ScheduleManager scheduleManager = ApplicationContextHolder.getBean(ScheduleManager.class);
                /**触发任务*/
                scheduleManager.triggerJob(jobDto.getId(),jobDto.getBeanName(),jobDto.getHandlerParam(),executeUser,executeId);
            } catch (SchedulerException e) {
            }
        }
        return Pair.of(true,"");
    }
    private static String getExecuteId(Long jobId,JobExecutionContext context) {
        String executeId = context.getMergedJobDataMap().getString(JobDataEnum.EXECUTE_ID.getKey());
        if (StringUtils.isEmpty(executeId)){
            executeId = jobId + SymbolConstant.colon +LocalDateTimeUtil.dateTime(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        }
        // 第一次执行，将ExecuteId 放入上下文
        int refireCount  = context.getRefireCount();
        if (refireCount == 0){
            context.getMergedJobDataMap().put(JobDataEnum.EXECUTE_ID.getKey(),executeId);
        }
        return executeId;
    }

    /**
     * 异常处理，抛JobExecutionException 异常，会重试
     * @param exception
     * @param refireCount
     * @param retryCount
     * @param retryInterval
     * @throws JobExecutionException
     */
    private static void handlerException(Exception exception, int refireCount, int retryCount, int retryInterval) throws JobExecutionException {
        // 情况一：没有异常，直接中断
        if (exception == null) {
            return;
        }
        // 情况二：如果到达重试上限，则直接抛出异常即可
        if (refireCount >= retryCount) {
            throw new JobExecutionException(exception);
        }
        // 情况二：如果未到达重试上限，则 sleep 一定间隔时间，然后重试
        // 这里使用 sleep 来实现，主要还是希望实现比较简单。因为，同一时间，不会存在大量失败的 Job。
        if (retryInterval > 0) {
            try {
                Thread.sleep(retryInterval);
            } catch (InterruptedException e) {
            }
        }
        // 第二个参数，refireImmediately = true，表示立即重试
        throw new JobExecutionException(exception, true);
    }

    private void updateLogResultAsync(ISysJobLogGateway sysJobLogGateway,Long logId,String handlerName, Exception exception, Pair<Boolean, String> pair,String executeUser, Long start) {
        int status = 0;
        String result = null;
        try {
            if (Objects.isNull(logId)){
                return;
            }
            if (Objects.nonNull(exception)){
                result = ExceptionUtil.getMessage(exception);
            } else {
                status = pair.getKey() ? 1 : 0;
                result = pair.getValue();
            }
            Long duration = System.currentTimeMillis()- start;
            LocalDateTime dateTime = LocalDateTimeUtil.dateTime();
            SysJobLogContext context = SysJobLogContext.builder()
                    .id(logId).status(status)
                    .result(result)
                    .executeTime(duration.intValue()).endDate(dateTime)
                    .updateBy(executeUser).updateTime(dateTime)
                    .build();
            sysJobLogGateway.updateAsync(context);
        } catch (Exception e) {
            log.error("handlerName:{} 更新日志失败({}/{})！",handlerName,start,result,e);
        }
    }

    /**
     * 构建执行日志上下文
     * @param jobId
     * @param beanName
     * @param handlerParam
     * @param executeUser
     * @param executeId
     * @param refireCount
     * @return
     */
    private SysJobLogContext buildLogContext(Long jobId , String beanName, String handlerParam,String executeUser,String executeId, int refireCount){
        return SysJobLogContext.builder()
                .jobId(jobId)
                .beanName(beanName)
                .executeId(executeId)
                .handlerParam(handlerParam)
                .executeNum(refireCount+1)
                .createBy(executeUser)
                .startTime(LocalDateTimeUtil.dateTime())
                .createTime(LocalDateTimeUtil.dateTime())
                .build();
    }

}
