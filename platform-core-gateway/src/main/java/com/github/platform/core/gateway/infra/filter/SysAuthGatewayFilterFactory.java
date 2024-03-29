package com.github.platform.core.gateway.infra.filter;

import com.github.platform.core.auth.configuration.properties.AuthProperties;
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
 *
 * @author: yxkong
 * @date: 2023/4/21 5:41 PM
 * @version: 1.0
 */
@Component
@Slf4j
public class SysAuthGatewayFilterFactory extends AbstractGatewayFilterFactory<SysAuthGatewayFilterFactory.Config> {
    @Resource(name = "sysConfigGateway")
    private ConfigGateway configGateway;
    @Resource
    private ITokenService tokenService;
    @Resource
    private AuthProperties authProperties;
    public SysAuthGatewayFilterFactory() {
        super(Config.class);
        if (log.isDebugEnabled()){
            log.debug("SysAuth init");
        }

    }

    @Override
    public GatewayFilter apply(Config config) {
        if (!config.isEnabled()){
            return ((exchange, chain) ->{return chain.filter(exchange);});
        }
        return new SysAuthFilter(configGateway,tokenService,authProperties);
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("enabled");
    }

    public static class Config{
        //是否开启验证
        private boolean enabled = false;

        public Config() {
        }

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }
    }
}
