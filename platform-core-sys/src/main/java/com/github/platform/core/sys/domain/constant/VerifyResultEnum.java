package com.github.platform.core.sys.domain.constant;

import lombok.Data;
import lombok.Getter;

/**
 * @author: yxkong
 * @date: 2023/1/4 2:42 PM
 * @version: 1.0
 */
@Getter
public enum VerifyResultEnum {

    pass("pass","验证通过！"),
    refuse("refuse","验证未通过！"),
    notFound("notfound","未找到对应的验证码！")
    ;

    private String type;
    private String desc;

    VerifyResultEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }
}
