package com.github.platform.core.gateway.admin.adapter.api.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 路由增删改
 * @Author: yxkong
 * @Date: 2024/8/16
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GatewayRouteInfoCmd {
    /**
     * 路由配置
     */
    private GatewayRouteCmd routeBasic;
    /**
     * 路由条件
     */
    private List<GatewayRouteConditionCmd> conditions;
}
