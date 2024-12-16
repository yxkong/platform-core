package com.github.platform.core.gateway.admin.infra.convert;

import com.github.platform.core.gateway.admin.domain.common.entity.GatewayRouteConditionBase;
import com.github.platform.core.gateway.admin.domain.context.GatewayRouteConditionContext;
import com.github.platform.core.gateway.admin.domain.dto.GatewayRouteConditionDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mappings;

import java.util.ArrayList;
import java.util.List;
/**
 * 网关路由条件基础设施层转换器
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author yxkong
 * @date 2024-08-13 14:58:55.453
 * @version 1.0
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface GatewayRouteConditionInfraConvert {
    /**
    * 数据库实体列表转dto列表
    * @param list 数据库实体列表
    * @return dto列表
    */
    List<GatewayRouteConditionDto> toDtos(List<GatewayRouteConditionBase> list);
    /**
    * 数据库实体转dto
    * @param entity 数据库实体
    * @return dto
    */
    @Mappings({
        @Mapping(target = "strId", expression = "java(com.github.platform.core.common.utils.SignUtil.getStrId(entity.getId()))"),
    })
    GatewayRouteConditionDto toDto(GatewayRouteConditionBase entity);

    /**
    * 实体上下文转数据库实体
    * @param context 实体上下文
    * @return 数据库实体
    */
    GatewayRouteConditionBase toGatewayRouteConditionsBase(GatewayRouteConditionContext context);
    /**
    * dto转数据库实体
    * @param dto 传输实体
    * @return 数据库实体
    */
    GatewayRouteConditionBase toGatewayRouteConditionsBase(GatewayRouteConditionDto dto);

    /**
     * 转list
     * @param conditions
     * @param routeId 路由编号
     * @return
     */
    default List<GatewayRouteConditionBase> toConditions(List<GatewayRouteConditionContext> conditions,Long routeId){
        ArrayList<GatewayRouteConditionBase> result = new ArrayList<>();
        for (GatewayRouteConditionContext item : conditions) {
            GatewayRouteConditionBase conditionBase = toGatewayRouteConditionsBase(item);
            conditionBase.setRouteId(routeId);
            result.add(conditionBase);
        }
        return result;
    }
}