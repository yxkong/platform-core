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

    private Scheduler scheduler;

    public ScheduleManager(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    private static volatile Object lock = new Object();

    /**
     * 添加或更新job，使用SysJobBase及其子类作为入参
     * @param job
     * @param <T>
     * @throws SchedulerException
     */
    public <T extends SysJobBase> void addOrUpdateJob(T job) throws SchedulerException {
        Date startDate = new Date();
        if (Objects.nonNull(job.getStartDate())){
            startDate = LocalDateTimeUtil.localDateTimeToDate(job.getStartDate());
        }
        Date endDate = LocalDateTimeUtil.localDateTimeToDate(job.getEndDate());
        this.addOrUpdateJob(job.getId(),job.getName(),job.getBeanName(),job.getHandlerParam(),job.getCronExpression(),job.getStatus(), job.getRetryCount(), job.getRetryInterval(),startDate,endDate);
    }

    /**
     * 添加或更新 quartz中的job
     * @param id 任务id
     * @param name  任务名称
     * @param handlerName 处理器bean名称
     * @param handlerParam 处理器的参数
     * @param cronExpression 表达式
     * @param status 状态
     * @param retryCount 重试次数
     * @param retryInterval 重试间隔
     * @param startAt 任务执行的开始时间
     * @param endAt 任务执行的结束时间
     * @throws SchedulerException
     */
    public void addOrUpdateJob(Long id, String name, String handlerName, String handlerParam, String cronExpression ,Integer status, Integer retryCount, Integer retryInterval, Date startAt,Date endAt) throws SchedulerException {
        /**判断任务是否在schedule中*/
        TriggerKey triggerKey = new TriggerKey(handlerName);
        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        if (Objects.isNull(trigger)){
            /**新增任务，Trigger 对象
             *  添加 .withMisfireHandlingInstructionDoNothing() 表示不执行之前漏掉的任务，直接执行下一个任务。需要配置 org.quartz.jobStore.misfireThreshold = 1000 大于10000 不起作用
             * */
            trigger = TriggerBuilder.newTrigger()
                    .withIdentity(handlerName)
                    .withDescription(name)
                    .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression).withMisfireHandlingInstructionDoNothing())
                    .usingJobData(JobDataEnum.HANDLER_PARAM.getKey(), handlerParam)
                    .usingJobData(JobDataEnum.RETRY_COUNT.getKey(), retryCount)
                    .usingJobData(JobDataEnum.RETRY_INTERVAL.getKey(), retryInterval)
                    .startAt(startAt)
                    .endAt(endAt)
                    .build();

            /** 构建JobDetail对象*/
            JobDetail jobDetail = JobBuilder.newJob(JobHandlerExecutor.class)
                    .usingJobData(JobDataEnum.ID.getKey(), id)
                    .usingJobData(JobDataEnum.HANDLER_NAME.getKey(), handlerName)
                    .withIdentity(handlerName)
                    .withDescription(name)
                    .build();
            /**新增任务*/
            scheduler.scheduleJob(jobDetail,trigger);
            if (StatusEnum.OFF.getStatus().equals(status)){
                // 暂停任务
                scheduler.pauseJob(jobDetail.getKey());
            }
            if (log.isWarnEnabled()){
                log.warn("添加定时任务：{}  cron:{}",handlerName,cronExpression);
            }
        } else {
            trigger = trigger.getTriggerBuilder()
                    .withIdentity(triggerKey)
                    .withDescription(name)
                    .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression).withMisfireHandlingInstructionDoNothing())
                    .usingJobData(JobDataEnum.HANDLER_PARAM.getKey(), handlerParam)
                    .usingJobData(JobDataEnum.RETRY_COUNT.getKey(), retryCount)
                    .usingJobData(JobDataEnum.RETRY_INTERVAL.getKey(), retryInterval)
                    .startAt(startAt)
                    .endAt(endAt)
                    .build();
            /**更新任务*/
            scheduler.rescheduleJob(triggerKey,trigger);
            if (log.isWarnEnabled()){
                log.warn("更新定时任务：{}  cron:{}",handlerName,cronExpression);
            }
        }
    }


    /**
     * 判断任务是否存在
     * @param handlerName
     * @return
     * @throws SchedulerException
     */
    private Boolean isExist(String handlerName) throws SchedulerException {
        return Objects.nonNull(scheduler.getTrigger(new TriggerKey(handlerName)));
    }

    /**
     * 返回任务的状态
     * @param jobClass
     * @param jobGroupName
     * @return
     *      NONE：Trigger已经完成，且不会在执行，或者找不到该触发器，或者Trigger已经被删除
            NORMAL:正常状态
            PAUSED：暂停状态
            COMPLETE：触发器完成，但是任务可能还正在执行中
            BLOCKED：线程阻塞状态
            ERROR：出现错误
     */
    public Trigger.TriggerState taskState(String jobClass,String jobGroupName){
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(jobClass, jobGroupName);
            return scheduler.getTriggerState(triggerKey);
        } catch (SchedulerException e) {
            log.error("返回任务状态异常",e);
        }
        return null;
    }

    public String taskState(String handlerName){
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(handlerName);
            return scheduler.getTriggerState(triggerKey).name();
        } catch (SchedulerException e) {
            log.error("返回任务状态异常",e);
        }
        return null;
    }
    /**
     * 存在，不在执行
     * @param state
     * @return
     */
    public Boolean isException(Trigger.TriggerState state){
        if (Trigger.TriggerState.PAUSED == state || Trigger.TriggerState.BLOCKED == state
                || Trigger.TriggerState.ERROR == state){
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    /**
     * 为空
     * @param state
     * @return
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
    public void triggerJob(Long id, String handlerName, String handlerParam)
            throws SchedulerException {
        this.triggerJob(id,handlerName,handlerParam,LoginInfoUtil.getLoginName(),null);
    }
    public void triggerJob(Long id, String handlerName, String handlerParam,String executeUser,String executeId)
            throws SchedulerException {
        // 无需重试，所以不设置 retryCount 和 retryInterval
        JobDataMap data = new JobDataMap();
        data.put(JobDataEnum.ID.getKey(), id);
        data.put(JobDataEnum.HANDLER_NAME.getKey(), handlerName);
        data.put(JobDataEnum.HANDLER_PARAM.getKey(), handlerParam);
        data.put(JobDataEnum.EXECUTE_USER.getKey(), executeUser);
        data.put(JobDataEnum.EXECUTE_ID.getKey(), executeId);
        /**触发任务*/
        scheduler.triggerJob(getJobKey(handlerName), data);
    }

}
