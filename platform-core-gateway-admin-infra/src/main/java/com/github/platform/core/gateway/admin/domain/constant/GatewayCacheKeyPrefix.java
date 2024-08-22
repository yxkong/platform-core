package com.github.platform.core.gateway.admin.domain.constant;

import com.github.platform.core.standard.constant.BaseCacheKeyPrefix;
import lombok.Getter;

/**
 * 网关模块缓存key前缀
 * @Author: yxkong
 * @Date: 2024/7/29
 * @version: 1.0
 */
@Getter
public enum GatewayCacheKeyPrefix implements BaseCacheKeyPrefix {
    //网关路由缓存key
    ROUTE("p:g:r"),
    //网关路由条件缓存key
    CONDITION("p:g:c"),
    ;

    private final String prefix;
    private final String withColon;

    GatewayCacheKeyPrefix(String prefix) {
        this.prefix = handlerPrefix(prefix);
        this.withColon = handlerPrefixWithColon(prefix);
    }
}
