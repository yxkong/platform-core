package com.github.platform.core.sys.adapter.api.command.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 用户审批
 *
 * @author: yxkong
 * @date: 2023/6/2 10:44 AM
 * @version: 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "三方用户增加或修改")
public class ThirdApproveCmd {
    /** 主键 */
    @NotNull(message = "主键id不能为空")
    @Schema(description = "主键")
    private Long id;
    /** 加密用户id */
    @NotEmpty(message = "加密用户id不能为空")
    @Schema(description = "加密用户id")
    private String userId;
    @NotNull(message = "部门不能为空")
    @Schema(description ="部门ID")
    private Long deptId;
    @Schema(description ="用户角色")
    private String[] roleKeys;
    @Schema(description ="用户状态")
    private Integer status;
}
