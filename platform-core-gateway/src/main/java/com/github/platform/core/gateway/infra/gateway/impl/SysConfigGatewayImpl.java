package com.github.platform.core.gateway.infra.gateway.impl;

import com.github.platform.core.auth.configuration.properties.AuthProperties;
import com.github.platform.core.gateway.domain.gateway.ConfigGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 配置中心实现的动态路由
 * @author: yxkong
 * @date: 2021/12/6 4:08 PM
 * @version: 1.0
 */
@Service("sysConfigGateway")
@Slf4j
public class SysConfigGatewayImpl implements ConfigGateway {
    @Resource
    private AuthProperties authProperties;
    @Override
    public boolean excludeHost(String host) {
        AuthProperties.Sys sys = authProperties.getSys();
        return hostEquals(sys.getHosts(),host);
    }

    @Override
    public boolean excludeUrl(String url) {
        AuthProperties.Sys sys = authProperties.getSys();
        return urlStartWith(sys.getUrls(), url);
    }
}
