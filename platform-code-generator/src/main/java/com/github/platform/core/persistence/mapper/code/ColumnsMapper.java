package com.github.platform.core.persistence.mapper.code;

import com.github.platform.core.persistence.entity.code.ColumnsBase;

import java.util.List;

/**
 * @author 自定义代码生成器
 * @time 2023-04-25 11:42:55
 * @version 1.0
 *
 **/
public interface ColumnsMapper {

	/**
	* 通过实体查询
	* @param record
	* @return List<ColumnsBase>
	*/
	List<ColumnsBase> findListBy(ColumnsBase record);

}