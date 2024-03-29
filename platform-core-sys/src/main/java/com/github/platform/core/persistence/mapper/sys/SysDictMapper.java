package com.github.platform.core.persistence.mapper.sys;

import java.util.Map;
import java.util.List;

import com.github.platform.core.sys.domain.common.entity.SysDictBase;
import com.github.platform.core.sys.domain.dto.SysDictDto;
import org.apache.ibatis.annotations.Param;
/**
 * @author 自定义代码生成器
 * @time 2023-04-02 12:13:46
 * @version 1.0
 *
 **/
public interface SysDictMapper  {

	/**
	 * 插入实体
	 * @param record
	 * @return
	 */
	int insert(SysDictBase record);
	/**
	* 通过主键id 更新实体
	* @param record
	* @return 1成功  其它失败
	*/
	int updateById(SysDictBase record);
	/**
	 * 通过主键id 获取实体对象
	 * @param id
	 * @return
	 */
	SysDictBase findById(Long id);
	/**
	* 通过主键ids 获取多个实体对象(最多200条)
	* @param ids
	* @return
	*/
	List<SysDictBase> findByIds(@Param("ids") Long[] ids);
	/**
	 * 通过map参数获取列表
	 * @param params
	 * @return List<SysDictBase>
	 */
	List<SysDictBase> findList(Map<String,Object> params);
	/**
	* 通过实体查询
	* @param record
	* @return List<SysDictDto>
	*/
	List<SysDictDto> findDtoListBy(SysDictBase record);
	/**
	* 通过实体查询
	* @param record
	* @return List<SysDictBase>
	*/
	List<SysDictBase> findListBy(SysDictBase record);
	/**
	 * 通过map参数获取 总数
	 * @param params
	 * @return int
	 */
	int findListCount(Map<String,Object> params);

	/**
	 * 通过实体统计
	 * @param record
	 * @return
	 */
	int findListByCount(SysDictBase record);
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
	 * 查询对应的dictType中有无对应的key或label
	 * @param dictType
	 * @param key
	 * @param label
	 * @return
	 */
	int isExistDict(@Param("dictType")String dictType,@Param("key") String key,@Param("label") String label,@Param("id") Long id);
}