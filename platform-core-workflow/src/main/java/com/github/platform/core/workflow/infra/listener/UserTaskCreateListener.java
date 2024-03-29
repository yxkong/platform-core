package com.github.platform.core.workflow.infra.listener;

import com.github.platform.core.common.utils.JsonUtils;
import com.github.platform.core.workflow.domain.constant.FlwConstant;
import lombok.extern.slf4j.Slf4j;
import org.flowable.bpmn.model.FlowElement;
import org.flowable.engine.delegate.TaskListener;
import org.flowable.engine.impl.persistence.entity.ExecutionEntity;
import org.flowable.engine.impl.util.Flowable5Util;
import org.flowable.task.service.delegate.DelegateTask;

/**
 * 单用户任务处理监听
 * TaskListener 监听用户任务的生命周期，需要用户或系统需要执行的时候，适用于 UserTask<br>
 * 执行顺序：assignment 分配办理人 → create 创建 → complete 完成 → delete 删除
 *  生命周期节点见  {@link org.flowable.task.service.delegate.BaseTaskListener} <br>
 *  - `create`（创建任务时） 只执行一次
 *  - `assignment`（任务分配时） 任务每次分配给用户/组时执行一次
 *  - `complete`（任务完成时） 只执行一次
 *  - `delete` (任务删除时)
 *  - `all` (所有任务)
 *
 * @author: yxkong
 * @date: 2023/11/6 18:40
 * @version: 1.0
 */
@Slf4j
public class UserTaskCreateListener implements TaskListener {

    @Override
    public void notify(DelegateTask delegate) {
        String eventName = delegate.getEventName();
        // 获取当前任务的 ExecutionEntity
        // 获取当前任务的流程定义 ID
        String processDefinitionId = delegate.getProcessDefinitionId();
        // 获取当前任务的 ActivityId（节点 ID）
        String taskDefinitionKey = delegate.getTaskDefinitionKey();
        /**
         * 根据processDefinitionId 和 taskDefinitionKey获取流程节点 FlowElement
         */
        String instanceId = delegate.getProcessInstanceId();
        String instanceNo = (String) delegate.getVariable(FlwConstant.INSTANCE_NO);
        String bizNo = (String) delegate.getVariable(FlwConstant.BIZ_NO);
//        String processType = (String) delegate.getVariable(FlwConstant.PROCESS_TYPE);
        String taskId = delegate.getId();
        if (log.isDebugEnabled()){
            if (log.isDebugEnabled()){
                log.debug("UserTaskCreateListener  instanceId:{} instanceNo:{} bizNo:{} ",instanceId,instanceNo,bizNo);
            }
        }
    }
}
/**
 DelegateTask 中个字段的含义
 id: 任务的唯一标识符。每个任务都有一个唯一的ID，用于在流程中识别和访问任务。
 name: 任务的名称或标题。这是任务的可读性描述，通常用于显示给用户或参与者。
 description: 任务的描述。这是任务的详细说明，可以提供更多的上下文信息。
 assignee: 被分配的用户。这是指定要执行任务的用户，可以是具体的用户ID或用户的标识。
 owner: 任务的所有者。与 assignee 不同，owner 是指任务的所有者，但并不一定要执行任务。通常用于标识任务的归属，而不是执行者。
 processInstanceId: 流程实例的ID。这是启动任务所属的流程实例的唯一标识符。
 executionId: 执行实例的ID。执行实例是流程实例中的一个执行路径，可以包含多个任务。executionId 标识了任务所在的执行实例。
 taskDefinitionKey: 任务定义的关键。它是流程定义中任务节点的标识符，用于指示任务类型和流程中的位置。
 priority: 任务的优先级。可以用于指示任务的紧急程度，以帮助调度任务的执行顺序。
 dueDate: 任务的截止日期。这是任务的期限，指示任务应该在何时之前完成。
 */