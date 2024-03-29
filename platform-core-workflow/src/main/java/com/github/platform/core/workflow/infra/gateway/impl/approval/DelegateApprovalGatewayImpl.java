package com.github.platform.core.workflow.infra.gateway.impl.approval;

import com.github.platform.core.auth.util.LoginUserInfoUtil;
import com.github.platform.core.common.gateway.BaseGatewayImpl;
import com.github.platform.core.workflow.domain.constant.ProcessOptTypeEnum;
import com.github.platform.core.workflow.domain.context.ApprovalContext;
import com.github.platform.core.workflow.domain.gateway.IProcessApprovalGateway;
import com.github.platform.core.workflow.infra.service.IProcessTaskService;
import com.github.platform.core.standard.constant.SymbolConstant;
import lombok.extern.slf4j.Slf4j;
import org.flowable.task.api.Task;
import org.springframework.stereotype.Service;

/**
 * 委派任务实现
 * @author: yxkong
 * @date: 2023/11/10 15:23
 * @version: 1.0
 */
@Service("delegateApprovalGateway")
@Slf4j
public class DelegateApprovalGatewayImpl extends BaseGatewayImpl implements IProcessApprovalGateway {

    @Override
    public void execute(IProcessTaskService processTaskService,Task task, ApprovalContext context) {
        String comment = getComment(context);
        StringBuilder sb = new StringBuilder(LoginUserInfoUtil.getLoginName())
                .append("->").append(context.getAssignee()).append(SymbolConstant.colon).append(comment);
        processTaskService.addComment(context.getTaskId(), context.getInstanceId(), ProcessOptTypeEnum.DELEGATE.getType(), sb.toString());
//        processTaskService.setOwner(context.getTaskId(),LoginUserInfoUtil.getLoginName());
        processTaskService.delegateTask(context.getTaskId(),context.getAssignee());
        context.setComment(comment);
    }

}
