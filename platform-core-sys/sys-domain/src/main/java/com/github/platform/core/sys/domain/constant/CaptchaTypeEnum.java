package com.github.platform.core.sys.domain.constant;

import lombok.Getter;

/**
 * 图形验证码枚举
 * @author: yxkong
 * @date: 2023/4/3 7:08 PM
 * @version: 1.0
 */
@Getter
public enum CaptchaTypeEnum {
    MATH("math","数学验证"),
    DEFAULT("default","默认验证")
    ;

    private String type;
    private String desc;

    CaptchaTypeEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }
}
