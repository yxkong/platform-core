package com.github.platform.core.workflow.infra.service;

import com.github.platform.core.workflow.domain.constant.InstanceStatusEnum;
import com.github.platform.core.workflow.domain.context.ProcessQueryContext;
import com.github.platform.core.workflow.domain.context.ProcessRunContext;
import org.apache.commons.lang3.tuple.Pair;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.runtime.Execution;
import org.flowable.task.api.Task;

import java.util.List;
import java.util.Map;

/**
 * 流程实例服务
 * @author: yxkong
 * @date: 2023/11/2 11:05
 * @version: 1.0
 */
public interface IProcessInstanceService {
    /**
     * 启动流程实例
     * @param context  流程启动上下文
     * @return 流程实例ID
     */
    String startProcessInstance( ProcessRunContext context);

    /**
     * 删除流程实例（正常情况下不允许删除）
     * @param instanceId 实例id
     * @param reason 原因
     * @return
     */
    void delete(String instanceId, String reason);

    /**
     * 更新实例状态，激活，挂起
     * @param instanceId 实例id
     * @param reason 原因
     * @return 返回操作枚举
     */
    InstanceStatusEnum updateStatus(String instanceId,String reason);

    /**
     * 取消/停止/废弃当前工作流(停止或废弃以后不可恢复)
     * @param instanceId
     * @param reason
     */
    void stopInstance(String instanceId,String reason);

    /**
     * 根据业务ID查询流程任务
     * @param bizNo
     * @return
     */
    List<Task> queryByBizNo(String bizNo);

    /**
     * 查询代办
     */
    Pair<Long,List<Task>> queryTodo(ProcessQueryContext context);
    /**
     * 挂起/暂停流程
     * @param instanceId
     * @param reason
     */
    void suspend(String instanceId, String reason);
    /**
     * 恢复流程
     * @param instanceId
     * @param reason
     */
    void resume(String instanceId, String reason);

    /**
     * 强制执行到下一节点
     * @param processInstanceId
     * @param currentActivityId
     * @param targetActivityId
     */
    void changeState(String processInstanceId, String currentActivityId, String targetActivityId,Map<String, Object> variables);

    /**
     * 完成指定用户任务
     * @param taskId
     * @param variables
     */
    void completedTask(String taskId,Map<String, Object> variables);


    /**
     * 获取单个流程变量
     * @param instanceId
     * @param variableName
     * @return
     */
    Object getVariable(String instanceId,String variableName);

    /**
     * 获取全部的流程变量
     * @param instanceId
     * @return
     */
    Map<String,Object> getVariables(String instanceId);

    /**
     * 设置流程变量
     * @param instanceId
     * @param variableName
     * @param value
     */
    void setVariable(String instanceId,String variableName,Object value);

    /**
     * 设置流程变量
     * @param instanceId
     */
    void setVariables(String instanceId,Map<String,Object> variables);

    /**
     * 根据executionId 获取Execution
     * @param executionId
     * @return
     */
    Execution getExecution(String executionId);

}
