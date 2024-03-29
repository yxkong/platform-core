package com.github.platform.core.workflow.infra.service;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.task.api.Task;
import org.flowable.task.api.history.HistoricTaskInstance;

import java.util.List;
import java.util.Map;

/**
 * 流程任务服务
 * @author: yxkong
 * @date: 2023/11/10 15:25
 * @version: 1.0
 */
public interface IProcessTaskService {
    /**
     * 查询指定实例，指定节点，指定审批人的任务id
     * @param instanceId
     * @param taskKey
     * @param assignee
     * @return
     */
    Task getTaskByUserInstantIdAndTaskId(String instanceId, String taskKey, String assignee);

    /**
     * 获取指定优化active节点
     * @param instanceId
     * @param assignee
     * @return
     */
    Task getUserActiveTask(String instanceId, String assignee);

    /**
     * 给对应的任务添加评论
     * @param taskId  任务id
     * @param instanceId  实例id
     * @param type  评论自定义类型
     * @param message 评论信息
     */
    void addComment(String taskId, String instanceId, String type, String message);

    /**
     * 完成任务
     * @param taskId
     * @param variables
     */
    void complete(String taskId, Map<String, Object> variables);


    /**
     * 获取流程实例
     * @param instanceId
     * @return
     */
    HistoricProcessInstance getInstance(String instanceId);
    /**
     * 根据获取Historic任务实例
     * @param taskId
     * @return
     */
    HistoricTaskInstance getHisTask(String taskId);

    /**
     * 根据任务id获取任务
     * @param taskId
     * @return
     */
    Task getTask(String taskId);

    List<HistoricActivityInstance> queryHisActivity(String instanceId);

    /**
     * 委派任务以后，就要标记任务为已解决
     * @param taskId
     */
    void resolveTask(String taskId);

    /**
     * 设置受让人
     * @param taskId
     * @param assignee
     */
    void setAssignee(String taskId, String assignee);

    /**
     * 设置任务拥有人
     * @param taskId
     * @param assignee
     */
    void setOwner(String taskId, String assignee);

    /**
     * 委派任务
     * @param taskId
     * @param assignee
     */
    void delegateTask(String taskId, String assignee);

    /**
     * 根据实例id和节点定义id获取一条任务
     * @param instanceId
     * @param taskKey
     * @return
     */
    Task getTaskByInstanceIdAndTaskKey(String instanceId, String taskKey);

}
