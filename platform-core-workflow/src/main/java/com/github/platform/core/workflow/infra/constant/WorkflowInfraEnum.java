package com.github.platform.core.workflow.infra.constant;

import com.github.platform.core.standard.exception.BaseResult;

/**
 * 工作流Infra常量
 * @author: yxkong
 * @date: 2023/11/10 15:59
 * @version: 1.0
 */
public enum WorkflowInfraEnum implements BaseResult {
    APPROVAL_TASK_STATE_SUSPENDED("16601","当前登录用户未找到当前节点的任务" )
    ;

    WorkflowInfraEnum(String status, String message) {
        this.status = status;
        this.message = message;
    }

    private String status;
    private String message;

    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
