package com.github.platform.core.sys.domain.context;

import com.github.platform.core.sys.domain.common.entity.SysRoleBase;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;
import java.util.Set;

/**
* 系统角色增加或修改上下文
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-15 10:55:06.077
* @version 1.0
*/
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class SysRoleContext extends SysRoleBase {
    /**
     * 角色菜单权限
     */
    @Schema(description ="角色菜单权限")
    private Set<Long> menuIds;
}
