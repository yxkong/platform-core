package com.github.platform.core.gateway.admin.domain.gateway;

import com.github.platform.core.cache.domain.constant.CacheConstant;
import com.github.platform.core.gateway.admin.domain.constant.GatewayCacheKeyPrefix;
import com.github.platform.core.gateway.admin.domain.context.GatewayRouteConditionContext;
import com.github.platform.core.gateway.admin.domain.dto.GatewayRouteConditionDto;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

/**
 * 网关路由条件网关层，隔离模型和实现
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author yxkong
 * @date 2024-08-13 14:58:55.453
 * @version 1.0
 */
public interface IGatewayRouteConditionGateway {


    String PREFIX = GatewayCacheKeyPrefix.CONDITION.getPrefix();
    String PREFIX_COLON = GatewayCacheKeyPrefix.CONDITION.getWithColon();
    /**缓存名称*/
    String CACHE_NAME = CacheConstant.c12h;
    /**
    * 批量新增
    * @param conditions 条件列表
    * @return 返回结果
    */
    void insertList(List<GatewayRouteConditionContext> conditions,Long routeId);

    /**
     * 批量更新
     * @param conditions
     */
    void updateList(List<GatewayRouteConditionContext> conditions,Long routeId);
    /**
    * 新增网关路由条件
    * @param context 新增上下文
    * @return 返回结果
    */
    GatewayRouteConditionDto insert(GatewayRouteConditionContext context);
    /**
    * 根据id查询网关路由条件明细
    * @param id 主键
    * @return 结果
    */
    GatewayRouteConditionDto findById(Long id);
    /**
    * 修改网关路由条件
    * @param context 修改上下文
    * @return 更新结果
    */
    Pair<Boolean, GatewayRouteConditionDto> update(GatewayRouteConditionContext context);
    /**
    * 根据id删除网关路由条件记录
    * @param context 删除上下文（由id改为上下文了，是为了兼容cache的多处理）
    * @return 删除结果
    */
    int delete(GatewayRouteConditionContext context);

    /**
     * 批量删除
     * @param ids
     * @return
     */
    int deleteByIds(Long[] ids);

    /**
     * 根据路由id查询条件
     * @param routeId
     * @return
     */
    List<GatewayRouteConditionDto> findByRouteId(Long routeId);
}
