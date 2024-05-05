package com.github.platform.core.sys.infra.service.sys.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.platform.core.auth.util.LoginUserInfoUtil;
import com.github.platform.core.persistence.mapper.sys.SysRoleMapper;
import com.github.platform.core.persistence.mapper.sys.SysRoleMenuMapper;
import com.github.platform.core.standard.util.LocalDateTimeUtil;
import com.github.platform.core.sys.domain.common.entity.SysRoleBase;
import com.github.platform.core.sys.domain.common.entity.SysRoleMenuBase;
import com.github.platform.core.sys.domain.dto.SysRoleDto;
import com.github.platform.core.sys.infra.service.sys.ISysRoleMenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 自定义代码生成器
 * @time 2023-02-07 16:09:37
 * @version 1.0
 *
 **/
@Service("sysRoleMenuService")
public class SysRoleMenuServiceImpl  implements ISysRoleMenuService {

	@Resource
	private SysRoleMenuMapper sysRoleMenuMapper;
	@Resource
	private SysRoleMapper sysRoleMapper;

	@Override
	public int insert(SysRoleMenuBase record){
		return sysRoleMenuMapper.insert(record);
	}

	@Override
	public int updateById(SysRoleMenuBase record){
		return sysRoleMenuMapper.updateById(record);
	}

	@Override
	public SysRoleMenuBase findById(Long id){
		return sysRoleMenuMapper.findById(id);
	}

	@Override
	public List<SysRoleMenuBase> findByIds(Long[] ids){
		return sysRoleMenuMapper.findByIds(ids);
	}

	@Override
	public List<SysRoleMenuBase> findListBy(SysRoleMenuBase record){
		return  sysRoleMenuMapper.findListBy(record);
	}

	@Override
	public PageInfo<SysRoleMenuBase> findPageInfo(SysRoleMenuBase record,int pageNum,int pageSize){
		PageHelper.startPage(pageNum,pageSize);
		List<SysRoleMenuBase> list = sysRoleMenuMapper.findListBy(record);
		return new PageInfo<>(list);
	}

	@Override
	public int findListByCount(SysRoleMenuBase record){
		return  sysRoleMenuMapper.findListByCount(record);
	}

	@Override
	public int deleteByRoleId(Long roleId){
		return	sysRoleMenuMapper.deleteByRoleId(roleId);
	}

	@Override
	public int deleteByRoleIds(Long[] roleIds) {
		return sysRoleMenuMapper.deleteByRoleIds(roleIds);
	}

	@Override
	public int deleteByRoleKey(String roleKey) {
		return sysRoleMenuMapper.deleteByRoleKey(roleKey);
	}

	@Override
	public int insertList(Collection<Long> menuIds, SysRoleDto dto, Integer tenantId){
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