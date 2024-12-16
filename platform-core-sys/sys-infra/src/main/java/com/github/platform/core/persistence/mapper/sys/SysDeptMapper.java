package com.github.platform.core.persistence.mapper.sys;

import java.util.Map;
import java.util.List;

import com.github.platform.core.sys.domain.common.entity.SysDeptBase;
import org.apache.ibatis.annotations.Param;
/**
 * @author 自定义代码生成器
 * @time 2023-04-02 12:13:46
 * @version 1.0
 *
 **/
public interface SysDeptMapper  {

	/**
	 * 插入实体
	 * @param record
	 * @return
	 */
	int insert(SysDeptBase record);
	/**
	* 通过主键id 更新实体
	* @param record
	* @return 1成功  其它失败
	*/
	int updateById(SysDeptBase record);
	/**
	 * 通过主键id 获取实体对象
	 * @param id
	 * @return
	 */
	SysDeptBase findById(Long id);
	/**
	* 通过主键ids 获取多个实体对象(最多200条)
	* @param ids
	* @return
	*/
	List<SysDeptBase> findByIds(@Param("ids") Long[] ids);
	/**
	 * 通过map参数获取列表
	 * @param params
	 * @return List<SysDeptBase>
	 */
	List<SysDeptBase> findList(Map<String,Object> params);
	/**
	* 通过实体查询
	* @param record
	* @return List<SysDeptBase>
	*/
	List<SysDeptBase> findListBy(SysDeptBase record);
	/**
	 * 通过map参数获取 总数
	 * @param params
	 * @return int
	 */
	int findListCount(Map<String,Object> params);
	/**
	 * 通过实体查询
	 * @param record
	 * @return int
	 */
	int findListByCount(SysDeptBase record);
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
	 * 是否存在部门
	 * @param id
	 * @param parentId
	 * @param deptName
	 * @return
	 */
	int isExistDept(@Param("id")Long id,@Param("parentId")Long parentId, @Param("deptName")String deptName);
}