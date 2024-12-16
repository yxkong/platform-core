package com.github.platform.core.persistence.mapper.sys;

import com.github.platform.core.sys.domain.common.entity.SysRoleBase;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
/**
 * @author 自定义代码生成器
 * @time 2023-04-02 12:13:46
 * @version 1.0
 *
 **/
public interface SysRoleMapper  {

	/**
	 * 插入实体
	 * @param record
	 * @return
	 */
	int insert(SysRoleBase record);
	/**
	* 通过主键id 更新实体
	* @param record
	* @return 1成功  其它失败
	*/
	int updateById(SysRoleBase record);
	/**
	 * 通过主键id 获取实体对象
	 * @param id
	 * @return
	 */
	SysRoleBase findById(Long id);
	/**
	* 通过主键ids 获取多个实体对象(最多200条)
	* @param ids
	* @return
	*/
	List<SysRoleBase> findByIds(@Param("ids") Long[] ids);

	/**
	 * 根据keys查询角色
	 * @param roleKeys
	 * @return
	 */
	List<SysRoleBase> findByKeys(@Param("roleKeys") String[] roleKeys,@Param("tenantId") Integer tenantId);

	/**
	 * 根据角色key查询
	 * @param roleKey
	 * @param tenantId
	 * @return
	 */
	SysRoleBase findByKey(@Param("roleKey") String roleKey,@Param("tenantId")Integer tenantId);
	/**
	* 通过实体查询
	* @param record
	* @return List<SysRoleBase>
	*/
	List<SysRoleBase> findListBy(SysRoleBase record);

	/**
	* 通过主键id 删除
	* @param id
	*/
	int deleteById(Long id);
	/**
	* 批量删除
	* @param ids
	*/
	int deleteByIds(@Param("ids")Long[] ids);

	/**
	 * 根据角色名称h或key 在指定租户中查询
	 * @param name 角色名称
	 * @param key 角色key
	 * @param tenantId 租户id
	 * @return 角色信息
	 */
    SysRoleBase findByNameOrKeyAndTenant(@Param("name") String name,@Param("key") String key, @Param("tenantId") Integer tenantId);

	/**
	 * 根据角色id + （角色名称，或者角色key）在指定z租户中查询 角色信息
	 * @param id
	 * @param name 角色名称
	 * @param key 角色key
	 * @param tenantId 租户id
	 * @return
	 */
	SysRoleBase findByExist(@Param("id") Long id, @Param("name") String name, @Param("key") String key,  @Param("tenantId")Integer tenantId);
}