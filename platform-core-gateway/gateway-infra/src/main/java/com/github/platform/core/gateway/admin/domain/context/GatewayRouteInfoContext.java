package com.github.platform.core.gateway.admin.domain.context;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 网关路由增加或修改上下文
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author yxkong
 * @date 2024-08-13 14:58:53.823
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class GatewayRouteInfoContext  {
    /**
     * 路由配置
     */
    private GatewayRouteContext routeBasic;
    /**
     * 路由条件
     */
    private List<GatewayRouteConditionContext> conditions;



}
