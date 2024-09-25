package com.github.platform.core.auth.service;

import com.github.platform.core.auth.configuration.properties.AuthProperties;
import com.github.platform.core.standard.constant.SymbolConstant;

/**
 * 网关token服务，由各个服务实现
 * @Author: yxkong
 * @Date: 2024/9/5
 * @version: 1.0
 */
public interface IGatewayTokenService {
    /**
     * 获取登录信息，优先从缓存中获取，
     * <br> 缓存中没有则从数据库获取，然后缓存,实现自动续期
     * @param token
     * @return
     */
    String getLoginInfoStr(String token);

    /**
     * 获取缓存key
     * @param login 登录配置
     * @param token token的key
     * @return redis中缓存的key
     */
    default String getTokenKey(AuthProperties.Login login, String token){
        return  String.format("%s:%s",login.getToken(),token);
    }

    /**
     * 获取用户的缓存key
     * @param login 登录配置
     * @param tenantId 租户
     * @param loginName 登录用户名
     * @return redis中缓存的key
     */
    default String getMappingKey(AuthProperties.Login login,Integer tenantId, String loginName){
        return login.getUserTokenMapping() +tenantId+ SymbolConstant.colon+loginName;
    }
}
