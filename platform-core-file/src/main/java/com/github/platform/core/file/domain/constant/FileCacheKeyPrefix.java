package com.github.platform.core.file.domain.constant;

import com.github.platform.core.standard.constant.BaseCacheKeyPrefix;
import lombok.Getter;

/**
 * 文件模块缓存key前缀
 * @Author: yxkong
 * @Date: 2024/7/29
 * @version: 1.0
 */
@Getter
public enum FileCacheKeyPrefix implements BaseCacheKeyPrefix {
    //文件缓存
    FILE("p:s:f"),
    ;

    private final String prefix;
    private final String withColon;

    FileCacheKeyPrefix(String prefix) {
        this.prefix = handlerPrefix(prefix);
        this.withColon = handlerPrefixWithColon(prefix);
    }

}
