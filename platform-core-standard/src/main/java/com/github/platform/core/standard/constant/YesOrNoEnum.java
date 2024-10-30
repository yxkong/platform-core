package com.github.platform.core.standard.constant;

import lombok.Getter;

import java.util.Objects;

/**
 * 是否枚举
 * @author: yxkong
 * @date: 2022/6/25 12:11 PM
 * @version: 1.0
 */
public enum YesOrNoEnum {
    YES(1,"是"),
    NO(0,"否");

    YesOrNoEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private Integer code;
    private String desc;
    public static YesOrNoEnum of(Integer code) {
        for (YesOrNoEnum value : YesOrNoEnum.values()) {
            if (value.getCode().equals(code) ) {
                return value;
            }
        }
        return null;
    }

    public static boolean isYes(Integer code){
        return Objects.equals(YesOrNoEnum.YES.getCode(),code);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
