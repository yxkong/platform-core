package com.github.platform.core.auth.service;

import com.github.platform.core.cache.domain.constant.CacheConstant;
import org.springframework.cache.annotation.Cacheable;

/**
 * 认证
 * @author: yxkong
 * @date: 2024/4/24 21:12
 * @version: 1.0
 */
public interface IAuthorizationService {

    /**
     * 令牌认证
     * @param secretKey
     * @return
     */
    @Cacheable(cacheNames = CacheConstant.c30m, key = "'s:s'+#secretKey", cacheManager = CacheConstant.cacheManager, unless = "#result == null")
    void bearer(String secretKey);

    /**
     * 基本认证
     * @param user
     * @param pwd
     * @return
     */
    void basic(Integer tenantId,String user,String pwd);
}
