package com.github.platform.core.message.dingtalk.feign.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 获取用户信息
 * @author: yxkong
 * @date: 2024/4/18 14:43
 * @version: 1.0
 */
@Data
public class DingAccessUserDto {
    /**用户的钉钉昵称*/
    private String nick;
    //共用
    @JsonProperty("userid")
    private String userId;
    /**头像URL*/
    private String avatarUrl;
    /**用户的手机号*/
    private String mobile;
    /**用户的openId*/
    private String openId;
    /**用户的unionId*/
    private String unionId;
    /**钉钉的email*/
    private String email;
    /**用户的个人邮箱*/
    private String stateCode;
    /**手机号对应的国家号*/
    private String refreshToken;

}
