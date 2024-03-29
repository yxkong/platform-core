package com.github.platform.core.persistence.entity.code;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * information_schema中的tables
 *
 **/

@SuppressWarnings("serial")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class TablesBase {

    /**
     * 库名
     */
    private String tableSchema;
    /**
     * 表名
     */
    private String tableName;
    /**
     * 引擎
     */
    private String engine;
    /**
     * 表备注
     */
    private String tableComment;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;

}