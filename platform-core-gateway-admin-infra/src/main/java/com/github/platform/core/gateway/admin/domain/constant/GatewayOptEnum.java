package com.github.platform.core.gateway.admin.domain.constant;

import lombok.Getter;

/**
 * 网关操作
 * @Author: yxkong
 * @Date: 2024/8/20
 * @version: 1.0
 */
@Getter
public enum GatewayOptEnum {
    //新增路由
    ROUTE_ADD("routeAdd","routeAddEventHandler"),
    //更新路由
    ROUTE_UPDATE("routeUpdate","routeUpdateEventHandler"),
    //删除路由
    ROUTE_DELETE("routeDelete","routeDeleteEventHandler"),
    ;

    GatewayOptEnum(String opt, String handlerBeanName) {
        this.opt = opt;
        this.handlerBeanName = handlerBeanName;
    }

    private final String opt;
    private final String handlerBeanName;
}
