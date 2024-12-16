package com.github.platform.core.persistence.mapper.sys;

import com.github.platform.core.sys.domain.common.entity.SysRoleMenuBase;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * @author 自定义代码生成器
 * @time 2023-04-02 12:13:46
 * @version 1.0
 *
 **/
public interface SysRoleMenuMapper  {


	/**
	 * 通过主键id 获取实体对象
	 * @param id
	 * @return
	 */
	List<SysRoleMenuBase> findByRoleId(Long id);

	/**
	* 通过主键ids 获取多个实体对象(最多200条)
	* @param ids
	* @return
	*/
	List<SysRoleMenuBase> findByRoleIds(@Param("ids") Long[] ids);

	/**
	 * 通过实体查询
	 * @param record
	 * @return
	 */
	int findListByCount(SysRoleMenuBase record);
	/**
	* 通过主键id 删除
	* @param roleId
	* @return
	*/
	int deleteByRoleId(@Param("roleId")Long roleId);

	/**
	 * 批量删除
	 * @param roleIds
	 * @return
	 */
	int deleteByRoleIds(@Param("roleIds")Long[] roleIds);


	/**
	 * 批量插入
	 * @param list
	 * @return
	 */
	int insertList(@Param("list")List<SysRoleMenuBase> list);

	/**
	 * 删除指定菜单对应角色的数据
	 * @param roleKeys
	 * @param menuId
	 * @return
	 */
    int deleteByRolesAndMenuId(@Param("roleKeys")String[] roleKeys, @Param("menuId")Long menuId);


	/**
	 * 根据角色删除权限
	 * @param roleKey
	 * @return
	 */
    int deleteByRoleKey(@Param("roleKey")String roleKey);

}