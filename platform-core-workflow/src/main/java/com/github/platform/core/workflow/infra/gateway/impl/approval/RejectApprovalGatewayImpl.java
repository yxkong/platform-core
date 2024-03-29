package com.github.platform.core.workflow.infra.gateway.impl.approval;

import com.github.platform.core.common.gateway.BaseGatewayImpl;
import com.github.platform.core.workflow.domain.context.ApprovalContext;
import com.github.platform.core.workflow.domain.gateway.IProcessApprovalGateway;
import com.github.platform.core.workflow.infra.constant.WorkflowInfraEnum;
import com.github.platform.core.workflow.infra.service.IProcessTaskService;
import lombok.extern.slf4j.Slf4j;
import org.flowable.task.api.Task;
import org.springframework.stereotype.Service;

/**
 * 驳回任务
 * @author: yxkong
 * @date: 2023/11/10 15:23
 * @version: 1.0
 */
@Service("rejectApprovalGateway")
@Slf4j
public class RejectApprovalGatewayImpl extends BaseGatewayImpl implements IProcessApprovalGateway {

    @Override
    public void execute(IProcessTaskService processTaskService,Task task, ApprovalContext context) {
        String comment = getComment(context);
        if (task.isSuspended()){
            exception(WorkflowInfraEnum.APPROVAL_TASK_STATE_SUSPENDED);
        }
    }
}
