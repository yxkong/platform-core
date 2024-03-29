package com.github.platform.core.persistence.entity.code;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * information_schema中的tables
 *
 **/

@SuppressWarnings("serial")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ColumnsBase {

    /**
     * 库名
     */
    private String tableSchema;
    /**
     * 表名
     */
    private String tableName;
    /**
     * 字段名
     */
    private String columnName;
    /**
     * 是否为null
     */
    private String isNullable;
    /**
     * 数据类型
     */
    private String dataType;
    /**
     * 字段注释
     */
    private String columnComment;
    /**
     * 是否主键
     */
    private String columnKey;
    /**
     * 备注
     */
    private String extra;

}