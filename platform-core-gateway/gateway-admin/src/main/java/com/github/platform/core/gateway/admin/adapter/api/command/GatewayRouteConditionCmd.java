package com.github.platform.core.gateway.admin.adapter.api.command;
import com.github.platform.core.gateway.admin.domain.common.entity.GatewayRouteConditionBase;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;
/**
 * 网关路由条件增加或修改
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
@Schema(description = "网关路由条件增加或修改")
public class GatewayRouteConditionCmd extends GatewayRouteConditionBase {
}
