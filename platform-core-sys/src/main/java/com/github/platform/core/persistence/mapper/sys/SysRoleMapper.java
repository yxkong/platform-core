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
	* 通过实体查询
	* @param record
	* @return List<SysRoleBase>
	*/
	List<SysRoleBase> findListBy(SysRoleBase record);

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
	 * 根据角色名称和租户查询
	 * @param roleName
	 * @param key
	 * @param tenantId
	 * @return
	 */
    SysRoleBase findByNameOrKeyAndTenant(@Param("name") String roleName,@Param("key") String key, @Param("tenantId") Integer tenantId);

	/**
	 *
	 * @param id
	 * @param roleName
	 * @param key
	 * @param tenantId
	 * @return
	 */
	SysRoleBase findByExist(@Param("id") Long id, @Param("name") String roleName, @Param("key") String key,  @Param("tenantId")Integer tenantId);
}