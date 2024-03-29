package com.github.platform.core.sys.infra.service.sys;

import com.github.pagehelper.PageInfo;
import com.github.platform.core.sys.domain.common.entity.SysRoleMenuBase;

import java.util.Collection;
import java.util.List;

/**
 * @author 自定义代码生成器
 * @time 2023-02-07 16:09:37
 * @version 1.0
 *
 **/

public interface ISysRoleMenuService  {

	/**
	 * 插入实体
	 * @param record
	 * @return
	 */
	int insert(SysRoleMenuBase record);
	/**
	* 通过主键id 更新实体
	* @param record
	* @return 1成功  其它失败
	*/
	int updateById(SysRoleMenuBase record);
	/**
	 * 通过主键id 获取实体对象
	 * @param id
	 * @return
	 */
	SysRoleMenuBase findById(Long id);
	/**
	* 通过主键ids 获取多个实体对象(最多200条)
	* @param ids
	* @return
	*/
	List<SysRoleMenuBase> findByIds(Long[] ids);
	/**
	* 通过map参数获取列表
	* @param record 查询参数
	* @return List<SysRoleMenuBase>
	*/
	List<SysRoleMenuBase> findListBy(SysRoleMenuBase record);
	/**
	* 通过实体参数获取列表分页
	* @param record
	* @param pageNum 页数
	* @param pageSize 每页大小
	* @return PageInfo<SysRoleMenuBase>
	*/
	PageInfo<SysRoleMenuBase> findPageInfo(SysRoleMenuBase record,int pageNum,int pageSize);

	/**
	 * 通过实体参数获取总数
	 * @param record
	 * @return
	 */
	int findListByCount(SysRoleMenuBase record);
	/**
	* 通过主键id 删除
	* @param id
	* @return
	*/
	int deleteByRoleId(Long id);
	/**
	 * 批量删除
	 * @param roleIds
	 * @return
	 */
	int deleteByRoleIds(Long[] roleIds);

	/**
	 * 批量插入
	 * @param menuIds 菜单id
	 * @param roleId 角色id
	 * @param tenantId 租户
	 * @return
	 */
	int insertList(Collection<Long> menuIds, Long roleId, Integer tenantId);

	/**
	 * 查询菜单是否有对应角色关系
	 * @param menuId
	 * @return
	 */
    boolean checkMenuExistRole(Long menuId);

	/**
	 * 根据角色和菜单删除对应关系
	 * @param roleIds
	 * @param menuId
	 * @return
	 */
	int deleteByRolesAndMenuId(Long[] roleIds, Long menuId);

}