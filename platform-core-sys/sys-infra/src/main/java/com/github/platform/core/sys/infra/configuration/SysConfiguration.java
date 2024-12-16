package com.github.platform.core.sys.infra.configuration;

import com.github.platform.core.auth.configuration.properties.AuthProperties;
import com.github.platform.core.cache.infra.service.ICacheService;
import com.github.platform.core.common.constant.SpringBeanNameConstant;
import com.github.platform.core.common.constant.SpringBeanOrderConstant;
import com.github.platform.core.sys.domain.gateway.ISysTokenCacheGateway;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * sys配置
 * @author: yxkong
 * @date: 2024/4/26 21:48
 * @version: 1.0
 */
@Configuration
public class SysConfiguration {
//    @ConditionalOnMissingBean(ILoginTokenService.class)
//    @Order(SpringBeanOrderConstant.LOGIN_TOKEN_DB_SERVICE)
//    @Bean(SpringBeanNameConstant.LOGIN_TOKEN_SERVICE)
//    public ILoginTokenService loginTokenService(AuthProperties authProperties, ICacheService cacheService, ISysTokenCacheGateway sysTokenCacheGateway) {
//        return new SysLoginTokenServiceImpl(authProperties, cacheService,sysTokenCacheGateway);
//    }
}
