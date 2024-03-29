package com.github.platform.core.standard.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 所有DO的基类,用于 DB对应的实体添加常用的查询条件
 */
@Data
@SuperBuilder
@NoArgsConstructor
@Deprecated
public class BaseDO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 查询开始时间
     */
//    @JsonIgnore
    private transient String searchStartTime;
    /**
     * 查询结束时间
     */
//    @JsonIgnore
    private transient String searchEndTime;
    /**
     * 数据权限
     */
//    @JsonIgnore
    private transient String dataScope;
    /** 请求参数 */
//    @JsonIgnore
    private transient Map<String, Object> params;

    public Map<String, Object> getParams() {
        if (params == null) {
            params = new HashMap<>();
        }
        return params;
    }
}
