package com.github.platform.core.standard.constant;

import lombok.Getter;

/**
 * 消息通道类型
 * @Author: yxkong
 * @Date: 2024/12/6
 * @version: 1.0
 */
@Getter
public enum MessageNoticeChannelTypeEnum {
    WECHAT("wechat","微信"),
    EMAIL("email","邮件"),
    SMS("sms","短信"),
    DING_TALK("dingTalk","钉钉"),
    FEI_SHU("feiShu","飞书"),
    ;

    private String type;
    private String desc;

    MessageNoticeChannelTypeEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }
    public static MessageNoticeChannelTypeEnum getByType(String code) {
        for (MessageNoticeChannelTypeEnum item : values()) {
            if (item.getType().equals(code)) {
                return item;
            }
        }
        return DING_TALK;
    }

}
