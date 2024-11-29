package com.github.platform.core.sys.domain.dto;

import com.github.platform.core.sys.domain.common.entity.SysRoleMenuBase;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
* 系统角色菜单传输实体
 * @Author: yxkong
 * @Date: 2024/11/25
 * @version: 1.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
@Schema(description = "系统角色菜单传输实体")
public class SysRoleMenuDto extends SysRoleMenuBase {
}
