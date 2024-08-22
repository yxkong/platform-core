package com.github.platform.core.standard.constant;

/**
 * 缓存前缀
 * @Author: yxkong
 * @Date: 2024/8/19
 * @version: 1.0
 */
public interface BaseCacheKeyPrefix {
    default String handlerPrefix(String key) {
        return key;
    }
    default String handlerPrefixWithColon(String key) {
        return key+SymbolConstant.colon;
    }
}
