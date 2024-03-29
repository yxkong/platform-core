package com.github.platform.core.workflow.infra.service.impl;

import com.github.platform.core.common.utils.CollectionUtil;
import com.github.platform.core.workflow.infra.service.IProcessTaskService;
import org.flowable.engine.HistoryService;
import org.flowable.engine.TaskService;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.history.HistoricActivityInstanceQuery;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.task.api.Task;
import org.flowable.task.api.TaskQuery;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 流程任务服务
 * @author: yxkong
 * @date: 2023/11/10 15:26
 * @version: 1.0
 */
@Service("flowableProcessTaskService")
public class FlowableProcessTaskServiceImpl implements IProcessTaskService {

//    addComment(Task task, String message): 在任务对象上添加一个新的评论。评论会存储在act_hi_comment表中，并且可以使用相应的查询进行检索。
//    resolveTask(String taskId): 标记为已解委的任务，并将其指派回原始任务拥有者。这意味着谁创建任务就可以继续完成任务。
//    setAssignee(String taskId, String userId): 将任务指派给指定的用户。
//    complete(String taskId): 完成任务。一旦调用此方法，将从活动列表中删除任务，并继续流程。



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
    public Task getTaskByUserInstantIdAndTaskId(String instanceId, String taskKey, String assignee) {
        TaskQuery taskQuery = taskService.createTaskQuery()
                .processInstanceId(instanceId)
                .taskDefinitionKey(taskKey)
                .taskAssignee(assignee)
                .includeIdentityLinks()
                ;
        List<Task> list = taskQuery.list();
        if (CollectionUtil.isEmpty(list)){
            return null;
        }
        return list.get(0);
    }

    @Override
    public Task getUserActiveTask(String instanceId, String assignee) {
        List<Task> list = taskService.createTaskQuery()
                .processInstanceId(instanceId)
                .taskCandidateOrAssigned(assignee).list();
        if (CollectionUtil.isNotEmpty(list)){
            return list.get(0);
        }
        return null;
    }

    @Override
    public void addComment(String taskId, String instanceId, String type, String message) {
        taskService.addComment(taskId,instanceId,type,message);
    }

    @Override
    public void complete(String taskId, Map<String, Object> variables) {
        //添加任务级变量，如果需要流程级变量，直接runtimeService.setVariables
        taskService.complete(taskId,variables);
    }
    @Override
    public HistoricProcessInstance getInstance(String instanceId) {
        return  historyService.createHistoricProcessInstanceQuery()
                .processInstanceId(instanceId)
                .includeProcessVariables()
                .singleResult();
    }

    @Override
    public HistoricTaskInstance getHisTask(String taskId) {
        return historyService.createHistoricTaskInstanceQuery()
                .taskId(taskId)
                .includeIdentityLinks()
                .includeProcessVariables()
                .includeTaskLocalVariables()
                .singleResult();
    }

    @Override
    public Task getTask(String taskId) {
        return taskService.createTaskQuery().taskId(taskId).includeIdentityLinks().singleResult();
    }

    @Override
    public List<HistoricActivityInstance> queryHisActivity(String instanceId) {
        HistoricActivityInstanceQuery query = historyService.createHistoricActivityInstanceQuery()
                .processInstanceId(instanceId);
        return query.list();
    }

    @Override
    public void resolveTask(String taskId) {
        taskService.resolveTask(taskId);
    }

    @Override
    public void setAssignee(String taskId, String assignee) {
        taskService.setAssignee(taskId,assignee);
    }

    @Override
    public void setOwner(String taskId, String assignee) {
        taskService.setOwner(taskId,assignee);
    }

    @Override
    public void delegateTask(String taskId, String assignee) {
        taskService.delegateTask(taskId,assignee);
    }

    @Override
    public Task getTaskByInstanceIdAndTaskKey(String instanceId, String taskKey) {
        List<Task> list = taskService.createTaskQuery()
                .processInstanceId(instanceId)
                .taskDefinitionKey(taskKey)
                .includeIdentityLinks()
                .includeProcessVariables()
                .includeTaskLocalVariables()
                .list();
        if (CollectionUtil.isNotEmpty(list)){
            return list.get(0);
        }
        return null;
    }


}
