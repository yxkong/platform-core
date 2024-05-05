package com.github.platform.core.gateway.infra.filter;

import com.github.platform.core.auth.service.ITokenService;
import com.github.platform.core.gateway.domain.gateway.ConfigGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * api项目权限拦截器工厂
 * @author: yxkong
 * @date: 2023/4/21 6:57 PM
 * @version: 1.0
 */
@Component
@Slf4j
public class ApiAuthGatewayFilterFactory extends AbstractGatewayFilterFactory<SysAuthGatewayFilterFactory.Config> {
    @Resource(name = "apiConfigGateway")
    private ConfigGateway configGateway;
    @Resource(name = "apiTokenService")
    private ITokenService tokenService;
    public ApiAuthGatewayFilterFactory() {
        super(SysAuthGatewayFilterFactory.Config.class);
        if (log.isDebugEnabled()){
            log.debug("apiAuth init");
        }

    }
    @Override
    public GatewayFilter apply(SysAuthGatewayFilterFactory.Config config) {
        if (!config.isEnabled()){
            return ((exchange, chain) ->{return chain.filter(exchange);});
        }
        return new ApiAuthFilter(configGateway,tokenService);
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("enabled");
    }
}
