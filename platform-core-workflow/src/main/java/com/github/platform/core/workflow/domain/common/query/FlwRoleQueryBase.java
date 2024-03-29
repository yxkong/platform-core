package com.github.platform.core.workflow.domain.common.query;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;

/**
 * 流程角色查询实体
 *
 * @author: yxkong
 * @date: 2023/10/30 13:37
 * @version: 1.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
@Schema(description = "流程角色查询")
public class FlwRoleQueryBase {
    /**
     *流程类型
     */
    @Schema(description = "流程类型")
    @ApiModelProperty(value = "流程类型",required=true)
    @NotNull(message = "流程类型不能为空")
    private String processType;
    /**
     *
     */
    @Schema(description = "角色名称")
    @ApiModelProperty(value = "角色名称",required=false)
    private String name;
}
