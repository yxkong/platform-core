package com.github.platform.core.cache.infra.configuration;

import com.github.platform.core.cache.infra.configuration.properties.CacheProperties;
import com.github.platform.core.cache.infra.interceptor.RepeatSubmitInterceptor;
import com.github.platform.core.cache.infra.service.ICacheService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

import static org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type.SERVLET;

/**
 * Cache模块web配置
 * @author yxkong
 */
@Configuration
@ConditionalOnWebApplication(type = SERVLET)
public class CacheWebConfiguration implements WebMvcConfigurer{

    @Resource
    private ICacheService cacheService;
    @Resource CacheProperties properties;
    /**
     * 拦截器配置
     * @param registry
     */
    @Override
    @ConditionalOnClass(ICacheService.class)
    public void addInterceptors(InterceptorRegistry registry) {
        //添加防重拦截器
        registry.addInterceptor(new RepeatSubmitInterceptor(cacheService,properties)).addPathPatterns("/**");
    }
}
