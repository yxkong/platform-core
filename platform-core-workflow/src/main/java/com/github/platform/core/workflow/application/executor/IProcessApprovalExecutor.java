package com.github.platform.core.workflow.application.executor;

import com.github.platform.core.workflow.domain.constant.ProcessOptTypeEnum;
import com.github.platform.core.workflow.domain.context.ApprovalContext;

/**
 * 流程审批执行器
 *
 * @author: yxkong
 * @date: 2023/12/27 11:43
 * @version: 1.0
 */
public interface IProcessApprovalExecutor {
    /**
     * 审批提交, 适用于一人通过就通过的场景，随机获取一个task进行审批
     * @param optType 操作类型枚举 ProcessOptTypeEnum
     * @param bizNo 业务编号
     * @param taskKey 任务节点
     */
    void submit(ProcessOptTypeEnum optType, String bizNo, String taskKey);

    /**
     * 审批提交，适用于所有通过就通过的场景
     * @param context
     */
    void submit(ApprovalContext context);

    /**
     * 强制执行
     * @param bizNo
     * @param currentActivityId
     * @param targetActivityId
     */
    void changeState(String bizNo, String currentActivityId, String targetActivityId);
}
