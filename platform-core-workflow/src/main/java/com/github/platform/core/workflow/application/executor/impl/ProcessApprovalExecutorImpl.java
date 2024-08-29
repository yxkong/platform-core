package com.github.platform.core.workflow.application.executor.impl;

import com.github.platform.core.auth.util.LoginUserInfoUtil;
import com.github.platform.core.cache.infra.utils.SequenceUtil;
import com.github.platform.core.common.service.BaseExecutor;
import com.github.platform.core.common.utils.ApplicationContextHolder;
import com.github.platform.core.common.utils.StringUtils;
import com.github.platform.core.standard.constant.SymbolConstant;
import com.github.platform.core.standard.util.LocalDateTimeUtil;
import com.github.platform.core.workflow.application.constant.WorkflowApplicationEnum;
import com.github.platform.core.workflow.application.executor.IFormDataExecutor;
import com.github.platform.core.workflow.application.executor.IProcessApprovalExecutor;
import com.github.platform.core.workflow.domain.constant.ProcessOptTypeEnum;
import com.github.platform.core.workflow.domain.constant.WorkFlowSequenceEnum;
import com.github.platform.core.workflow.domain.context.ApprovalContext;
import com.github.platform.core.workflow.domain.context.ProcessApprovalRecordContext;
import com.github.platform.core.workflow.domain.dto.ProcessApprovalRecordDto;
import com.github.platform.core.workflow.domain.dto.ProcessInstanceDto;
import com.github.platform.core.workflow.domain.gateway.*;
import com.github.platform.core.workflow.infra.service.IProcessInstanceService;
import com.github.platform.core.workflow.infra.service.IProcessTaskService;
import lombok.extern.slf4j.Slf4j;
import org.flowable.identitylink.api.IdentityLinkInfo;
import org.flowable.task.api.Task;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * 流程审批执行器
 * @author: yxkong
 * @date: 2023/11/10 14:02
 * @version: 1.0
 */
@Service
@Slf4j
public class ProcessApprovalExecutorImpl extends BaseExecutor implements IProcessApprovalExecutor {

    @Resource
    private IProcessInstanceGateway instanceGateway;
    @Resource
    private IProcessApprovalRecordGateway approvalRecordGateway;
    @Resource
    private IFormDataExecutor formDataExecutor;
    @Resource(name="flowableProcessInstanceService")
    private IProcessInstanceService processInstanceService;

    @Resource(name="flowableProcessTaskService")
    private IProcessTaskService processTaskService;

    @Override
    public void submit(ProcessOptTypeEnum optType,String bizNo, String taskKey){
        ProcessInstanceDto dto = instanceGateway.findByBizNoAndProcessNo(bizNo, null);
        if (Objects.isNull(dto)){
            throw exception(WorkflowApplicationEnum.PROCESS_INSTANCE_NOT_EXIST);
        }
        // 适用于 一人通过就通过的场景
        Task task = processTaskService.getTaskByInstanceIdAndTaskKey(dto.getInstanceId(), taskKey);
        if (Objects.isNull(task)){
            throw exception(WorkflowApplicationEnum.PROCESS_TASK_NOT_EXIST);
        }
        ApprovalContext context = ApprovalContext.builder()
                .optTypeEnum(optType)
                .instanceId(dto.getInstanceId())
                .taskId(task.getId())
                .instanceNo(dto.getInstanceNo())
                .processInstanceDto(dto)
                .task(task)
                .build();
        submit(context);
    }
    @Override
    public void submit(ApprovalContext context) {
        /** ①校验任务类型是否实现*/
        ProcessOptTypeEnum optType = context.getOptTypeEnum();
        IProcessApprovalGateway approvalGateway = ApplicationContextHolder.getBean(optType.getBean(), IProcessApprovalGateway.class);
        if (Objects.isNull(optType)){
            throw exception(WorkflowApplicationEnum.PROCESS_TASK_EXECUTE_NOT_FOUND);
        }
        ProcessInstanceDto instanceDto = context.getProcessInstanceDto();
        if (Objects.isNull(instanceDto)){
            instanceDto = instanceGateway.findByInstanceId(context.getInstanceId());
        }
        /** ②校验任务是否存在*/
        String assignee = LoginUserInfoUtil.getLoginName();

        Task task = context.getTask();
        if (Objects.isNull(task)){
            task = processTaskService.getTask(context.getTaskId());
        }
        if (Objects.isNull(task)){
            throw exception(WorkflowApplicationEnum.PROCESS_TASK_NOT_EXIST);
        }

        /** ③根据策略执行任务*/
        approvalGateway.execute(processTaskService,task,context);

        /** ④抄送用户处理*/


        /** ⑤表单实例数据记录*/
        String formInstNo = SequenceUtil.nextSequenceNum(WorkFlowSequenceEnum.FORM_INSTANCE);
//        if (StringUtils.isEmpty(instanceDto.getFormInstNo())){
//            String formInstNo = SequenceUtil.nextSequenceNum(SequenceEnum.FORM_INSTANCE);
//            instanceDto.setFormInstNo(formInstNo);
//            ProcessInstanceContext instanceContext = ProcessInstanceContext.builder().id(instanceDto.getId()).bizNo(instanceDto.getBizNo()).instanceId(instanceDto.getInstanceId()).formInstNo(formInstNo).build();
//            instanceGateway.update(instanceContext);
//        }
        formDataExecutor.formDataHandler(context.getTaskFormInfo(), instanceDto.getInstanceNo(),formInstNo);
        /**
         * ⑥ 日志记录
         */
        logRecord(context, task, assignee, instanceDto, formInstNo);
    }

    @Override
    public void changeState(String bizNo, String currentActivityId, String targetActivityId) {
        ProcessInstanceDto instanceDto = instanceGateway.findByBizNoAndProcessNo(bizNo, null);
        if (Objects.isNull(instanceDto)){
            throw  exception(WorkflowApplicationEnum.PROCESS_INSTANCE_NOT_EXIST);
        }
        processInstanceService.changeState(instanceDto.getInstanceId(),currentActivityId,targetActivityId,null);
    }

    /**
     * 记录日志
     * @param context
     * @param task
     * @param assignee
     * @param instanceDto
     * @param formInstNo
     */
    private void logRecord(ApprovalContext context, Task task, String assignee, ProcessInstanceDto instanceDto, String formInstNo) {
        String candidate = getCandidate(task);
        String taskId = task.getId();
        ProcessApprovalRecordDto dto =  approvalRecordGateway.findByUserInstanceNoAndTaskId(assignee, instanceDto.getInstanceNo(),taskId);
        if (Objects.isNull(dto)){
            ProcessApprovalRecordContext recordContext = ProcessApprovalRecordContext.builder()
                    .formInstNo(formInstNo)
                    .instanceNo(instanceDto.getInstanceNo())
                    .instanceName(instanceDto.getInstanceName())
                    .instanceId(task.getProcessInstanceId())
                    .taskId(taskId)
                    .taskKey(task.getTaskDefinitionKey())
                    .taskName(task.getName())
                    .startTime(LocalDateTimeUtil.dateToLocalDateTime(task.getCreateTime()))
                    .endTime(LocalDateTimeUtil.dateTime())
                    .candidate(candidate)
                    .enAssignee(assignee)
                    .cnAssignee(LoginUserInfoUtil.getLoginUserInfo().getUserName())
                    .optType(context.getOptTypeEnum().getType())
                    .comment(context.getComment())
                    .build();
            approvalRecordGateway.insert(recordContext);
        }
    }

    /**
     * 获取候选人或角色
     * @param task
     * @return
     */
    private  String getCandidate(Task task) {
        List<? extends IdentityLinkInfo> identityLinks = task.getIdentityLinks();
        Set<String>  users = new HashSet<>();
        identityLinks.forEach(s->{
            if (StringUtils.isNotEmpty(s.getUserId())){
                users.add(s.getUserId());
            }
            if(StringUtils.isNotEmpty(s.getGroupId())){
                // 候选角色
                users.add(s.getGroupId());
            }
        });
        return String.join(SymbolConstant.comma,users);
    }

}
