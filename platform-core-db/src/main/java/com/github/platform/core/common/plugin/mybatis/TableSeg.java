package com.github.platform.core.common.plugin.mybatis;


import java.lang.annotation.*;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface TableSeg {
	/**
	 * 表名
	 * 
	 * @return
	 */
	public String tableName();

	/**
	 * 
	 * 分表类型
	 * 
	 * @return
	 */
	public int nums();

	/**
	 * 根据什么字段分表
	 * 
	 * @return
	 */
	public String shardBy();


}
