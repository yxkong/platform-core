package com.github.platform.core.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 追踪记录
 * @author: yxkong
 * @date: 2024/6/7 14:01
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrackChangeRecord {
    /**字段名称*/
    private String fieldName;
    /**比较追踪*/
    private boolean compare;
    /**合并*/
    private String merge;
    /**原值*/
    private Object originalValue;
    /**新值*/
    private Object modifiedValue;
    /**业务注释*/
    private String remark;
    /**时间格式*/
    private String dateFormat;
    /**排序*/
    private int sort;
}
