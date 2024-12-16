package com.github.platform.core.sys.infra.gateway.impl;

import com.github.platform.core.auth.util.LoginUserInfoUtil;
import com.github.platform.core.cache.domain.constant.CacheConstant;
import com.github.platform.core.persistence.mapper.sys.SysRoleMenuMapper;
import com.github.platform.core.standard.util.LocalDateTimeUtil;
import com.github.platform.core.sys.domain.common.entity.SysRoleMenuBase;
import com.github.platform.core.sys.domain.dto.SysRoleDto;
import com.github.platform.core.sys.domain.dto.SysRoleMenuDto;
import com.github.platform.core.sys.domain.gateway.ISysRoleMenuGateway;
import com.github.platform.core.sys.infra.convert.SysRoleMenuInfraConvert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 角色菜单网关实现
 * @Author: yxkong
 * @Date: 2024/11/25
 * @version: 1.0
 */
@Service
@Slf4j
public class SysRoleMenuGatewayImpl implements ISysRoleMenuGateway {
    @Resource
	private SysRoleMenuMapper sysRoleMenuMapper;
	@Resource
	private SysRoleMenuInfraConvert roleMenuInfraConvert;
    @Override
    public int insertList(Collection<Long> menuIds, SysRoleDto dto, Integer tenantId) {
        List<SysRoleMenuBase> list = new ArrayList<>(menuIds.size());
		for (Long menuId : menuIds) {
			SysRoleMenuBase roleMenuDO = SysRoleMenuBase.builder()
					.menuId(menuId)
					.roleId(dto.getId())
					.roleKey(dto.getKey())
					.tenantId(tenantId)
					.createBy(LoginUserInfoUtil.getLoginName())
					.createTime(LocalDateTimeUtil.dateTime())
					.build();
			list.add(roleMenuDO);
		}
		return sysRoleMenuMapper.insertList(list);
    }

	@Override
	@Cacheable(cacheNames = CacheConstant.c1h, key = "#root.target.ROLE_MENU_COLON + #roleId",cacheManager = CacheConstant.cacheManager, unless = "#result == null || #result.isEmpty()")
	public List<SysRoleMenuDto> findByRoleId(Long roleId) {
		List<SysRoleMenuBase> list = sysRoleMenuMapper.findByRoleId(roleId);
		return roleMenuInfraConvert.toDtos(list);
	}

	@Override
	@CacheEvict(cacheNames = CacheConstant.c1h, key = "#root.target.ROLE_MENU_COLON + #roleId",cacheManager = CacheConstant.cacheManager)
	public int deleteByRoleId(Long roleId) {
		return sysRoleMenuMapper.deleteByRoleId(roleId);
	}

	@Override
	public int deleteByRoleKey(String roleKey) {
		return sysRoleMenuMapper.deleteByRoleKey(roleKey);
	}

	@Override
	public boolean checkMenuExistRole(Long menuId) {
		SysRoleMenuBase roleMenu = SysRoleMenuBase.builder().menuId(menuId).build();
		return sysRoleMenuMapper.findListByCount(roleMenu) > 0 ? true : false;
	}

	@Override
	public int deleteByRolesAndMenuId(String[] roleKeys, Long menuId) {
		// 根据角色key，删除所有的
		return sysRoleMenuMapper.deleteByRolesAndMenuId(roleKeys,menuId);
	}
}
