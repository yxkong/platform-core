package com.github.platform.core.common.service;

import com.github.platform.core.standard.entity.event.DomainEvent;

/**
 * 事件发布接口
 * @Author: yxkong
 * @Date: 2024/12/13
 * @version: 1.0
 */
public interface EventSender {
    /**
     * 发布事件
     * @param event 事件实体
     * @param topic  主题
     * @param key  key
     */
    boolean send(DomainEvent event, String topic,String key);
}
