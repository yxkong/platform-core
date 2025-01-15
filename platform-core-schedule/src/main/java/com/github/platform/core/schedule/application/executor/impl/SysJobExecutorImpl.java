package com.github.platform.core.schedule.application.executor.impl;

import com.github.platform.core.auth.application.executor.SysExecutor;
import com.github.platform.core.auth.util.LoginInfoUtil;
import com.github.platform.core.common.service.IPublishService;
import com.github.platform.core.schedule.application.constant.JobApplicationEnum;
import com.github.platform.core.schedule.application.executor.ISysJobExecutor;
import com.github.platform.core.schedule.domain.constant.JobStatusEnum;
import com.github.platform.core.schedule.domain.context.SysJobContext;
import com.github.platform.core.schedule.domain.context.SysJobQueryContext;
import com.github.platform.core.schedule.domain.dto.SysJobDto;
import com.github.platform.core.schedule.domain.gateway.ISysJobGateway;
import com.github.platform.core.schedule.infra.configuration.ScheduleManager;
import com.github.platform.core.schedule.infra.util.CronUtil;
import com.github.platform.core.standard.constant.ResultStatusEnum;
import com.github.platform.core.standard.constant.StatusEnum;
import com.github.platform.core.standard.constant.SymbolConstant;
import com.github.platform.core.standard.entity.dto.PageBean;
import com.github.platform.core.standard.exception.ApplicationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Objects;

/**
* 任务管理执行器
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-09-05 11:37:59.627
* @version 1.0
*/
@Service
@Slf4j
public class SysJobExecutorImpl extends SysExecutor implements ISysJobExecutor {
    @Resource
    private ISysJobGateway gateway;
    @Autowired(required = false)
    private ScheduleManager scheduleManager;
    @Resource
    private IPublishService publishService;
    @Override
    public PageBean<SysJobDto> query(SysJobQueryContext context){
        context.setTenantId(getTenantId(context));
        return gateway.query(context);
    };
    @Override
    public SysJobDto findById(Long id) {
        return gateway.findById(id);
    }

    private void vlidateSchedule(){
        if (Objects.isNull(scheduleManager)){
            throw exception(JobApplicationEnum.QUARTZ_IS_DISABLED);
        }
    }
    @Override
    public void delete(Long id) throws SchedulerException {
        vlidateSchedule();
        SysJobDto jobDto = validateJobExist(id);
        // 删除 Job 到 Quartz 中
        scheduleManager.deleteJob(jobDto.getName());
        int delete = gateway.delete(id);
        if (delete <=0 ){
            throw exception(ResultStatusEnum.COMMON_DELETE_ERROR);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addJob(SysJobContext context) {
        //TODO  需要注意使用的quartz 是数据库集群，如果单机，并且有多节点
        vlidateSchedule();
        if (context.isCallBack()){
            //先临时保存，添加任务成功以后更新
            context.setBeanName("callBackUrlJobHandler");
        }
        validateCronExpression(context.getCronExpression());
        validateJobUnique(context);
        context.setTenantId(getTenantId(context));
        SysJobDto sysJobDto = gateway.insert(context);
        if (Objects.isNull(sysJobDto.getId())){
            throw exception(ResultStatusEnum.COMMON_INSERT_ERROR);
        }
        // 多实例更新beanName
        if (context.isMultiInstance()){
            if (context.isCallBack()){
                sysJobDto.setBeanName("callBackUrlJobHandler:"+sysJobDto.getId());
            } else {
                sysJobDto.setBeanName(sysJobDto.getBeanName() + SymbolConstant.colon + sysJobDto.getId());
            }
            try {
                scheduleManager.addOrUpdateJob(sysJobDto);
            } catch (SchedulerException e) {
                throw exception(JobApplicationEnum.ADD_ERROR);
            }
        }
        SysJobDto updateJob = SysJobDto.builder().id(sysJobDto.getId()).jobStatus(JobStatusEnum.NORMAL.getStatus()).beanName(sysJobDto.getBeanName()).build();
        boolean update = gateway.update(updateJob);
        if (!update){
            throw exception(ResultStatusEnum.COMMON_UPDATE_ERROR);
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class,IllegalArgumentException.class})
    public void updateJob(SysJobContext context) throws SchedulerException, ApplicationException {
        vlidateSchedule();
        validateCronExpression(context.getCronExpression());
        validateJobExist(context.getId());
        validateJobUnique(context);
        context.setTenantId(getTenantId(context));
        Pair<Boolean, SysJobDto> update = gateway.update(context);
        if (!update.getKey()){
            throw exception(ResultStatusEnum.COMMON_UPDATE_ERROR);
        }
        //订阅与发布
        scheduleManager.addOrUpdateJob(update.getRight());
    }
    @Override
    public void triggerJob(Long id) throws SchedulerException {
        vlidateSchedule();
        SysJobDto jobDto = validateJobExist(id);
        scheduleManager.triggerJob(id,jobDto.getBeanName(),jobDto.getHandlerParam(), LoginInfoUtil.getLoginName(),null,jobDto.getCronExpression(),jobDto.getRetryCount(), jobDto.getRetryInterval());
    }
    private void validateCronExpression(String cronExpression) {
        if (!CronUtil.isValid(cronExpression)) {
            throw exception(JobApplicationEnum.CRON_VALIDATE);
        }
    }

    /**
     * job 唯一性校验
     * @param context
     */
    private void validateJobUnique(SysJobContext context) {
        SysJobDto jobDto = gateway.jobUnique(context);
        if (Objects.nonNull(jobDto)){
            throw exception(JobApplicationEnum.CRON_UNIQUE);
        }
    }
    private SysJobDto validateJobExist(Long id){
        SysJobDto jobDto = gateway.findById(id);
        if (Objects.isNull(jobDto)){
            throw exception(JobApplicationEnum.JOB_NOT_EXIST);
        }
        return jobDto;
    }

    @Override
    public void pauseJob(Long id)throws SchedulerException {
        vlidateSchedule();
        SysJobDto jobDto = validateJobExist(id);
        scheduleManager.pauseJob(jobDto.getBeanName());
        SysJobContext context = SysJobContext.builder().id(id).status(StatusEnum.OFF.getStatus()).build();
        gateway.update(context);
    }

    @Override
    public void resumeJob(Long id)throws SchedulerException {
        vlidateSchedule();
        SysJobDto jobDto = validateJobExist(id);
        scheduleManager.resumeJob(jobDto.getBeanName());
        SysJobContext context = SysJobContext.builder().id(id).status(StatusEnum.ON.getStatus()).build();
        gateway.update(context);
    }
}
