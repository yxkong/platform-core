package com.github.platform.core.workflow.infra.event;

import com.github.platform.core.workflow.domain.entity.WorkflowTaskEntity;
import org.springframework.context.ApplicationEvent;

/**
 * 用户任务事件
 * @author: yxkong
 * @date: 2024/2/6 18:30
 * @version: 1.0
 */
public class WorkFlowTaskEvent extends ApplicationEvent {

    private WorkflowTaskEntity taskEntity;

    public WorkFlowTaskEvent(WorkflowTaskEntity taskEntity) {
        super(taskEntity);
        this.taskEntity = taskEntity;
    }

    public WorkflowTaskEntity getTaskEntity() {
        return this.taskEntity;
    }
}
