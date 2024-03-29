package com.github.platform.core.dingtalk.domain.common.entity;
import lombok.*;
import lombok.experimental.SuperBuilder;
import com.github.platform.core.standard.entity.BaseEntity;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 钉钉部门模型实体
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author yxkong
 * @date 2024-01-18 15:49:32.694
 * @version 1.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
public class DingDeptBase extends BaseEntity   {
    @NotNull(message="部门id（deptId）不能为空")
    /** 部门id */
    @Schema(description = "部门id")
    protected Long deptId;
    /** 部门名称 */
    @Schema(description = "部门名称")
    protected String name;
    @NotNull(message="父部门id（parentId）不能为空")
    /** 父部门id */
    @Schema(description = "父部门id")
    protected Long parentId;
    @NotNull(message="状态（1正常 0停用）（status）不能为空")
    /** 状态（1正常 0停用） */
    @Schema(description = "状态（1正常 0停用）")
    protected Integer status;
    /** 创建人 */
    @Schema(description = "创建人")
    protected String createBy;
    /** 更新人 */
    @Schema(description = "更新人")
    protected String updateBy;
}
