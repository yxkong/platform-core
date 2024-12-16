package com.github.platform.core.gateway.admin.infra.gateway.impl;

import com.github.platform.core.cache.domain.constant.CacheConstant;
import com.github.platform.core.common.gateway.BaseGatewayImpl;
import com.github.platform.core.gateway.admin.domain.common.entity.GatewayRouteConditionBase;
import com.github.platform.core.gateway.admin.domain.context.GatewayRouteConditionContext;
import com.github.platform.core.gateway.admin.domain.dto.GatewayRouteConditionDto;
import com.github.platform.core.gateway.admin.domain.gateway.IGatewayRouteConditionGateway;
import com.github.platform.core.gateway.admin.infra.convert.GatewayRouteConditionInfraConvert;
import com.github.platform.core.persistence.mapper.gateway.admin.GatewayRouteConditionMapper;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
/**
 * 网关路由条件网关层实现
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author yxkong
 * @date 2024-08-13 14:58:55.453
 * @version 1.0
 */
@Service
public class GatewayRouteConditionGatewayImpl extends BaseGatewayImpl implements IGatewayRouteConditionGateway {

    @Resource
    private GatewayRouteConditionMapper gatewayRouteConditionsMapper;
    @Resource
    private GatewayRouteConditionInfraConvert gatewayRouteConditionsConvert;

    @Override
    @CacheEvict(cacheNames = CACHE_NAME, key = "#root.target.PREFIX_COLON+#routeId", cacheManager = CacheConstant.cacheManager)
    public void insertList(List<GatewayRouteConditionContext> conditions,Long routeId) {
        List<GatewayRouteConditionBase> list = gatewayRouteConditionsConvert.toConditions(conditions,routeId);
        gatewayRouteConditionsMapper.insertList(list);
    }

    @Override
    @CacheEvict(cacheNames = CACHE_NAME, key = "#root.target.PREFIX_COLON+#routeId", cacheManager = CacheConstant.cacheManager)
    public void updateList(List<GatewayRouteConditionContext> conditions,Long routeId) {
        List<GatewayRouteConditionBase> list = gatewayRouteConditionsConvert.toConditions(conditions,routeId);
        gatewayRouteConditionsMapper.updateList(list);
    }

    @Override
    public GatewayRouteConditionDto insert(GatewayRouteConditionContext context) {
        GatewayRouteConditionBase record = gatewayRouteConditionsConvert.toGatewayRouteConditionsBase(context);
        gatewayRouteConditionsMapper.insert(record);
        return gatewayRouteConditionsConvert.toDto(record);
    }

    @Override
    public GatewayRouteConditionDto findById(Long id) {
        if (Objects.isNull(id)){
            return null;
        }
        GatewayRouteConditionBase record = gatewayRouteConditionsMapper.findById(id);
        return gatewayRouteConditionsConvert.toDto(record);
    }

    @Override
    public Pair<Boolean, GatewayRouteConditionDto> update(GatewayRouteConditionContext context) {
        GatewayRouteConditionBase record = gatewayRouteConditionsConvert.toGatewayRouteConditionsBase(context);
        int flag = gatewayRouteConditionsMapper.updateById(record);
        return Pair.of( flag>0 , gatewayRouteConditionsConvert.toDto(record)) ;
    }

    @Override
    public int delete(GatewayRouteConditionContext context) {
        return gatewayRouteConditionsMapper.deleteById(context.getId());
    }

    @Override
    public int deleteByIds(Long[] ids) {
        return gatewayRouteConditionsMapper.deleteByIds(ids);
    }

    @Override
    @Cacheable(cacheNames = CACHE_NAME, key = "#root.target.PREFIX_COLON+#routeId", cacheManager = CacheConstant.cacheManager, unless = "#result == null || #result.isEmpty()")
    public List<GatewayRouteConditionDto> findByRouteId(Long routeId) {
        List<GatewayRouteConditionBase> listBy = gatewayRouteConditionsMapper.findListBy(GatewayRouteConditionBase.builder().routeId(routeId).build());
        return gatewayRouteConditionsConvert.toDtos(listBy);
    }
}
