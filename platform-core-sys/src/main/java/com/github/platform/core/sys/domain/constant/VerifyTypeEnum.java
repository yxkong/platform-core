package com.github.platform.core.sys.domain.constant;

import lombok.Getter;
/**
 * 验证类型
 * @author: yxkong
 * @date: 2023/1/4 1:32 PM
 * @version: 1.0
 */
@Getter
public enum VerifyTypeEnum {
    DEFAULT("default","默认策略，直接通过"),
    CAPTCHA("captcha","图形验证"),
    SMS("sms","短信验证"),
    SLIDING("sliding","滑块验证");
    private String type;
    private String desc;
    VerifyTypeEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public static VerifyTypeEnum of(String type){
        for (VerifyTypeEnum typeEnum:VerifyTypeEnum.values()){
            if (typeEnum.getType().equalsIgnoreCase(type)){
                return typeEnum;
            }
        }
        return VerifyTypeEnum.DEFAULT;
    }
}
