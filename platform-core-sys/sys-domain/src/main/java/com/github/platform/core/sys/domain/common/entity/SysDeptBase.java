package com.github.platform.core.sys.domain.common.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.platform.core.common.entity.BaseAdminEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;

/**
* 系统部门模型实体
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-15 10:55:05.241
* @version 1.0
*/
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class SysDeptBase extends BaseAdminEntity   {
    /** 父部门id */
    @Schema(description = "父部门id")
    protected Long parentId;
    /** 祖级列表 */
    @Schema(description = "祖级列表")
    protected String ancestors;
    /** 部门名称 */
    @Schema(description = "部门名称")
    @NotEmpty(message="部门名称（deptName）不能为空")
    protected String deptName;
    /** 显示顺序 */
    @Schema(description = "显示顺序")
    protected Integer sort;
    /** 负责人 */
    @Schema(description = "负责人")
    protected String leader;
    /** 负责人电话号 */
    @Schema(description = "负责人电话号")
    protected String leaderMobile;

    @JsonIgnore
    protected Boolean idGtZero;
}
