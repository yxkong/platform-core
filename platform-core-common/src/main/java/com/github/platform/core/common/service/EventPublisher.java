package com.github.platform.core.common.service;

/**
 * 事件发布接口
 * @Author: yxkong
 * @Date: 2024/12/13
 * @version: 1.0
 */
public interface EventPublisher {
    /**
     * 发布事件
     * @param event 事件实体
     */
    void publishEvent(Object event);
}
