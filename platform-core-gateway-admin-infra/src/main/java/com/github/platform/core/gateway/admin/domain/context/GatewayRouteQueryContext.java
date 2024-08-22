package com.github.platform.core.gateway.admin.domain.context;

import com.github.platform.core.gateway.admin.domain.common.query.GatewayRouteQueryBase;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
/**
 * 网关路由查询上下文
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author yxkong
 * @date 2024-08-13 14:58:53.823
 * @version 1.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class GatewayRouteQueryContext extends GatewayRouteQueryBase {
}
