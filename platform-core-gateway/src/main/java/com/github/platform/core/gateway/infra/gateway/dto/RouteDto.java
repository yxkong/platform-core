package com.github.platform.core.gateway.infra.gateway.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.cloud.gateway.route.RouteDefinition;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 网关路由规则，解析yaml文件需要
 * @author: yxkong
 * @date: 2021/12/17 3:46 PM
 * @version: 1.0
 */
@Setter
@Getter
public class RouteDto {

    private List<RouteDefinition> routes;

    /**
     * 添加路由信息
     * @param routeDefinition
     */
    public void addRoute(RouteDefinition routeDefinition) {
        if (Objects.isNull(this.routes)){
            this.routes = new ArrayList<>();
        }
        routes.add(routeDefinition);
    }

}
