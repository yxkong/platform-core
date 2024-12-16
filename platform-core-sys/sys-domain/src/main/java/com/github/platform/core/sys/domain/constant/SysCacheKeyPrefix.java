package com.github.platform.core.sys.domain.constant;

import com.github.platform.core.standard.constant.BaseCacheKeyPrefix;
import com.github.platform.core.standard.constant.SymbolConstant;
import lombok.Getter;

/**
 * sys模块缓存key前缀
 * @Author: yxkong
 * @Date: 2024/7/29
 * @version: 1.0
 */
@Getter
public enum SysCacheKeyPrefix implements BaseCacheKeyPrefix {

    DICT_TYPE(SysCacheConstant.DICT_TYPE_PREFIX),
    DEPT(SysCacheConstant.DEPT_PREFIX),
    CASCADE(SysCacheConstant.CASCADE_PREFIX),
    CONFIG(SysCacheConstant.CONFIG_PREFIX),
    ROLE(SysCacheConstant.ROLE_PREFIX),
    TOKEN_CACHE(SysCacheConstant.TOKEN_CACHE_PREFIX),
    USER_CONFIG(SysCacheConstant.USER_CONFIG_PREFIX),
    USER(SysCacheConstant.USER_PREFIX),
    ;

    private final String prefix;
    private final String withColon;

    SysCacheKeyPrefix(String prefix) {
        this.prefix = handlerPrefix(prefix);
        this.withColon = handlerPrefixWithColon(prefix);
    }
}
