package com.github.platform.core.dingtalk.infra.rpc.external.dto;

import lombok.Data;

/**
 * 获取token响应
 * @author: yxkong
 * @date: 2024/1/19 13:43
 * @version: 1.0
 */
@Data
public class DingAccessTokenDto {
    /**生成的accessToken*/
    private String accessToken;
    /**accessToken的过期时间，单位秒*/
    private Long expireIn;
}
