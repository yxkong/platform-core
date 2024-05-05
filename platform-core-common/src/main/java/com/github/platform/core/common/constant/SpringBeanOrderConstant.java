package com.github.platform.core.common.constant;

import org.springframework.core.Ordered;

/**
 * spring bean的排序常量(用于功能封装)
 * @author: yxkong
 * @date: 2022/11/24 6:39 下午
 * @version: 1.0
 */
public interface SpringBeanOrderConstant extends Ordered {
    /**domainEvent服务实现的默认排序*/
    int DOMAIN_SERVICE_DEFAULT = 10;
    /**domainEvent服务kafka的排序*/
    int DOMAIN_SERVICE_KAFKA = 0;


    /**发布服务默认实现*/
    int PUBLISH_SERVICE_DEFAULT =  0;
    /**发布服务redis实现*/
    int PUBLISH_SERVICE_REDIS = PUBLISH_SERVICE_DEFAULT-1;
    /**
     * 排序
     * */
    int REDIS_STARTER = -1000;

    /**
     * 灰度规则加载顺序，默认default
     */
    int GRAY_RULE_DEFAULT = 100;
    /**
     * 灰度规则加载顺序，db优先默认
     */
    int GRAY_RULE_DB = 99;
    /**
     * loginInfo切面排序
     */
    int AUTH_FILTER_ORDER = -900;



    /**
     * 登录验证切面
     */
    int LOGIN_ORDER = -998;
    int ZERO = 0;

    int GLOBAL_EXCEPTION = -100 ;
    int DB_EXCEPTION = -101 ;
    /**
     * 优先web返回
     */
    int GLOBAL_EXCEPTION_WEB = -101 ;
    /**cache 加载顺序 redis增强最高*/
    int CACHE_REDIS_ENHANCE = -1;
    /**cache 加载顺序*/
    int CACHE_REDIS = 0;
    int TOKEN_SERVICE = 99 ;
    int LOGIN_TOKEN_SERVICE = 100 ;
    int LOGIN_TOKEN_DB_SERVICE = 99 ;
    int LOGIN_INFO_ASPECT = -101;
    int AUTHORIZE_ASPECT = -100;

    /** 需要在LOGIN_INFO_ASPECT 之后*/
    int FLOWABLE_FILTER = -99;

    int FILTER_SLEUTH = -999;
    int FILTER_HTTP_TRACE_LOG = -998;
    /**
     * RouteToRequestUrlFilter 是10000
     *  系统默认的ReactiveLoadBalancerClientFilter order是10150
     *  得在这两个之间
     */
    int LOAD_BALANCER_CLIENT_FILTER_ORDER = 10100;
    int GATEWAY_TRACING_REQUEST = -3;
    int GATEWAY_TRACING_RESPONSE = -1;
    int GATEWAY_TRACING = -10;
    int GATEWAY_AUTH_API = -100;
    int GATEWAY_AUTH_SYS = -100;

    /**DB初始化排序*/
    int DB_INIT = -1000;
    /**job启动排序*/
    int JOB = -900;
}
