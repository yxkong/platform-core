package com.github.platform.core.standard.constant;

import lombok.Getter;

/**
 * 通知类型枚举
 * @Author: yxkong
 * @Date: 2024/12/17
 * @version: 1.0
 */
@Getter
public enum NoticeTypeEnum {
    GROUP("group","群"),
    WORK("work","工作通知"),
    WEB_HOOK("webHook","webHook"),
            ;

    private String type;
    private String desc;

    NoticeTypeEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }
    public static NoticeTypeEnum getByType(String code) {
        for (NoticeTypeEnum item : values()) {
            if (item.getType().equals(code)) {
                return item;
            }
        }
        return WORK;
    }
}
