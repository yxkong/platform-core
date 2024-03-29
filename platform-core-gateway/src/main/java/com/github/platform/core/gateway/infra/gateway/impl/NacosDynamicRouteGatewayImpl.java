package com.github.platform.core.gateway.infra.gateway.impl;

import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import com.github.platform.core.gateway.domain.gateway.RouteDataGateway;
import com.github.platform.core.common.utils.YamlUtil;
import com.github.platform.core.gateway.infra.configuration.properties.NacosRouteProperties;
import com.github.platform.core.gateway.infra.gateway.dto.RouteDto;
import com.github.platform.core.gateway.infra.service.RouteOperatorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.route.RouteDefinition;

import java.util.concurrent.Executor;

/**
 * nacos 动态路由网关实现
 *
 * @author: yxkong
 * @date: 2021/12/7 8:00 PM
 * @version: 1.0
 */
@Slf4j
public class NacosDynamicRouteGatewayImpl implements RouteDataGateway {

    private NacosConfigManager nacosConfigManager;

    private NacosRouteProperties routeProperties;

    private RouteOperatorService routeOperatorService;

    public NacosDynamicRouteGatewayImpl(NacosConfigManager nacosConfigManager, NacosRouteProperties routeProperties, RouteOperatorService routeOperatorService) {
        this.nacosConfigManager = nacosConfigManager;
        this.routeProperties = routeProperties;
        this.routeOperatorService = routeOperatorService;
    }

    @Override
    public void initAll() {
        try {
            String configInfo = nacosConfigManager.getConfigService().getConfig(routeProperties.getDataId(), routeProperties.getGroup(), routeProperties.getTimeout());
            log.warn("加载网关路由信息：{}",configInfo);
            RouteDto routeDto = YamlUtil.load(configInfo, RouteDto.class);
            routeOperatorService.refresh(routeDto.getRoutes());
            log.warn("初始化网关完成");
        } catch (NacosException e) {
            log.error("init gateway is error",e);
        }
    }

    @Override
    public boolean add(RouteDefinition routeDefinition) {
        return false;
    }

    @Override
    public Boolean delete(String id) {
        return false;
    }

    @Override
    public void refresh() {
        try {
            nacosConfigManager.getConfigService().addListener(routeProperties.getDataId(), routeProperties.getGroup(), new Listener() {
                @Override
                public Executor getExecutor() {
                    return null;
                }

                @Override
                public void receiveConfigInfo(String configInfo) {
                    log.warn("监听网关配置更新：{}",configInfo);
                    RouteDto routeDto = YamlUtil.load(configInfo, RouteDto.class);
                    routeOperatorService.refresh(routeDto.getRoutes());
                    log.warn("网关更新完成：{}",configInfo);
                }
            });
        } catch (NacosException e) {
            log.error("refresh gateway is error",e);
        }

    }
}
