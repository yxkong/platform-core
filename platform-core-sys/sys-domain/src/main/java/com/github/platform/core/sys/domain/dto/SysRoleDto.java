package com.github.platform.core.sys.domain.dto;

import com.github.platform.core.sys.domain.common.entity.SysRoleBase;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
* 系统角色传输实体
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-15 10:55:06.077
* @version 1.0
*/
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
@Schema(description = "系统角色传输实体")
public class SysRoleDto extends SysRoleBase{
    @Schema(description ="权限id列表")
    private List<Long> menuIds;
}