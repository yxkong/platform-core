package com.github.platform.core.gateway.admin.infra.convert;

import com.github.pagehelper.PageInfo;
import com.github.platform.core.gateway.admin.domain.common.entity.GatewayRouteBase;
import com.github.platform.core.gateway.admin.domain.context.GatewayRouteContext;
import com.github.platform.core.gateway.admin.domain.context.GatewayRouteQueryContext;
import com.github.platform.core.gateway.admin.domain.dto.GatewayRouteDto;
import com.github.platform.core.standard.entity.dto.PageBean;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mappings;

import java.util.List;
/**
 * 网关路由基础设施层转换器
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author yxkong
 * @date 2024-08-13 14:58:53.823
 * @version 1.0
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface GatewayRouteInfraConvert {
    /**
    * 数据库实体列表转dto列表
    * @param list 数据库实体列表
    * @return dto列表
    */
    List<GatewayRouteDto> toDtos(List<GatewayRouteBase> list);
    /**
    * 数据库实体转dto
    * @param entity 数据库实体
    * @return dto
    */
    @Mappings({
        @Mapping(target = "strId", expression = "java(com.github.platform.core.common.utils.SignUtil.getStrId(entity.getId()))"),
    })
    GatewayRouteDto toDto(GatewayRouteBase entity);
    /**
    * 数据库分页转业务分页
    * @param pageInfo 数据库分页
    * @return 业务分页
    */
    @Mappings({
        @Mapping(target = "totalSize", source = "total"),
        @Mapping(target = "data", source = "list"),
    })
    PageBean<GatewayRouteDto> ofPageBean(PageInfo<GatewayRouteBase> pageInfo);

    /**
    * 查询上下文转数据库实体
    * @param context 数据库分页
    * @return 数据库实体
    */
    GatewayRouteBase toGatewayRoutesBase(GatewayRouteQueryContext context);
    /**
    * 实体上下文转数据库实体
    * @param context 实体上下文
    * @return 数据库实体
    */
    GatewayRouteBase toGatewayRoutesBase(GatewayRouteContext context);
    /**
    * dto转数据库实体
    * @param dto 传输实体
    * @return 数据库实体
    */
    GatewayRouteBase toGatewayRoutesBase(GatewayRouteDto dto);
}