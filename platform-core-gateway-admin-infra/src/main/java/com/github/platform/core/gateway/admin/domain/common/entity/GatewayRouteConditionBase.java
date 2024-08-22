package com.github.platform.core.gateway.admin.domain.common.entity;

import com.github.platform.core.common.entity.BaseAdminEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 网关路由条件模型实体
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author yxkong
 * @date 2024-08-13 14:58:55.453
 * @version 1.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
public class GatewayRouteConditionBase extends BaseAdminEntity   {
    /** 路由id,对应route表id */
    @Schema(description = "路由id,对应route表id")
    protected Long routeId;
    /** 类型：predicate- 断言，filter- 过滤器 */
    @Schema(description = "类型：predicate- 断言，filter- 过滤器")
    protected String type;
    /** 名称 */
    @Schema(description = "名称")
    protected String name;
    /** 参数 */
    @Schema(description = "参数")
    protected String args;
}
