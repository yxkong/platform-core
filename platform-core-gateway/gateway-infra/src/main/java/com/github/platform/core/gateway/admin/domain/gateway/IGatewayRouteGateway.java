package com.github.platform.core.gateway.admin.domain.gateway;

import com.github.platform.core.gateway.admin.domain.constant.GatewayCacheKeyPrefix;
import com.github.platform.core.gateway.admin.domain.context.GatewayRouteContext;
import com.github.platform.core.gateway.admin.domain.context.GatewayRouteQueryContext;
import com.github.platform.core.gateway.admin.domain.dto.GatewayRouteDto;
import com.github.platform.core.standard.entity.dto.PageBean;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

/**
 * 网关路由网关层，隔离模型和实现
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author yxkong
 * @date 2024-08-13 14:58:53.823
 * @version 1.0
 */
public interface IGatewayRouteGateway {
    /**缓存前缀*/
    String PREFIX = GatewayCacheKeyPrefix.ROUTE.getPrefix();
    /**缓存前缀加冒号*/
    String PREFIX_COLON = GatewayCacheKeyPrefix.ROUTE.getWithColon();
    /**
    * 查询网关路由列表
    * @param context 查询上下文
    * @return 分页结果
    */
    PageBean<GatewayRouteDto> query(GatewayRouteQueryContext context);
    /**
    * 根据条件查询网关路由列表
    * @param context 查询上下文
    * @return 结果
    */
    List<GatewayRouteDto> findBy(GatewayRouteContext context);
    /**
    * 新增网关路由
    * @param context 新增上下文
    * @return 返回结果
    */
    GatewayRouteDto insert(GatewayRouteContext context);
    /**
    * 根据id查询网关路由明细
    * @param id 主键
    * @return 结果
    */
    GatewayRouteDto findById(Long id);
    /**
    * 修改网关路由
    * @param context 修改上下文
    * @return 更新结果
    */
    Pair<Boolean, GatewayRouteDto> update(GatewayRouteContext context);
    /**
    * 根据id删除网关路由记录
    * @param context 删除上下文（由id改为上下文了，是为了兼容cache的多处理）
    * @return 删除结果
    */
    int delete(GatewayRouteContext context);
}
