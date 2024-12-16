package com.github.platform.core.message.dingtalk.feign.dto;

import lombok.Data;

/**
 * 获取企业应用token响应实体
 * @author: yxkong
 * @date: 2024/1/19 13:43
 * @version: 1.0
 */
@Data
public class DingAppAccessTokenDto {
    /**生成的accessToken*/
    private String accessToken;
    /**accessToken的过期时间，单位秒*/
    private Long expireIn;
}
