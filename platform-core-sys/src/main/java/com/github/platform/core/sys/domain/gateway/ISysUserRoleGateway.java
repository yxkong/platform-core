package com.github.platform.core.sys.domain.gateway;

import com.github.platform.core.sys.domain.common.entity.SysUserRoleBase;
import com.github.platform.core.sys.domain.dto.SysUserRoleDto;

import java.util.List;
import java.util.Set;

/**
 * 用户角色关联
 * @Author: yxkong
 * @Date: 2024/11/25
 * @version: 1.0
 */
public interface ISysUserRoleGateway {
	/**
	 * 查询角色和用户的关联关系
	 * @param record
	 * @return List<SysUserBase>
	 */
	List<SysUserRoleDto> findListBy(SysUserRoleBase record);

	/**
     * 新增用户角色
     * @param userId 用户id
     * @param tenantId 租户id
     * @param roleKeys 角色key集合
     */
    void addUserRole(Long userId,Integer tenantId, Set<String> roleKeys);

}
