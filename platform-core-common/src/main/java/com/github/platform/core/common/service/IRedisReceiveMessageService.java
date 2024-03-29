package com.github.platform.core.common.service;

import org.apache.commons.lang3.tuple.Pair;

/**
 * redis 监听接口
 * @author: yxkong
 * @date: 2023/9/8 3:13 PM
 * @version: 1.0
 */
public interface IRedisReceiveMessageService {
    /**
     * 接收消息,方法名不能修改，修改得在往RedisMessageListenerContainer 中塞入的时候，显示赋值
     * @param message
     * @return
     */
    Pair<Boolean,String> handleMessage(String message);
}
