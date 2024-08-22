package com.github.platform.core.cache.infra.service;

/**
 * redis 事件处理器
 * @Author: yxkong
 * @Date: 2024/8/9
 * @version: 1.0
 */
public interface IRedisEventProcessor {
    /**
     * 处理事件
     * @param message 事件消息
     * @return 成功返回true，失败返回false
     */
    Boolean process(String message);
}
