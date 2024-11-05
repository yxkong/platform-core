package com.github.platform.core.cache.infra.service.impl;

import com.github.platform.core.common.service.IRedisReceiveMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

/**
 * redis订阅消息动态添加channel，用于项目启动后添加指定channel的监听
 * @author: yxkong
 * @date: 2023/9/8 3:11 PM
 * @version: 1.0
 */
//@Slf4j
//public class RedisMessageListenerService {
//    private RedisMessageListenerContainer redisMessageListenerContainer;
//
//    public RedisMessageListenerService(RedisMessageListenerContainer redisMessageListenerContainer) {
//        this.redisMessageListenerContainer = redisMessageListenerContainer;
//    }
//
//    /**
//     * 添加监听通道
//     * @param redisReceiveMessageService 指定实现bean
//     * @param channel 指定channel
//     */
//    public void addChannelListener(IRedisReceiveMessageService redisReceiveMessageService, String channel) {
//        redisMessageListenerContainer.addMessageListener(new MessageListenerAdapter(redisReceiveMessageService), new ChannelTopic(channel));
//    }
//
//    public void removeChannelListener(IRedisReceiveMessageService redisReceiveMessageService, String channel) {
//        redisMessageListenerContainer.removeMessageListener(new MessageListenerAdapter(redisReceiveMessageService), new ChannelTopic(channel));
//    }
//}
