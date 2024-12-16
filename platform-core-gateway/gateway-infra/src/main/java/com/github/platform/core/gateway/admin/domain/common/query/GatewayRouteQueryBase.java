package com.github.platform.core.gateway.admin.domain.common.query;

import com.github.platform.core.common.entity.query.PageQueryBaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
/**
 * 网关路由查询基类
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
@Schema(description = "网关路由查询")
public class GatewayRouteQueryBase extends PageQueryBaseEntity {
    /** 路由id,对应gateway的route的id */
    @Schema(description = "路由id,对应gateway的route的id")
    private String routeId;
    /** 归属网关 */
    @Schema(description = "归属网关")
    private String gateway;
    /** 模块 */
    @Schema(description = "模块")
    private String module;
}