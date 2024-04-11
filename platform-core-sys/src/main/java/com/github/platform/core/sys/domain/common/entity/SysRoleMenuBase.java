package com.github.platform.core.sys.domain.common.entity;

import com.github.platform.core.standard.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
* 角色和菜单关联表模型实体
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-15 10:55:06.371
* @version 1.0
*/
@Data
@SuperBuilder
@NoArgsConstructor
public class SysRoleMenuBase extends BaseEntity {
    /** 角色ID */
    @Schema(description = "角色ID")
    protected Long roleId;
    /** 角色标识 */
    @Schema(description = "角色标识")
    protected String roleKey;
    /** 菜单ID */
    @Schema(description = "菜单ID")
    protected Long menuId;
    /**创建人*/
    @Schema(description = "创建人")
    protected String createBy;
}
