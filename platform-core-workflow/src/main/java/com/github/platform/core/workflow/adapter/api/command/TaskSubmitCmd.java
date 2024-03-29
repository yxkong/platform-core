package com.github.platform.core.workflow.adapter.api.command;

import com.github.platform.core.workflow.domain.common.opt.ApprovalBase;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;

/**
 * 流程审批
 * @author: yxkong
 * @date: 2023/11/10 11:35
 * @version: 1.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Schema(description = " 创建流程实例实体")
public class TaskSubmitCmd extends ApprovalBase {
    /**
     * 操作类型
     */
    @NotEmpty(message="操作类型（optType）不能为空")
    private String optType;
}
