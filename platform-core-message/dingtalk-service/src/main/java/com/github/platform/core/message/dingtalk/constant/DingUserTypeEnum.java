package com.github.platform.core.message.dingtalk.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 钉钉用户类型
 * @author: yxkong
 * @date: 2024/1/19 16:08
 * @version: 1.0
 */
@Getter
@AllArgsConstructor
public enum DingUserTypeEnum {
    mobile(1,"手机号"),
    userId(2,"钉钉用户id"),
    ;

    private Integer type;
    private String  desc;
}
