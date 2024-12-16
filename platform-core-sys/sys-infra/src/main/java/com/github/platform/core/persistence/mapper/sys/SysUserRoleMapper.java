package com.github.platform.core.persistence.mapper.sys;

import java.util.Map;
import java.util.List;

import com.github.platform.core.sys.domain.common.entity.SysUserRoleBase;
import org.apache.ibatis.annotations.Param;
/**
 * @author 自定义代码生成器
 * @time 2023-04-02 12:13:46
 * @version 1.0
 *
 **/
public interface SysUserRoleMapper  {

	/**
	 * 插入实体
	 * @param record
	 * @return
	 */
	int insert(SysUserRoleBase record);

	/**
	 * 批量插入
	 * @param list
	 * @return
	 */
	int insertList(@Param("list") List<SysUserRoleBase> list);
	/**
	* 通过主键id 更新实体
	* @param record
	* @return 1成功  其它失败
	*/
	int updateById(SysUserRoleBase record);
	/**
	 * 通过主键id 获取实体对象
	 * @param id
	 * @return
	 */
	SysUserRoleBase findById(Long id);
	/**
	* 通过主键ids 获取多个实体对象(最多200条)
	* @param ids
	* @return
	*/
	List<SysUserRoleBase> findByIds(@Param("ids") Long[] ids);

	/**
	* 通过实体查询
	* @param record
	* @return List<SysUserRoleBase>
	*/
	List<SysUserRoleBase> findListBy(SysUserRoleBase record);
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

}