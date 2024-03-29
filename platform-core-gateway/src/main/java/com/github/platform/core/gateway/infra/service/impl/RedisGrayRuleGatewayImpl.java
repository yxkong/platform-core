package com.github.platform.core.gateway.infra.service.impl;

import com.github.platform.core.cache.infra.configuration.properties.CacheProperties;
import com.github.platform.core.cache.infra.service.ICacheService;
import com.github.platform.core.common.utils.JsonUtils;
import com.github.platform.core.loadbalancer.domain.entity.GrayRuleEntity;
import com.github.platform.core.loadbalancer.gateway.IGrayRuleQueryGateway;

import java.util.Objects;

/**
 * 默认不配置直接返回null，配置直接使用redis
 * @author: yxkong
 * @date: 2023/4/14 5:48 PM
 * @version: 1.0
 */
public class RedisGrayRuleGatewayImpl implements IGrayRuleQueryGateway {
    private ICacheService cacheService;
    private CacheProperties properties;

    public RedisGrayRuleGatewayImpl(ICacheService cacheService, CacheProperties properties) {
        this.cacheService = cacheService;
        this.properties = properties;
    }

    @Override
    public GrayRuleEntity findOne() {
        if (Objects.isNull(properties.getGrayRule())){
            return null;
        }
        String str = (String)cacheService.get(properties.getGrayRule());
        return JsonUtils.fromJson(str,GrayRuleEntity.class);
    }
}
