package com.github.platform.core.gateway.infra.service.impl;

import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.LinkedHashMap;
import java.util.Map;

import static java.util.Collections.synchronizedMap;

/**
 * 自定义RouteDefinitionRepository ，gateway实现的没有clean功能
 * 正常也不应该清空
 * @author: yxkong
 * @date: 2021/12/9 7:13 PM
 * @version: 1.0
 */
@Service
public class RouteDefinitionRepositoryImpl implements RouteDefinitionRepository {
    /**
     * 定义网关路由存储对象
     */
    private final Map<String, RouteDefinition> routes = synchronizedMap(new LinkedHashMap<>());

    @Override
    public Mono<Void> save(Mono<RouteDefinition> route) {
        return route.flatMap(r -> {
            if (!StringUtils.hasText(r.getId())) {
                return Mono.error(new IllegalArgumentException("id may not be empty"));
            }
            routes.put(r.getId(), r);
            return Mono.empty();
        });
    }

    @Override
    public Mono<Void> delete(Mono<String> routeIdMono) {
        return routeIdMono.flatMap(routeId -> {
            if (routes.containsKey(routeId)) {
                routes.remove(routeId);
                return Mono.empty();
            }
            return Mono.error(new NotFoundException("RouteDefinition not found: " + routeId));
        });
    }

    @Override
    public Flux<RouteDefinition> getRouteDefinitions() {
        //获取一个副本
        Map<String, RouteDefinition> routesSafeCopy = new LinkedHashMap(this.routes);
        return Flux.fromIterable(routesSafeCopy.values());
    }
    /**
     * 清空配置
     */
    public void clear(){
        routes.clear();
    }
}
