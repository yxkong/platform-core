package com.github.platform.core.persistence.mapper.sys;

import java.util.Map;
import java.util.List;

import com.github.platform.core.sys.domain.common.entity.SysDictTypeBase;
import org.apache.ibatis.annotations.Param;
/**
 * @author 自定义代码生成器
 * @time 2023-04-02 12:13:46
 * @version 1.0
 *
 **/
public interface SysDictTypeMapper  {

	/**
	 * 插入实体
	 * @param record
	 * @return
	 */
	int insert(SysDictTypeBase record);
	/**
	* 通过主键id 更新实体
	* @param record
	* @return 1成功  其它失败
	*/
	int updateById(SysDictTypeBase record);
	/**
	 * 通过主键id 获取实体对象
	 * @param id
	 * @return
	 */
	SysDictTypeBase findById(Long id);
	/**
	* 通过主键ids 获取多个实体对象(最多200条)
	* @param ids
	* @return
	*/
	List<SysDictTypeBase> findByIds(@Param("ids") Long[] ids);

	/**
	* 通过实体查询
	* @param record
	* @return List<SysDictTypeBase>
	*/
	List<SysDictTypeBase> findListBy(SysDictTypeBase record);

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
	 * 校验字典类型称是否唯一
	 *
	 * @param dictType 字典类型
	 * @return 结果
	 */
	SysDictTypeBase checkDictTypeUnique(String dictType);

	/**
	 * 是否存在对应的类型或名称的字典
	 * 用于新增时判断
	 * @param type
	 * @param name
	 * @return
	 */
    int isExistDictTypeInsert(@Param("type")String type, @Param("name")String name);

	/**
	 * 用于更新时判断
	 * @param id
	 * @param name
	 * @return
	 */
    int isExistDictTypeUpdate(@Param("id")Long id, @Param("name")String name);
}