package com.github.platform.core.workflow.infra.listener;

import com.github.platform.core.common.utils.ApplicationContextHolder;
import com.github.platform.core.common.utils.CollectionUtil;
import com.github.platform.core.common.utils.JsonUtils;
import com.github.platform.core.standard.constant.SymbolConstant;
import com.github.platform.core.standard.util.LocalDateTimeUtil;
import com.github.platform.core.workflow.domain.constant.FlwConstant;
import com.github.platform.core.workflow.domain.constant.InstanceStatusEnum;
import com.github.platform.core.workflow.domain.constant.ProcessTypeEnum;
import com.github.platform.core.workflow.domain.dto.ProcessDefinitionDto;
import com.github.platform.core.workflow.domain.entity.WorkflowActivityEntity;
import com.github.platform.core.workflow.domain.entity.WorkflowTaskEntity;
import com.github.platform.core.workflow.domain.gateway.IProcessDefinitionGateway;
import com.github.platform.core.workflow.domain.gateway.IProcessInstanceGateway;
import com.github.platform.core.workflow.infra.event.WorkFlowTaskEvent;
import com.github.platform.core.workflow.infra.event.WorkflowActivityEvent;
import com.github.platform.core.workflow.infra.event.WorkflowProcessEvent;
import com.github.platform.core.workflow.infra.util.BpmnModelUtils;
import lombok.extern.slf4j.Slf4j;
import org.flowable.bpmn.constants.BpmnXMLConstants;
import org.flowable.bpmn.model.FlowElement;
import org.flowable.bpmn.model.SequenceFlow;
import org.flowable.bpmn.model.UserTask;
import org.flowable.common.engine.api.delegate.event.FlowableEngineEntityEvent;
import org.flowable.common.engine.api.delegate.event.FlowableEngineEventType;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.event.AbstractFlowableEngineEventListener;
import org.flowable.engine.delegate.event.FlowableActivityEvent;
import org.flowable.engine.delegate.event.FlowableCancelledEvent;
import org.flowable.engine.delegate.event.FlowableProcessStartedEvent;
import org.flowable.engine.delegate.event.impl.FlowableActivityEventImpl;
import org.flowable.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.flowable.task.service.impl.persistence.entity.TaskEntity;
import org.flowable.task.service.impl.persistence.entity.TaskEntityImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 全局事件监听
 * 适用于与任务相关的业务逻辑
 * @author: yxkong
 * @date: 2023/11/8 13:45
 * @version: 1.0
 */
@Component
@Slf4j
public class GlobalEventListener extends AbstractFlowableEngineEventListener {


    @Resource
    private IProcessDefinitionGateway processDefinitionGateway;

    private RuntimeService runtimeService;

    @Resource
    private ApplicationContext applicationContext;

    public void setRuntimeService(RuntimeService runtimeService) {
        this.runtimeService = runtimeService;
    }

    @Override
    protected void processStarted(FlowableProcessStartedEvent event) {
        // 在流程取消时的处理逻辑
        org.flowable.engine.delegate.event.impl.FlowableEntityEventImpl t = (org.flowable.engine.delegate.event.impl.FlowableEntityEventImpl)event;
        ExecutionEntityImpl entity = (ExecutionEntityImpl) t.getEntity();
        String instanceId =  entity.getProcessInstanceId();
//        log.info("流程任务监听开始节点 instanceId = {} class = {}  ", instanceId,event.getClass());
        String bizNo = (String) entity.getVariable(FlwConstant.BIZ_NO);
        String processType = (String) entity.getVariable(FlwConstant.PROCESS_TYPE);
        if (!ProcessTypeEnum.isPm(processType)){
            return;
        }
        Boolean createGroup = (Boolean) entity.getVariable(FlwConstant.CREATE_GROUP);
        String createUser = (String) entity.getVariable(BpmnXMLConstants.ATTRIBUTE_EVENT_START_INITIATOR);
        WorkflowProcessEvent processEvent = new WorkflowProcessEvent(instanceId, bizNo, processType, InstanceStatusEnum.ACTIVE);
        processEvent.setCreateGroup(createGroup);
        processEvent.setCreateUser(createUser);
        applicationContext.publishEvent(processEvent);
    }
    @Override
    protected void processCancelled(FlowableCancelledEvent event) {
        // 在流程取消时的处理逻辑
        String instanceId = event.getProcessInstanceId();

        org.flowable.engine.delegate.event.impl.FlowableProcessCancelledEventImpl t = (org.flowable.engine.delegate.event.impl.FlowableProcessCancelledEventImpl)event;
        DelegateExecution e = t.getExecution();
        String processType = (String) e.getVariable(FlwConstant.PROCESS_TYPE);
        if (!ProcessTypeEnum.isPm(processType)){
            return;
        }
        String currentActivityId = e.getCurrentActivityId();
        FlowableEngineEventType type = t.getType();
        String bizNo = (String) e.getVariable(FlwConstant.BIZ_NO);

        log.info("流程任务监听取消节点 bizNo = {}, instanceId = {} currentActivityId={} class = {}  ", bizNo,instanceId,currentActivityId,event.getClass());

        applicationContext.publishEvent(new WorkflowProcessEvent(instanceId,bizNo,processType,InstanceStatusEnum.CANCELLED));
    }
    @Override
    protected void processCompleted(FlowableEngineEntityEvent event) {
        String instanceId = event.getProcessInstanceId();
        org.flowable.engine.delegate.event.impl.FlowableEntityEventImpl t = (org.flowable.engine.delegate.event.impl.FlowableEntityEventImpl)event;
        ExecutionEntityImpl entity = (ExecutionEntityImpl) t.getEntity();
        String processType = (String) entity.getVariable(FlwConstant.PROCESS_TYPE);
        if (!ProcessTypeEnum.isPm(processType)){
            return;
        }
        log.info("流程任务监听结束节点 instanceId = {} class = {}  ", instanceId,event.getClass());
        String instanceNo = (String) entity.getVariable(FlwConstant.INSTANCE_NO);
        String bizNo = (String) entity.getVariable(FlwConstant.BIZ_NO);

        IProcessInstanceGateway processInstanceGateway = ApplicationContextHolder.getBean(IProcessInstanceGateway.class);
        processInstanceGateway.updateByInstanceId(instanceId, InstanceStatusEnum.COMPLETED);

        applicationContext.publishEvent(new WorkflowProcessEvent(instanceId,bizNo,processType,InstanceStatusEnum.COMPLETED));
    }


    @Override
    protected void taskCreated(FlowableEngineEntityEvent event) {
        handlerTask(event,FlwConstant.TASK_CREATED);
    }

    @Override
    protected void taskCompleted(FlowableEngineEntityEvent event) {
        handlerTask(event,FlwConstant.TASK_COMPLETED);
    }
    private void handlerTask(FlowableEngineEntityEvent event,String eventType){
        if (!(event.getEntity() instanceof TaskEntity)) {
            log.info("非TaskEntity任创建：executionId={}, instanceId={}, entity class={} result:{}", event.getExecutionId(), event.getProcessInstanceId(), event.getEntity().getClass(), JsonUtils.toJson(event.getEntity()));
            return;
        }
        // 在任务创建时的处理逻辑
        TaskEntityImpl entity = (TaskEntityImpl)event.getEntity();
        // 节点名称
        String name = entity.getName();
        // 节点id
        String taskDefinitionKey = entity.getTaskDefinitionKey();
//        log.info("任务task：{} name: {} entity class={} result:{}",eventType, name, entity.getClass(), JsonUtils.toJson(entity));
        String executionId = entity.getExecutionId();
        DelegateExecution execution = (DelegateExecution) runtimeService.createExecutionQuery()
                .executionId(executionId)
                .singleResult();
        FlowElement flowElement = execution.getCurrentFlowElement();
        Map<String, Object> variables = entity.getVariables();
        String processType = (String) variables.get(FlwConstant.PROCESS_TYPE);
        if (!ProcessTypeEnum.isPm(processType)){
            return;
        }
        String instanceNo = (String) variables.get(FlwConstant.INSTANCE_NO);
        String bizNo = (String) variables.get(FlwConstant.BIZ_NO);

        String processNo = (String) variables.get(FlwConstant.PROCESS_NO);
        Integer version = (Integer) variables.get(FlwConstant.PROCESS_VERSION);
        // 获取拆分角色
        List<SequenceFlow> outgoingFlows = BpmnModelUtils.getElementOutgoingFlows(flowElement);
        String formKey = BpmnModelUtils.getFormKey(flowElement);
        log.info("任务task：{} executionId={}, flowElement={} variables:{}",eventType, executionId, JsonUtils.toJson(flowElement), JsonUtils.toJson(variables));
        WorkflowTaskEntity taskEntity = WorkflowTaskEntity.builder()
                .executionId(executionId).taskId(entity.getId()).taskKey(entity.getTaskDefinitionKey()).taskName(name)
                .eventType(eventType)
                .bizNo(bizNo).processType(processType).processNo(processNo).processVersion(version)
                .instanceId(entity.getProcessInstanceId()).instanceNo(instanceNo)
                .sendTime(LocalDateTimeUtil.dateTime()).currentActivityId(flowElement.getId())
                .outgoingFlows(outgoingFlows)
                .currentActivityId(flowElement.getId())
                .formKey(formKey)
                .build();
        applicationContext.publishEvent(new WorkFlowTaskEvent(taskEntity));
    }
    @Override
    protected void activityStarted(FlowableActivityEvent event) {
        handleActivityEvent(event,FlwConstant.ACTIVITY_STARTED);
    }
    private void handleActivityEvent(FlowableActivityEvent event, String eventType) {
        DelegateExecution execution = ((FlowableActivityEventImpl) event).getExecution();
        FlowElement flowElement = execution.getCurrentFlowElement();
        String instanceId = event.getProcessInstanceId();
        String activityId = event.getActivityId();
        String activityName = event.getActivityName();
        String activityType = event.getActivityType();
        log.warn("activity事件 {} instanceId:{} activityName:{}  activityType：{} type:{}",
                eventType,execution.getProcessInstanceId(), flowElement.getName(), event.getActivityName(), event.getClass().getName());
        if (BpmnXMLConstants.ELEMENT_TASK_USER.equals(activityType)){
            Map<String, Object> variables = execution.getVariables();
            String instanceNo = (String) variables.get(FlwConstant.INSTANCE_NO);
            String bizNo = (String) variables.get(FlwConstant.BIZ_NO);
            String processType = (String) variables.get(FlwConstant.PROCESS_TYPE);
            if (!ProcessTypeEnum.isPm(processType)){
                return;
            }
            String processNo = (String) variables.get(FlwConstant.PROCESS_NO);
            Integer version = (Integer) variables.get(FlwConstant.PROCESS_VERSION);
            ProcessDefinitionDto definitionDto = processDefinitionGateway.findByProcessNo(processNo, version);
            String executionId = execution.getId();
            String roles = null;
            UserTask userTask = (UserTask) flowElement;
            List<String> candidateGroups = userTask.getCandidateGroups();
            if (CollectionUtil.isNotEmpty(candidateGroups)){
                roles = String.join(SymbolConstant.comma,candidateGroups);
            }
            String mainRole = getMainRole(flowElement,roles);
            WorkflowActivityEntity activityEntity = WorkflowActivityEntity.builder()
                    .executionId(executionId).taskKey(activityId).taskName(activityName)
                    .eventType(eventType).activityType(activityType)
                    .bizNo(bizNo).processType(processType).processTypeName(definitionDto.getProcessName())
                    .instanceId(instanceId).instanceNo(instanceNo)
                    .mainRole(mainRole).roles(roles)
                    .sendTime(LocalDateTimeUtil.dateTime()).currentActivityId(flowElement.getId())
                    .build();
           applicationContext.publishEvent(new WorkflowActivityEvent(activityEntity));
        }
    }


    /**
     * 获取主角色
     * @param flowElement
     * @param roles
     * @return
     */
    private String getMainRole( FlowElement flowElement,String roles){
        if (!roles.contains(SymbolConstant.comma)){
            return roles;
        }
        Map<String, String> map = BpmnModelUtils.getElementExtendAttribute(flowElement);
        return map.get(FlwConstant.TASK_PROPERTY_MAIN_ROLE);
    }
}
