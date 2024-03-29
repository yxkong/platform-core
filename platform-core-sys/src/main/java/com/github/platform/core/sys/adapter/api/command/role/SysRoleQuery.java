package com.github.platform.core.sys.adapter.api.command.role;

import com.github.platform.core.sys.domain.common.query.SysRoleQueryBase;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
/**
* 系统角色查询
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-15 10:55:06.077
* @version 1.0
*/
@Data
@SuperBuilder
@NoArgsConstructor
@Schema(description = "系统角色查询")
public class SysRoleQuery extends SysRoleQueryBase {
}