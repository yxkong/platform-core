package com.github.platform.core.gateway.admin.domain.common.query;

import com.github.platform.core.common.entity.query.PageQueryBaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
/**
 * 网关路由条件查询基类
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author yxkong
 * @date 2024-08-13 14:58:55.453
 * @version 1.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Schema(description = "网关路由条件查询")
public class GatewayRouteConditionQueryBase extends PageQueryBaseEntity {
    /** 路由id,对应route表id */
    @Schema(description = "路由id,对应route表id")
    private Long routeId;
    /** 类型：predicate- 断言，filter- 过滤器 */
    @Schema(description = "类型：predicate- 断言，filter- 过滤器")
    private String type;
}