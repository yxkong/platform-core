package com.github.platform.core.persistence.mapper.code;

import com.github.platform.core.persistence.entity.code.TablesBase;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 自定义代码生成器
 * @time 2023-04-25 11:42:55
 * @version 1.0
 *
 **/
public interface TablesMapper {

	/**
	* 通过实体查询
	* @param record
	* @return List<TablesBase>
	*/
	List<TablesBase> findListBy(TablesBase record);

//	/**
//	 * 根据表名查询
//	 * @param tableName
//	 * @return
//	 */
//	List<TablesBase> findByTableName(@Param("tableName") String tableName);
}