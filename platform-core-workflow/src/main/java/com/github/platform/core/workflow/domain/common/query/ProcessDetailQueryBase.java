package com.github.platform.core.workflow.domain.common.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;

/**
 * 流程详情查询
 * @author: yxkong
 * @date: 2023/11/13 14:18
 * @version: 1.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
@Schema(description = "流程查询")
public class ProcessDetailQueryBase{
    /**
     * 流程实例id
     */
    @Schema(description = "实例id不能为空")
    @NotEmpty(message = "流程实例id不能为空")
    private String instanceId;
    /**
     * 指定节点的任务id（用于查该节点的表单或表单数据）
     */
    @Schema(description = "任务id")
    private String taskId;
}
