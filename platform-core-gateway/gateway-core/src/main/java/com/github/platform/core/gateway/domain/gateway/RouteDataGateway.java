package com.github.platform.core.gateway.domain.gateway;

import org.springframework.cloud.gateway.route.RouteDefinition;
/**
 * 网关数据来源
 *
 * @author: yxkong
 * @date: 2021/12/7 7:56 PM
 * @version: 1.0
 */
public interface RouteDataGateway {
    /**
     * 获取初始路由信息
     * @return
     */
    void initAll();

    /**
     * 新增路由
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
     * 刷新网关
     */
    void refresh();


}
