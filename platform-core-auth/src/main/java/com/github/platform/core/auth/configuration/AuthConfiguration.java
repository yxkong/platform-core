package com.github.platform.core.auth.configuration;

import com.github.platform.core.auth.configuration.properties.AuthProperties;
import com.github.platform.core.auth.service.ILoginTokenService;
import com.github.platform.core.auth.service.impl.DefaultLoginTokenServiceImpl;
import com.github.platform.core.auth.service.impl.DefaultTokenServiceImpl;
import com.github.platform.core.auth.service.ITokenService;
import com.github.platform.core.cache.infra.service.ICacheService;
import com.github.platform.core.common.constant.SpringBeanNameConstant;
import com.github.platform.core.common.constant.SpringBeanOrderConstant;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * springboot相关的扩展点
 *
 * @author: yxkong
 * @date: 2022/11/30 10:42 下午
 * @version: 1.0
 */
@Configuration
public class AuthConfiguration {

    @ConditionalOnMissingBean(ITokenService.class)
    @Order(SpringBeanOrderConstant.TOKEN_SERVICE)
    @Bean(SpringBeanNameConstant.TOKEN_SERVICE)
    public ITokenService tokenService(AuthProperties authProperties, ICacheService cacheService) {
        return new DefaultTokenServiceImpl(authProperties, cacheService);
    }

    @ConditionalOnMissingBean(ILoginTokenService.class)
    @Order(SpringBeanOrderConstant.LOGIN_TOKEN_SERVICE)
    @Bean(SpringBeanNameConstant.LOGIN_TOKEN_SERVICE)
    public ILoginTokenService loginTokenService(AuthProperties authProperties, ICacheService cacheService) {
        return new DefaultLoginTokenServiceImpl(authProperties, cacheService);
    }
}
