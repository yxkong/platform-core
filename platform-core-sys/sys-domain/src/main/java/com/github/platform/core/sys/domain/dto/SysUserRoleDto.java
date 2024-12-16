package com.github.platform.core.sys.domain.dto;

import com.github.platform.core.sys.domain.common.entity.SysUserRoleBase;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 系统用户和角色关联传输实体
 * @Author: yxkong
 * @Date: 2024/11/25
 * @version: 1.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
@Schema(description = "系统角色菜单传输实体")
public class SysUserRoleDto extends SysUserRoleBase {
}
