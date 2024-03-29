package com.github.platform.core.gateway.infra.gateway.dto;

import org.springframework.cloud.gateway.route.RouteDefinition;

import java.util.List;

/**
 * 网关路由规则，解析yaml文件需要
 * @author: yxkong
 * @date: 2021/12/17 3:46 PM
 * @version: 1.0
 */
public class RouteDto {

    private List<RouteDefinition> routes;

    public List<RouteDefinition> getRoutes() {
        return routes;
    }

    public void setRoutes(List<RouteDefinition> routes) {
        this.routes = routes;
    }
}
