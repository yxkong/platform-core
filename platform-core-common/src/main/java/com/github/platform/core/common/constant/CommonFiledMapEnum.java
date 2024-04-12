package com.github.platform.core.common.constant;

import lombok.Getter;

/**
 * 公共字段映射枚举
 * @author: yxkong
 * @date: 2024/4/2 19:38
 * @version: 1.0
 */
@Getter
public enum CommonFiledMapEnum {
    TENANT_ID("tenantId","tenant_id"),
    CREATE_TIME("createTime","create_time"),
    UPDATE_TIME("updateTime","update_time"),
    CREATE_BY("createBy","create_by"),
    UPDATE_BY("updateBy","update_by"),
    STATUS("status","status"),
    DEPT_ID("deptId","dept_id"),
    ;

    CommonFiledMapEnum(String javaFiled, String dbFiled) {
        this.javaFiled = javaFiled;
        this.dbFiled = dbFiled;
    }

    private String javaFiled;
    private String dbFiled;
}
