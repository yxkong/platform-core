package com.github.platform.core.cache.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 字典通用实体
 * @author: yxkong
 * @date: 2023/2/10 11:25 AM
 * @version: 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DictEntity implements Serializable {
    /**字典类型*/
    private String dictType;
    /**
     * 字符类型
     */
    private String charType;
    /**字典类型名称*/
    private String dictTypeName;
    /**字典配置键值*/
    private String key;
    /**字典配置标签*/
    private String label;
    /**样式属性*/
    private String cssClass;
    /**列表属性*/
    private String listClass;
    /**
     * 租户
     */
    private Integer tenantId;
    /**字典排序*/
    private Integer sort;
    /**字典状态(1启用 0停用)*/
    private Integer status;
    /**备注*/
    private String remark;

    public DictEntity(String key, String label) {
        this.key = key;
        this.label = label;
    }
}
