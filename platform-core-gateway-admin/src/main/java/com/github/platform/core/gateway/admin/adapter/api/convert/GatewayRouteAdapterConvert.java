package com.github.platform.core.gateway.admin.adapter.api.convert;

import com.github.platform.core.gateway.admin.adapter.api.command.GatewayRouteCmd;
import com.github.platform.core.gateway.admin.adapter.api.command.GatewayRouteInfoCmd;
import com.github.platform.core.gateway.admin.adapter.api.command.GatewayRouteQuery;
import com.github.platform.core.gateway.admin.domain.context.GatewayRouteContext;
import com.github.platform.core.gateway.admin.domain.context.GatewayRouteInfoContext;
import com.github.platform.core.gateway.admin.domain.context.GatewayRouteQueryContext;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
/**
 * 网关路由Controller到Application层的适配
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author yxkong
 * @date 2024-08-13 14:58:53.823
 * @version 1.0
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface GatewayRouteAdapterConvert {
    /**
    * 查询实体转查询上下文
    * @param query 查询实体
    * @return 查询上下文
    */
    GatewayRouteQueryContext toQuery(GatewayRouteQuery query);
    /**
    * 操作实体转操作上下文
    * @param cmd 操作实体
    * @return 操作上下文
    */
    GatewayRouteContext toContext(GatewayRouteCmd cmd);

    /**
     * 路由信息转上下文
     * @param cmd
     * @return
     */
    GatewayRouteInfoContext toRouteInfo(GatewayRouteInfoCmd cmd);
}