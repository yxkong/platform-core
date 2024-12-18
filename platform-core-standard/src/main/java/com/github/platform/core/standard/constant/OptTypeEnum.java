package com.github.platform.core.standard.constant;

import lombok.Getter;

/**
 * 操作类型枚举
 * @Author: yxkong
 * @Date: 2024/11/5
 * @version: 1.0
 */
@Getter
public enum OptTypeEnum {
    ADD("add","新增"),
    UPDATE("update","修改"),
    DELETE("delete","删除"),
    SELECT("delete","删除"),
        ;
    OptTypeEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    private String type;
    private String desc;
    public static OptTypeEnum of(String type) {
        for (OptTypeEnum value : OptTypeEnum.values()) {
            if (value.getType().equals(type) ) {
                return value;
            }
        }
        return null;
    }
}
