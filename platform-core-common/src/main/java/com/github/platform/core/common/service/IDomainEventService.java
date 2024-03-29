package com.github.platform.core.common.service;

import com.github.platform.core.standard.entity.event.MsgContent;

import java.util.List;
import java.util.Map;

/**
 * 领域事件服务
 * @author: yxkong
 * @date: 2023/4/17 11:10 AM
 * @version: 1.0
 */
public interface IDomainEventService {
    /**
     * 发布事件， 使用spring的Listener
     * @param domainEvent
     */
    void publish(Object domainEvent);

    /**
     * 批量发布事件，使用spring的Listener
     * @param domainEvents
     */
    void batchPublish(List<Object> domainEvents);

    /**
     * 使用消息中间件发送事件
     * @param msgContent 事件
     * @param topic 消息topic
     */
    void publishMq(MsgContent msgContent, String topic);

    /**
     * 使用消息中间件发送事件
     * @param msgContent 事件
     * @param topic 消息topic
     * @param key 该事件对应的key
     */
    void publishMq(MsgContent msgContent,String topic, String key);

    /**
     * 使用消息中间件批量发送事件(不需要对应的key)
     * @param list
     * @param topic
     */
    void batchPublishMq(List<MsgContent> list,String topic);

    /**
     * 使用消息中间件批量发送事件（需要使用key）
     * @param list
     * @param topic
     */
    void batchPublishMq(Map<String, MsgContent> list, String topic);

    void publishMq(String content, String topic);

}
