package com.github.platform.core.auth.resolver;

import com.github.platform.core.auth.configuration.properties.AuthProperties;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.CacheOperationInvocationContext;
import org.springframework.cache.interceptor.CacheResolver;

import java.util.Collection;
import java.util.Collections;

/**
 * sys 自定义缓存key解析器
 * @Author: yxkong
 * @Date: 2024/8/12
 * @version: 1.0
 */
public class SysAuthCacheResolver implements CacheResolver {
    private final CacheManager cacheManager;
    private final AuthProperties authProperties;

    public SysAuthCacheResolver(CacheManager cacheManager, AuthProperties authProperties) {
        this.cacheManager = cacheManager;
        this.authProperties = authProperties;
    }
    @Override
    public Collection<? extends Cache> resolveCaches(CacheOperationInvocationContext<?> context) {
        String cacheName = authProperties.getSys().getLogin().getToken();
        return Collections.singleton(cacheManager.getCache(cacheName));
    }
}
