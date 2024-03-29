package com.github.platform.core.workflow.domain.context;

import com.github.platform.core.workflow.domain.common.opt.ApprovalBase;
import com.github.platform.core.workflow.domain.constant.ProcessOptTypeEnum;
import com.github.platform.core.workflow.domain.dto.ProcessInstanceDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.flowable.task.api.Task;

/**
 * 流程审批上下文
 * @author: yxkong
 * @date: 2023/11/10 14:05
 * @version: 1.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ApprovalContext extends ApprovalBase {
    /**
     * 操作类型枚举
     */
    protected ProcessOptTypeEnum optTypeEnum;
    /**
     * 性能优化 -> 冗余ProcessInstanceDto
     */
    protected ProcessInstanceDto processInstanceDto;
    /**
     * 性能优化 -> 冗余task
     */
    protected Task task;
}
