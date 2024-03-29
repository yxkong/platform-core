package com.github.platform.core.gateway.infra.gateway.impl;

import com.github.platform.core.gateway.domain.gateway.RouteDataGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.route.RouteDefinition;

/**
 * 网关动态路由mysql实现
 * TODO 待实现
 * @author: yxkong
 * @date: 2021/12/7 8:00 PM
 * @version: 1.0
 */
@Slf4j
public class MysqlDynamicRouteGatewayImpl implements RouteDataGateway {
    public MysqlDynamicRouteGatewayImpl() {
    }

    @Override
    public void initAll() {
    }

    @Override
    public boolean add(RouteDefinition routeDefinition) {
        return false;
    }

    @Override
    public Boolean delete(String id) {
        return null;
    }

    @Override
    public void refresh() {

    }
}
