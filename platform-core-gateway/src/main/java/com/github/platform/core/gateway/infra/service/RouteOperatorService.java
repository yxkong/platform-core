package com.github.platform.core.gateway.infra.service;

import org.springframework.cloud.gateway.route.RouteDefinition;

import java.util.List;

/**
 * 网关操作服务
 *
 * @author: yxkong
 * @version: 1.0
 */
public interface RouteOperatorService {
    /**
     * 新增路由（基本上是通过数据库，一条条的插入进来的）
     * @param routeDefinition
     * @return
     */
    boolean add(RouteDefinition routeDefinition);
    /**
     * 删除路由
     * @param id
     * @return
     */
    Boolean delete(String id);

    /**
     * 更新路由,先删除后新增
     * @param routeDefinition
     * @return
     */
    Boolean update(RouteDefinition routeDefinition);
    /**
     * 刷新路由
     * @param list
     * @return
     */
    public Boolean refresh(List<RouteDefinition> list);

}
