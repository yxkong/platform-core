package com.github.platform.core.cache.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 配置项通用实体
 * @author: yxkong
 * @date: 2023/2/10 11:20 AM
 * @version: 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConfigEntity implements Serializable {
    /**配置项key*/
    private String key;
    /**配置项名称*/
    private String name;
    /**配置项值*/
    private String value;
    /**配置项值的类型，以java类型为准*/
    private String type;
    /**配置项状态*/
    private int status;
    private String module;

}
