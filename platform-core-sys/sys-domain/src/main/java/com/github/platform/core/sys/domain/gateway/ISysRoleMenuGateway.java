package com.github.platform.core.sys.domain.gateway;

import com.github.platform.core.sys.domain.dto.SysRoleDto;
import com.github.platform.core.sys.domain.dto.SysRoleMenuDto;

import java.util.Collection;
import java.util.List;

/**
 * 角色菜单网关
 * @Author: yxkong
 * @Date: 2024/11/25
 * @version: 1.0
 */
public interface ISysRoleMenuGateway {
	String ROLE_MENU_COLON = ISysRoleGateway.PREFIX_COLON + "m:";
    /**
	 * 批量插入
	 * @param menuIds 菜单id
	 * @param dto 角色实体
	 * @param tenantId 租户
	 * @return
	 */
	int insertList(Collection<Long> menuIds, SysRoleDto dto, Integer tenantId);

	/**
	 * 通过主键id 获取实体对象
	 * @param roleId
	 * @return
	 */
	List<SysRoleMenuDto> findByRoleId(Long roleId);

	/**
	* 通过角色id 删除
	* @param roleId 角色id
	*/
	int deleteByRoleId(Long roleId);

	/**
	 * 批量删除
	 * @param roleKey
	 * @return
	 */
	int deleteByRoleKey(String roleKey);

	/**
	 * 查询菜单是否有对应角色关系
	 * @param menuId
	 * @return
	 */
    boolean checkMenuExistRole(Long menuId);

	/**
	 * 根据角色和菜单删除对应关系
	 * @param roleKeys
	 * @param menuId 指定某个菜单，为空则全部
	 * @return
	 */
	int deleteByRolesAndMenuId(String[] roleKeys, Long menuId);
}
