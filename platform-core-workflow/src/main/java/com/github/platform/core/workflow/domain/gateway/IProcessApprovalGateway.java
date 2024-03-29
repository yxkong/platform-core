package com.github.platform.core.workflow.domain.gateway;

import com.github.platform.core.common.utils.StringUtils;
import com.github.platform.core.workflow.domain.context.ApprovalContext;
import com.github.platform.core.workflow.infra.service.IProcessTaskService;
import org.flowable.task.api.Task;

/**
 * 流程审批网关
 * @author: yxkong
 * @date: 2023/11/10 15:20
 * @version: 1.0
 */
public interface IProcessApprovalGateway {
    /**
     * 获取评论
     * @param context
     * @return
     */
    default String getComment(ApprovalContext context) {
        return StringUtils.isEmpty(context.getComment()) ? context.getOptTypeEnum().getDesc() : context.getComment();
    }
    /**
     * 流程处理
     * @param processTaskService 任务处理服务，不同的工作流引擎实现方式不一样
     * @param task 当前节点
     * @param context 处理上下文
     */
    void execute(IProcessTaskService processTaskService,Task task, ApprovalContext context);

}
