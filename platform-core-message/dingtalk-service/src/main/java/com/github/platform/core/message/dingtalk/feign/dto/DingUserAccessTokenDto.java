package com.github.platform.core.message.dingtalk.feign.dto;

import lombok.Data;

/**
 * 获取个人token响应实体
 * @author: yxkong
 * @date: 2024/1/19 13:43
 * @version: 1.0
 */
@Data
public class DingUserAccessTokenDto {
    /**生成的accessToken*/
    private String accessToken;
    /**生成的refresh_token。可以使用此刷新token，定期的获取用户的accessToken*/
    private String refreshToken;
    /**所选企业corpId*/
    private String corpId;
    /**accessToken的过期时间，单位秒*/
    private Long expireIn;
}
