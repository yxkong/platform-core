package com.github.platform.core.sys.adapter.api.command.role;

import com.github.platform.core.sys.domain.common.entity.SysRoleBase;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;
import java.util.Set;

/**
* 系统角色增加或修改
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-15 10:55:06.077
* @version 1.0
*/
@Data
@SuperBuilder
@NoArgsConstructor
@Schema(description = "系统角色增加或修改")
public class SysRoleCmd extends SysRoleBase{
    /**
     * 角色菜单权限
     */
    @NotEmpty(message = "权限不能为空")
    @Schema(description ="角色菜单权限")
    private Set<Long> menuIds;
}
