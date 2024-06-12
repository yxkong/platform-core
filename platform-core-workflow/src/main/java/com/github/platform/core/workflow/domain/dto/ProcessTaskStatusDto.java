package com.github.platform.core.workflow.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * 流程任务状态
 * @author: yxkong
 * @date: 2023/11/15 10:31
 * @version: 1.0
 */
@Schema(description = "流程用户")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProcessTaskStatusDto {
    /**
     * 获取流程实例的历史节点（去重）
     */
    private Set<String> finishedTasks;

    /**
     * 已完成
     */
    private Set<String> finishedSequenceFlow;

    /**
     * 获取流程实例当前正在待办的节点（去重）
     */
    private Set<String> activeTasks;

    /**
     * 已拒绝
     */
    private Set<String> rejectedTasks;
    /**
     * 跳过任务，冗余字段，需要定制业务自己填充
     */
    private Set<String> skipTasks;

    public ProcessTaskStatusDto(Set<String> finishedTasks, Set<String> finishedSequenceFlow, Set<String> activeTasks, Set<String> rejectedTasks) {
        this.finishedTasks = finishedTasks;
        this.finishedSequenceFlow = finishedSequenceFlow;
        this.activeTasks = activeTasks;
        this.rejectedTasks = rejectedTasks;
    }
}
