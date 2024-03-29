package com.github.platform.core.workflow.infra.event;

import com.github.platform.core.workflow.domain.entity.WorkflowActivityEntity;
import org.springframework.context.ApplicationEvent;

/**
 * 活动事件
 * @Author: yxkong
 * @Date: 2023/12/6 17:09
 * @version: 1.0
 */
public class WorkflowActivityEvent extends ApplicationEvent {
    private WorkflowActivityEntity userTask;

    public WorkflowActivityEvent(WorkflowActivityEntity userTask) {
        super(userTask);
        this.userTask = userTask;
    }

    public WorkflowActivityEntity getUserTask() {
        return userTask;
    }
}
