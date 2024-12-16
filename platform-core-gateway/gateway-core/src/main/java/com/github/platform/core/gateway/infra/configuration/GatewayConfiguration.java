package com.github.platform.core.gateway.infra.configuration;

import com.alibaba.cloud.nacos.NacosConfigManager;
import com.github.platform.core.cache.infra.configuration.properties.CacheProperties;
import com.github.platform.core.cache.infra.service.ICacheService;
import com.github.platform.core.common.constant.PropertyConstant;
import com.github.platform.core.common.constant.SpringBeanNameConstant;
import com.github.platform.core.common.constant.SpringBeanOrderConstant;
import com.github.platform.core.gateway.admin.domain.gateway.IGatewayRouteConditionGateway;
import com.github.platform.core.gateway.admin.domain.gateway.IGatewayRouteGateway;
import com.github.platform.core.gateway.domain.gateway.RouteDataGateway;
import com.github.platform.core.gateway.infra.configuration.properties.NacosRouteProperties;
import com.github.platform.core.gateway.infra.configuration.properties.PlatformGatewayProperties;
import com.github.platform.core.gateway.infra.filter.GrayReactiveLoadBalancerFilter;
import com.github.platform.core.gateway.infra.gateway.impl.MysqlDynamicRouteGatewayImpl;
import com.github.platform.core.gateway.infra.gateway.impl.NacosDynamicRouteGatewayImpl;
import com.github.platform.core.gateway.infra.service.IRouteOperatorService;
import com.github.platform.core.gateway.infra.service.impl.RedisGrayRuleGatewayImpl;
import com.github.platform.core.loadbalancer.gateway.IGrayRuleQueryGateway;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;

/**
 * 网关初始化配置
 *
 * @author: yxkong
 * @date: 2021/12/17 4:02 PM
 * @version: 1.0
 */
@Configuration
public class GatewayConfiguration {
    /**
     * 默认灰度规则实现
     * @param cacheService
     * @param properties
     * @return
     */
    @Bean(name = SpringBeanNameConstant.GRAY_RULE_GATEWAY)
    @Order(SpringBeanOrderConstant.GRAY_RULE_DEFAULT)
    @ConditionalOnMissingBean(name = SpringBeanNameConstant.GRAY_RULE_GATEWAY)
    public IGrayRuleQueryGateway redisGrayRule(ICacheService cacheService, CacheProperties properties){
        return new RedisGrayRuleGatewayImpl(cacheService,properties);
    }
    @Bean
    @ConditionalOnProperty(name = PropertyConstant.CON_GATEWAY_GRAY_ENABLED,havingValue = "true",matchIfMissing = false)
    public GlobalFilter grayReactiveLoadBalancerFilter(LoadBalancerClientFactory clientFactory, IGrayRuleQueryGateway grayRuleService, Environment environment){
        return new GrayReactiveLoadBalancerFilter(clientFactory,grayRuleService,environment);
    }

    @Bean(name = SpringBeanNameConstant.ROUTE_DATA_GATEWAY)
    @ConditionalOnMissingBean(name = SpringBeanNameConstant.ROUTE_DATA_GATEWAY)
    @ConditionalOnProperty(name = PropertyConstant.CON_GATEWAY_ROUTE_TYPE,havingValue = "nacos",matchIfMissing = false)
    public RouteDataGateway nacosDynamicRouteGateway(NacosConfigManager nacosConfigManager, NacosRouteProperties routeProperties, IRouteOperatorService routeOperatorService){
        return new NacosDynamicRouteGatewayImpl(nacosConfigManager,routeProperties,routeOperatorService);
    }
    @Bean(name = SpringBeanNameConstant.ROUTE_DATA_GATEWAY)
    @ConditionalOnMissingBean(name = SpringBeanNameConstant.ROUTE_DATA_GATEWAY)
    @ConditionalOnProperty(name = PropertyConstant.CON_GATEWAY_ROUTE_TYPE,havingValue = "mysql",matchIfMissing = false)
    public RouteDataGateway mysqlDynamicRouteGateway(IRouteOperatorService routeOperatorService, IGatewayRouteGateway gatewayRouteGateway, IGatewayRouteConditionGateway gatewayRouteConditionGateway, PlatformGatewayProperties gatewayProperties){
        return new MysqlDynamicRouteGatewayImpl(routeOperatorService,gatewayRouteGateway,gatewayRouteConditionGateway,gatewayProperties);
    }


//    @Bean
//    @Order(SpringBeanOrderConstant.ZERO)
//    public GlobalFilter sentinelGatewayFilter() {
//        return new SentinelGatewayFilter(-2);
//    }
//
//    @Bean
//    @Order(SpringBeanOrderConstant.HIGHEST_PRECEDENCE+1)
//    public SentinelGatewayBlockExceptionHandler sentinelGatewayBlockExceptionHandler() {
//        return new SentinelGatewayBlockExceptionHandler(viewResolvers, serverCodecConfigurer);
//    }
}
