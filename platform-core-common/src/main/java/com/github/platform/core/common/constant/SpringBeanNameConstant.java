package com.github.platform.core.common.constant;

/**
 * 统一springBean的名称，bean有多个实现，但只有一个生效的时候
 * @author: yxkong
 * @date: 2023/4/14 6:12 PM
 * @version: 1.0
 */
public interface SpringBeanNameConstant {
    /**
     * 灰度规则网关名称
     */
    String GRAY_RULE_GATEWAY = "grayRuleGateway";
    /**路由数据服务*/
    String ROUTE_DATA_GATEWAY = "routeDataGateway";
    /**
     * 缓存服务，根据实现实现
     */
    String CACHE_SERVICE = "cacheService";
    /**领域事件服务*/
    String DOMAIN_EVENT_SERVICE = "domainEventService";
    /**发布服务*/
    String PUBLISH_SERVICE = "publishService";
    String REDIS_PUBLISH_SERVICE = "redisPublishService";
    /**令牌服务*/
    String TOKEN_SERVICE = "tokenService";
    /**登录令牌服务*/
    String LOGIN_TOKEN_SERVICE = "loginTokenService";

}
