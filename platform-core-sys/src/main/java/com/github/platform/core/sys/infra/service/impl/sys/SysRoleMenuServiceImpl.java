package com.github.platform.core.sys.infra.service.impl.sys;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.platform.core.persistence.mapper.sys.SysRoleMenuMapper;
import com.github.platform.core.sys.domain.common.entity.SysRoleMenuBase;
import com.github.platform.core.sys.infra.service.sys.ISysRoleMenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

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
	public int insertList(Collection<Long> menuIds, Long roleId, Integer tenantId){
		List<SysRoleMenuBase> list = new ArrayList<>(menuIds.size());
		for (Long menuId : menuIds) {
			SysRoleMenuBase roleMenuDO = SysRoleMenuBase.builder()
					.menuId(menuId)
					.roleId(roleId)
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
	public int deleteByRolesAndMenuId(Long[] roleIds, Long menuId) {
		return sysRoleMenuMapper.deleteByRolesAndMenuId(roleIds,menuId);
	}

}