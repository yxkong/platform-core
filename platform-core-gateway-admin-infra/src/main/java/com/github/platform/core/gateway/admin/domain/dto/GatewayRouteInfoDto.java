package com.github.platform.core.gateway.admin.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 网关路由传输实体
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author yxkong
 * @date 2024-08-13 14:58:53.823
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "网关路由传输实体")
public class GatewayRouteInfoDto {
    private GatewayRouteDto routeBasic;
    private List<GatewayRouteConditionDto> conditions;
}