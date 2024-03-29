package com.github.platform.core.workflow.infra.gateway.impl.approval;

import com.github.platform.core.common.gateway.BaseGatewayImpl;
import com.github.platform.core.workflow.domain.constant.ProcessOptTypeEnum;
import com.github.platform.core.workflow.domain.context.ApprovalContext;
import com.github.platform.core.workflow.domain.gateway.IProcessApprovalGateway;
import com.github.platform.core.workflow.infra.service.IProcessTaskService;
import lombok.extern.slf4j.Slf4j;
import org.flowable.task.api.DelegationState;
import org.flowable.task.api.Task;
import org.springframework.stereotype.Service;

/**
 * 完成任务实现
 * @author: yxkong
 * @date: 2023/11/10 15:23
 * @version: 1.0
 */
@Service("completeApprovalGateway")
@Slf4j
public class CompleteApprovalGatewayImpl extends BaseGatewayImpl implements IProcessApprovalGateway {

    @Override
    public void execute(IProcessTaskService processTaskService,Task task, ApprovalContext context) {
        String comment = getComment(context);
        /** 任务处于委派挂起状态，表示任务已经被委派，但尚未被受让人处理。*/
        if (DelegationState.PENDING.equals(task.getDelegationState())) {
            processTaskService.addComment(context.getTaskId(), context.getInstanceId(), ProcessOptTypeEnum.DELEGATE.getType(), comment);
            processTaskService.resolveTask(context.getTaskId());
        } else {
            /**正常的任务提交 任务已经被解决*/
            processTaskService.addComment(context.getTaskId(), context.getInstanceId(), ProcessOptTypeEnum.NORMAL.getType(), comment);
            processTaskService.setAssignee(context.getTaskId(), context.getAssignee());
            processTaskService.complete(context.getTaskId(), context.getVariables());
        }
    }
}
