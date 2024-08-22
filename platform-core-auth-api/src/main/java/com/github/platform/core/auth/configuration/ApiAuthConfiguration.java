package com.github.platform.core.auth.configuration;

import com.github.platform.core.auth.configuration.properties.AuthProperties;
import com.github.platform.core.auth.resolver.ApiAuthCacheResolver;
import com.github.platform.core.cache.domain.constant.CacheConstant;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * springboot api权限相关的扩展点
 *
 * @author: yxkong
 * @date: 2022/11/30 10:42 下午
 * @version: 1.0
 */
@Configuration
public class ApiAuthConfiguration {

    @Bean
    public CacheResolver apiAuthCacheResolver(@Qualifier(CacheConstant.cacheManager)CacheManager cacheManager, AuthProperties authProperties) {
        return new ApiAuthCacheResolver(cacheManager, authProperties);
    }

}
