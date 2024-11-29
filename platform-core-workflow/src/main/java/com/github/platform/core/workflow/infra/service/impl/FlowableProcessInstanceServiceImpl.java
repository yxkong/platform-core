package com.github.platform.core.workflow.infra.service.impl;

import com.github.platform.core.auth.util.LoginUserInfoUtil;
import com.github.platform.core.common.utils.CollectionUtil;
import com.github.platform.core.common.utils.StringUtils;
import com.github.platform.core.standard.entity.dto.PageBean;
import com.github.platform.core.workflow.domain.constant.FlwConstant;
import com.github.platform.core.workflow.domain.constant.InstanceStatusEnum;
import com.github.platform.core.workflow.domain.constant.ProcessOptTypeEnum;
import com.github.platform.core.workflow.domain.context.ProcessQueryContext;
import com.github.platform.core.workflow.domain.context.ProcessRunContext;
import com.github.platform.core.workflow.infra.service.IProcessInstanceService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.flowable.bpmn.constants.BpmnXMLConstants;
import org.flowable.common.engine.impl.identity.Authentication;
import org.flowable.engine.HistoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.runtime.Execution;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.flowable.task.api.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * flowable 实例管理
 * @author: yxkong
 * @date: 2023/11/2 11:06
 * @version: 1.0
 */
@Service("flowableProcessInstanceService")
@Slf4j
public class FlowableProcessInstanceServiceImpl implements IProcessInstanceService {
    /**
     * 查询运行信息
     */
    @Autowired(required = false)
    private RuntimeService runtimeService;
    /**
     * 查询任务信息
     */
    @Autowired(required = false)
    private TaskService taskService;
    /**
     * 查询历史信息
     */
    @Autowired(required = false)
    private HistoryService historyService;



    @Override
    @Transactional(rollbackFor = Exception.class)
    public String startProcessInstance(ProcessRunContext context) {
        context.putParam(FlwConstant.BIZ_NO, context.getBizNo());
        context.putParam(FlwConstant.PROCESS_NO, context.getProcessNo());
        if (StringUtils.isEmpty(context.getInitiator())){
            context.setInitiator(LoginUserInfoUtil.getLoginName());
        }
        //设置启动人
        Authentication.setAuthenticatedUserId(context.getInitiator());
        context.putParam(BpmnXMLConstants.ATTRIBUTE_EVENT_START_INITIATOR, context.getInitiator());
        context.putParam(FlwConstant.TENANT_ID,context.getTenantId());
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(context.getProcessNo(),context.getBizNo(),context.getVariables());
        String processInstanceId = processInstance.getId();
        if (log.isWarnEnabled()){
            log.warn("工作流启动成功 流程标识 = {} 启动人 = {} 业务单号 = {} 流程实例ID = {} ", context.getProcessNo(),context.getInitiator(), context.getBizNo() , processInstanceId);
        }

        //设置业务名称作为流程实例名称
        runtimeService.setProcessInstanceName(processInstanceId,context.getInstanceName());
        //启动第一个任务
//        startFirstTask(processInstance,context.getVariables());
        return processInstanceId;
    }

    /**
     * 如果第一个任务是发起人，直接通过
     * @param processInstance
     * @param variables
     */
    private void startFirstTask(ProcessInstance processInstance, Map<String, Object> variables) {
        // 若第一个用户任务为发起人，则自动完成任务
        List<Task> tasks = taskService.createTaskQuery().processInstanceId(processInstance.getProcessInstanceId()).list();
        if (CollectionUtil.isEmpty(tasks)){
            return;
        }
        String user = (String) variables.get(BpmnXMLConstants.ATTRIBUTE_EVENT_START_INITIATOR);
        for (Task task : tasks) {
            if (user.equals(task.getAssignee())) {
                taskService.addComment(task.getId(), processInstance.getProcessInstanceId(), ProcessOptTypeEnum.NORMAL.getType(), LoginUserInfoUtil.getLoginName() + "发起流程申请");
                taskService.complete(task.getId(), variables);
            }
        }
    }
    @Override
    public void delete(String instanceId, String reason) {
        List<Task> list = getTasks(instanceId);
        List<Task> subTasks = list.stream().filter(e -> StringUtils.isNotBlank(e.getParentTaskId())).collect(Collectors.toList());
        if (CollectionUtil.isNotEmpty(subTasks)) {
            subTasks.forEach(e -> taskService.deleteTask(e.getId()));
        }
        runtimeService.deleteProcessInstance(instanceId, reason);
        //2.删除历史记录
        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(instanceId).singleResult();
        if (Objects.nonNull(historicProcessInstance)) {
            historyService.deleteHistoricProcessInstance(instanceId);
        }
        if (log.isDebugEnabled()){
            log.debug("工作流删除成功 实例id = {} 删除原因 = {} ", instanceId, reason);
        }
    }

    private List<Task> getTasks(String instanceId) {
        return taskService.createTaskQuery().processInstanceId(instanceId).list();
    }

    @Override
    public InstanceStatusEnum updateStatus(String instanceId, String reason) {
        // 1. 查询指定流程实例的数据
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(instanceId).singleResult();
        if (processInstance.isSuspended()){
            runtimeService.activateProcessInstanceById(instanceId);
            if (log.isDebugEnabled()){
                log.debug("工作流激活成功 流程实例ID = {} 激活原因 = {} ", instanceId, reason);
            }
            return InstanceStatusEnum.ACTIVE;
        } else {
            // 更新为挂起
            runtimeService.suspendProcessInstanceById(instanceId);
            if (log.isDebugEnabled()){
                log.debug("工作流挂起成功 流程实例ID = {} 挂起原因 = {} ", instanceId, reason);
            }
            return InstanceStatusEnum.SUSPEND;
        }
    }

    @Override
    public void stopInstance(String instanceId, String reason) {
//        List<Task> list =  getTasks(instanceId);
//        List<Task> subTasks = list.stream().filter(e -> StringUtils.isNotBlank(e.getParentTaskId())).collect(Collectors.toList());
//        if (CollectionUtil.isNotEmpty(subTasks)) {
//            //废弃以后不可恢复，任务即可
//            subTasks.forEach(e -> taskService.deleteTask(e.getId()));
//        }
//        if (log.isDebugEnabled()){
//            log.debug("工作流废弃成功 流程实例ID = {} 废弃原因 = {} ", instanceId, reason);
//        }
        runtimeService.deleteProcessInstance(instanceId,reason);
    }

    @Override
    public List<Task> queryByBizNo(String bizNo) {
        return taskService.createTaskQuery().processInstanceBusinessKey(bizNo).list();
    }

    @Override
    public Pair<Long,List<Task>> queryTodo(ProcessQueryContext context) {
        TaskQuery query = taskService.createTaskQuery()
                .active()
                .includeProcessVariables()
                .taskCandidateOrAssigned(context.getAssignee())
                .orderByTaskCreateTime().desc();
        if (StringUtils.isNotEmpty(context.getBizNo())){
            query.processInstanceBusinessKey(context.getBizNo());
        }
        if (StringUtils.isNotEmpty(context.getProcessNo())){
            query.processDefinitionKeyLike(context.getProcessNo());
        }
        if (CollectionUtil.isNotEmpty(context.getRoles())){
            query.taskCandidateGroupIn(context.getRoles());
        }
        if (StringUtils.isNotEmpty(context.getTaskName())){
            query.taskName(context.getTaskName());
        }
        if (StringUtils.isNotEmpty(context.getProcessType())){
            query.processVariableValueEquals(FlwConstant.PROCESS_TYPE,context.getProcessType());
        }
        if (StringUtils.isNotEmpty(context.getInstanceName())){
            query.processVariableValueEquals(FlwConstant.INSTANCE_NAME,context.getInstanceName());
        }


        long totalSize = query.count();
        if(totalSize == PageBean.EMPTY_TOTAL_SIZE.longValue()){
            return Pair.of(totalSize,null) ;
        }
        return Pair.of(totalSize,query.listPage(context.getStartOffset(), context.getPageSize()));
    }

    @Override
    public void suspend(String instanceId, String reason) {
        // 1. 查询指定流程实例的数据
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(instanceId).singleResult();
        if (!processInstance.isSuspended()){
            // 更新为挂起
            runtimeService.suspendProcessInstanceById(instanceId);
            if (log.isDebugEnabled()){
                log.debug("工作流挂起成功 流程实例ID = {} 挂起原因 = {} ", instanceId, reason);
            }
        } else {
            if (log.isDebugEnabled()){
                log.debug("工作流：instanceId = {} 处于挂起状态，不能操作挂起 ", instanceId);
            }
        }
    }

    @Override
    public void resume(String instanceId, String reason) {
        // 1. 查询指定流程实例的数据
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(instanceId).singleResult();
        if (processInstance.isSuspended()){
            runtimeService.activateProcessInstanceById(instanceId);
            if (log.isDebugEnabled()){
                log.debug("工作流激活成功 流程实例ID = {} 激活原因 = {} ", instanceId, reason);
            }
        } else {
            if (log.isDebugEnabled()){
                log.debug("工作流：instanceId = {} 处于非挂起状态,不能操作恢复 ", instanceId);
            }
        }
    }

    @Override
    public void changeState(String processInstanceId, String currentActivityId, String targetActivityId,Map<String, Object> variables) {
        runtimeService.createChangeActivityStateBuilder()
                .processInstanceId(processInstanceId)
                .moveActivityIdTo(currentActivityId,targetActivityId)
                .processVariables(variables)
                .changeState();
    }

    @Override
    public void completedTask(String taskId, Map<String, Object> variables) {
        taskService.complete(taskId,variables);
    }

    @Override
    public Object getVariable(String instanceId, String variableName) {
        return runtimeService.getVariable(instanceId,variableName);
    }

    @Override
    public Map<String, Object> getVariables(String instanceId) {
        return runtimeService.getVariables(instanceId);
    }

    @Override
    public void setVariable(String instanceId, String variableName, Object value) {
        if (log.isDebugEnabled()){
            log.debug("设置流程变量 instanceId：{} variableName:{} value:{}",instanceId,variableName,value);
        }
        runtimeService.setVariable(instanceId,variableName,value);
    }



    @Override
    public void setVariables(String instanceId,Map<String, Object> variables) {
        if (log.isDebugEnabled()){
            log.debug("设置流程变量 instanceId：{} variables:{} ",instanceId,variables);
        }
        runtimeService.setVariables(instanceId,variables);
    }

    @Override
    public Execution getExecution(String executionId) {
        return runtimeService.createExecutionQuery()
                .executionId(executionId)
                .singleResult();
    }
}
