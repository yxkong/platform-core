package com.github.platform.core.schedule.infra.configuration;

import com.github.platform.core.auth.util.LoginInfoUtil;
import com.github.platform.core.schedule.domain.common.entity.SysJobBase;
import com.github.platform.core.schedule.domain.constant.JobDataEnum;
import com.github.platform.core.schedule.infra.handler.JobHandlerExecutor;
import com.github.platform.core.standard.constant.StatusEnum;
import com.github.platform.core.standard.util.LocalDateTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;

import java.util.Date;
import java.util.Objects;

/**
 * job任务管理器
 * @author: yxkong
 * @date: 2022/6/24 9:59 AM
 * @version: 1.0
 */
@Slf4j
public class ScheduleManager {

    private final Scheduler scheduler;

    public ScheduleManager(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    private static final Object LOCK = new Object();

    /**
     * 添加或更新 Job，支持 SysJobBase 子类
     */
    public <T extends SysJobBase> void addOrUpdateJob(T job) throws SchedulerException {
        Date startAt = Objects.nonNull(job.getStartDate())
                ? LocalDateTimeUtil.localDateTimeToDate(job.getStartDate())
                : new Date();
        Date endAt = LocalDateTimeUtil.localDateTimeToDate(job.getEndDate());

        addOrUpdateJob(
                job.getId(),
                job.getName(),
                job.getBeanName(),
                job.getHandlerParam(),
                job.getCronExpression(),
                job.getStatus(),
                job.getRetryCount(),
                job.getRetryInterval(),
                startAt,
                endAt
        );
    }

    /**
     * 添加或更新 Quartz 中的 Job
     */
    public void addOrUpdateJob(Long id, String name, String handlerName, String handlerParam,
                               String cronExpression, Integer status, Integer retryCount,
                               Integer retryInterval, Date startAt, Date endAt) throws SchedulerException {

        TriggerKey triggerKey = getTriggerKey(handlerName);
        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

        if (trigger == null) {
            // 新增任务
            createJob(id, name, handlerName, handlerParam, cronExpression, retryCount, retryInterval, startAt, endAt, status);
        } else {
            // 更新任务
            updateTrigger(triggerKey, name, cronExpression, handlerParam, retryCount, retryInterval, startAt, endAt);
            log.warn("更新定时任务：{} cron: {}", handlerName, cronExpression);
        }
    }

    /**
     * 创建任务
     */
    private void createJob(Long id, String name, String handlerName, String handlerParam,
                           String cronExpression, Integer retryCount, Integer retryInterval,
                           Date startAt, Date endAt, Integer status) throws SchedulerException {

        // 构建 Trigger
        CronTrigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(handlerName)
                .withDescription(name)
                .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression).withMisfireHandlingInstructionDoNothing())
                .usingJobData(JobDataEnum.HANDLER_PARAM.getKey(), handlerParam)
                .usingJobData(JobDataEnum.RETRY_COUNT.getKey(), retryCount)
                .usingJobData(JobDataEnum.RETRY_INTERVAL.getKey(), retryInterval)
                .startAt(startAt)
                .endAt(endAt)
                .build();

        // 构建 JobDetail
        JobDetail jobDetail = JobBuilder.newJob(JobHandlerExecutor.class)
                .withIdentity(handlerName)
                .withDescription(name)
                .usingJobData(JobDataEnum.ID.getKey(), id)
                .usingJobData(JobDataEnum.HANDLER_NAME.getKey(), handlerName)
                .build();

        // 添加任务到调度器
        scheduler.scheduleJob(jobDetail, trigger);
        if (StatusEnum.OFF.getStatus().equals(status)) {
            scheduler.pauseJob(jobDetail.getKey());
        }
        log.warn("添加定时任务：{} cron: {}", handlerName, cronExpression);
    }

    /**
     * 更新 Trigger
     */
    private void updateTrigger(TriggerKey triggerKey, String name, String cronExpression,
                               String handlerParam, Integer retryCount, Integer retryInterval,
                               Date startAt, Date endAt) throws SchedulerException {

        CronTrigger newTrigger = TriggerBuilder.newTrigger()
                .withIdentity(triggerKey)
                .withDescription(name)
                .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression).withMisfireHandlingInstructionDoNothing())
                .usingJobData(JobDataEnum.HANDLER_PARAM.getKey(), handlerParam)
                .usingJobData(JobDataEnum.RETRY_COUNT.getKey(), retryCount)
                .usingJobData(JobDataEnum.RETRY_INTERVAL.getKey(), retryInterval)
                .startAt(startAt)
                .endAt(endAt)
                .build();

        scheduler.rescheduleJob(triggerKey, newTrigger);
    }

    /**
     * 立即触发任务
     */
    public Boolean isNone(Trigger.TriggerState state){
        if (Trigger.TriggerState.NONE == state){
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    /**
     * 正常运行
     * @param state
     * @return
     */
    public Boolean isRun(Trigger.TriggerState state){
        if (Trigger.TriggerState.NORMAL == state || Trigger.TriggerState.COMPLETE == state){
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public void deleteJob(String handlerName) throws SchedulerException {
        if (isExist(handlerName)){
            scheduler.deleteJob(getJobKey(handlerName));
        }
    }
    private JobKey getJobKey(String handlerName){
        return new JobKey(handlerName);
    }
    /**
     * 暂停定时任务
     * @param handlerName
     * @throws SchedulerException
     */
    public void pauseJob(String handlerName) throws SchedulerException {
        scheduler.pauseJob(getJobKey(handlerName));
    }
    /**
     * 启动 quartz中的job
     * @param handlerName
     * @throws SchedulerException
     */
    public void resumeJob(String handlerName) throws SchedulerException {
        scheduler.resumeJob(getJobKey(handlerName));
        scheduler.resumeTrigger(new TriggerKey(handlerName));
    }

    /**
     * 立即触发一次任务
     * @param id  任务id
     * @param handlerName 处理器的名称
     * @param handlerParam 处理器的参数
     * @throws SchedulerException
     */
    public void triggerJob(Long id, String handlerName, String handlerParam,String executeUser,String executeId,String cronExpression, Integer retryCount, Integer retryInterval)
            throws SchedulerException {

        JobDataMap data = new JobDataMap();
        data.put(JobDataEnum.ID.getKey(), id);
        data.put(JobDataEnum.HANDLER_NAME.getKey(), handlerName);
        data.put(JobDataEnum.HANDLER_PARAM.getKey(), handlerParam);
        data.put(JobDataEnum.EXECUTE_USER.getKey(), executeUser);
        data.put(JobDataEnum.EXECUTE_ID.getKey(), executeId);

        if (!isExist(handlerName)) {
            log.warn("任务 [{}] 不存在，创建新任务", handlerName);
            createJob(id, handlerName, handlerName, handlerParam, cronExpression, retryCount, retryInterval, new Date(), null, StatusEnum.ON.getStatus());
        }
        scheduler.triggerJob(getJobKey(handlerName), data);
    }

    /**
     * 判断任务是否存在
     */
    private boolean isExist(String handlerName) throws SchedulerException {
        return scheduler.checkExists(getTriggerKey(handlerName));
    }

    /**
     * 封装 TriggerKey 生成
     */
    private TriggerKey getTriggerKey(String handlerName) {
        return TriggerKey.triggerKey(handlerName);
    }


    /**
     * 获取任务状态
     */
    public String taskState(String handlerName) {
        try {
            return scheduler.getTriggerState(getTriggerKey(handlerName)).name();
        } catch (SchedulerException e) {
            log.error("获取任务状态异常", e);
        }
        return null;
    }
}

