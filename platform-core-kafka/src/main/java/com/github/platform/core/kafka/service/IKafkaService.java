package com.github.platform.core.kafka.service;

import org.apache.commons.lang3.tuple.Pair;

/**
 * kafka 推送实现
 * @author: yxkong
 * @date: 2024/5/19 13:31
 * @version: 1.0
 */
public interface IKafkaService {
    /**
     * 异步推送
     * @param topic 推送的topic
     * @param key 分区key
     * @param data 数据
     */
    void asyncSend(String topic, String key, String data);

    /**
     * 同步推送
     * @param topic
     * @param key
     * @param data
     */
    Pair<Boolean,String> syncSend(String topic, String key, String data);
}
