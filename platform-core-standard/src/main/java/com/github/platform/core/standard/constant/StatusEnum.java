package com.github.platform.core.standard.constant;

import lombok.Getter;

/**
 * 统一状态码
 *
 * @author: yxkong
 * @date: 2022/6/25 12:11 PM
 * @version: 1.0
 */
@Getter
public enum StatusEnum {
    ON(1, "启用,正常", "1"),
    OFF(0, "禁用/未启用,停用", "0"),
    ERROR(-1, "异常/取消等", "-1");


    StatusEnum(Integer status, String desc, String val) {
        this.status = status;
        this.desc = desc;
        this.val = val;
    }

    private Integer status;
    private String desc;
    private String val;

    public static StatusEnum of(Integer status) {
        for (StatusEnum value : StatusEnum.values()) {
            if (status.equals(value.getStatus())) {
                return value;
            }
        }
        return null;
    }

    public static StatusEnum of(Boolean status) {
        return status ? ON : OFF;
    }
}
