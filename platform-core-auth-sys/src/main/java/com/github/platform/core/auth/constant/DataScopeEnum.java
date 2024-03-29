package com.github.platform.core.auth.constant;

import lombok.Getter;

/**
 * @author yxkong
 * @create 2023/2/8 下午4:12
 * @desc DataScopeEnum
 */
@Getter
public enum DataScopeEnum {

    TENANT(0, "租户数据权限", "0"),

    DEPT(1, "本部门数据权限", "1"),

    DEPT_DOWN(2, "本部门及下级部门数据权限", "2"),

    USER(3, "本人数据权限", "3");

    private Integer scope;
    private String desc;
    private String val;

    DataScopeEnum(Integer scope, String desc, String val) {
        this.scope = scope;
        this.desc = desc;
        this.val = val;
    }

    public static DataScopeEnum of(String val) {
        for (DataScopeEnum dataScope : values()) {
            if (dataScope.getVal().equalsIgnoreCase(val)) {
                return dataScope;
            }
        }
        return null;
    }
}
