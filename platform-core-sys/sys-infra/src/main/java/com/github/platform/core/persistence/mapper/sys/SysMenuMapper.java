package com.github.platform.core.persistence.mapper.sys;

import java.util.Map;
import java.util.List;

import com.github.platform.core.sys.domain.common.entity.SysMenuBase;
import org.apache.ibatis.annotations.Param;
/**
 * @author 自定义代码生成器
 * @time 2023-04-02 12:13:46
 * @version 1.0
 *
 **/
public interface SysMenuMapper  {

	/**
	 * 插入实体
	 * @param record
	 * @return
	 */
	int insert(SysMenuBase record);
	/**
	* 通过主键id 更新实体
	* @param record
	* @return 1成功  其它失败
	*/
	int updateById(SysMenuBase record);
	/**
	 * 通过主键id 获取实体对象
	 * @param id
	 * @return
	 */
	SysMenuBase findById(Long id);
	/**
	* 通过主键ids 获取多个实体对象(最多200条)
	* @param ids
	* @return
	*/
	List<SysMenuBase> findByIds(@Param("ids") Long[] ids);
	/**
	* 通过实体查询
	* @param record
	* @return List<SysMenuBase>
	*/
	List<SysMenuBase> findListBy(SysMenuBase record);
	/**
	 * 通过实体查询
	 * @param record
	 * @return int
	 */
	int findListByCount(SysMenuBase record);
	/**
	* 通过主键id 删除
	* @param id
	* @return
	*/
	int deleteById(Long id);
	/**
	* 批量删除
	* @param ids
	* @return
	*/
	int deleteByIds(@Param("ids")Long[] ids);
	/**
	 * 查询所有的菜单
	 * @return 菜单列表
	 */
	List<SysMenuBase> findMenuAll();
	/**
	 * 根据用户id查询菜单
	 *  TODO 后续需要重构，降低连表的个数
	 * @param userId 用户id，必须填写
	 * @param flag 录用
	 * @param menu 菜单 可用字段：
	 *                菜单名称 name，模糊查询，可选
	 *                是否可见标识 visible， 可选
	 *                是否启用标识 status ,可选
	 * @return
	 */
	List<SysMenuBase> findMenuByUserId(@Param("userId") Long userId,@Param("flag") String flag, @Param("menu") SysMenuBase menu);

	/**
	 * 根据角色查询用户的菜单
	 * @param roleIds
	 * @param menu
	 * @return
	 */

	List<SysMenuBase> findMenuByRoleIds(@Param("roleIds") List<Long> roleIds, @Param("menu")  SysMenuBase menu);

	/**
	 * 获取所有可用的菜单id
	 * @return
	 */
	List<Long> findAllMenuIds(@Param("giveTenant") Integer giveTenant);


}