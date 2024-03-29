package com.github.platform.core.sms.infra.factory;

import com.github.platform.core.sms.domain.entity.SmsRouteEntity;
import com.github.platform.core.sms.infra.service.ISmsRouterService;

import java.util.Map;

/**
 * 短信工厂
 * @author: yxkong
 * @date: 2022/6/23 10:26 AM
 * @version: 1.0
 */
public class SmsFactory {

    /**
     * 创建路由服务
     * @param route
     * @return
     */
    public static ISmsRouterService createRouter(SmsRouteEntity route, Map<String,ISmsRouterService> map) {
        if (route.isRoute()){
            //TODO 根据路由规则，匹配对应的实现,目前只有一个路由服务，就直接返回默认
        }
        //默认路由服务为兜底服务
        return map.get(ISmsRouterService.DEFAULT_ROUTER);
    }

}
