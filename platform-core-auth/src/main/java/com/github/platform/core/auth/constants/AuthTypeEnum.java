package com.github.platform.core.auth.constants;

import lombok.Getter;

/**
 *
 * @author: yxkong
 * @date: 2023/4/20 4:52 PM
 * @version: 1.0
 */
@Getter
public enum AuthTypeEnum {
    API("api", "api模块"),
    SYS("sys", "后台管理模块");

    AuthTypeEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    private String type;
    private String desc;

    public static AuthTypeEnum of(String type) {
        for (AuthTypeEnum value : AuthTypeEnum.values()) {
            if (type.equals(value.getType())) {
                return value;
            }
        }
        return null;
    }
}
