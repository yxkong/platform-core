package com.github.platform.core.gateway.domain.gateway;

/**
 * token 解析网关
 * @author: yxkong
 * @date: 2021/12/6 11:43 AM
 * @version: 1.0
 */
public interface TokenParseGateway {
    String getLoginToken(String token);
}
