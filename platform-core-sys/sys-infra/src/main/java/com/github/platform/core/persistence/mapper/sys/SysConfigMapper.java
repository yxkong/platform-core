package com.github.platform.core.persistence.mapper.sys;

import com.github.platform.core.sys.domain.common.entity.SysConfigBase;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
/**
 * @author 自定义代码生成器
 * @time 2023-04-02 12:13:46
 * @version 1.0
 *
 **/
public interface SysConfigMapper  {

	/**
	 * 插入实体
	 * @param record
	 * @return
	 */
	int insert(SysConfigBase record);
	/**
	* 通过主键id 更新实体
	* @param record
	* @return 1成功  其它失败
	*/
	int updateById(SysConfigBase record);
	/**
	 * 通过主键id 获取实体对象
	 * @param id
	 * @return
	 */
	SysConfigBase findById(Long id);
	/**
	* 通过主键ids 获取多个实体对象(最多200条)
	* @param ids
	* @return
	*/
	List<SysConfigBase> findByIds(@Param("ids") Long[] ids);
	/**
	 * 通过map参数获取列表
	 * @param params
	 * @return List<SysConfigBase>
	 */
	List<SysConfigBase> findList(Map<String,Object> params);
	/**
	* 通过实体查询
	* @param record
	* @return List<SysConfigBase>
	*/
	List<SysConfigBase> findListBy(SysConfigBase record);
	/**
	 * 通过map参数获取 总数
	 * @param params
	 * @return int
	 */
	int findListCount(Map<String,Object> params);
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