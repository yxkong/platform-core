package com.github.platform.core.gateway.admin.domain.constant;

import com.github.platform.core.standard.constant.OptTypeEnum;
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
    ROUTE_ADD(OptTypeEnum.ADD,"routeAddEventHandler"),
    //更新路由
    ROUTE_UPDATE(OptTypeEnum.UPDATE,"routeUpdateEventHandler"),
    //删除路由
    ROUTE_DELETE(OptTypeEnum.DELETE,"routeDeleteEventHandler"),
    ;

    GatewayOptEnum(OptTypeEnum optType, String handlerBeanName) {
        this.optType = optType;
        this.handlerBeanName = handlerBeanName;
    }

    private final OptTypeEnum optType;
    private final String handlerBeanName;
}
