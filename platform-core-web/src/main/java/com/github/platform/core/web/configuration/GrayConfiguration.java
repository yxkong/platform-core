package com.github.platform.core.web.configuration;

import com.github.platform.core.loadbalancer.holder.RequestHeaderHolder;
import com.github.platform.core.web.interceptor.GrayHandlerInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 灰度配置
 * @author: yxkong
 * @date: 2024/6/26 16:42
 * @version: 1.0
 */
@Configuration
@ConditionalOnClass(RequestHeaderHolder.class)
public class GrayConfiguration implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new GrayHandlerInterceptor());
    }
}
