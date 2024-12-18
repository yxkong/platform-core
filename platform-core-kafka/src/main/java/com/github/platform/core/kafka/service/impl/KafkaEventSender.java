package com.github.platform.core.kafka.service.impl;

import com.github.platform.core.common.service.EventSender;
import com.github.platform.core.common.utils.JsonUtils;
import com.github.platform.core.kafka.service.IKafkaService;
import com.github.platform.core.standard.entity.event.DomainEvent;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * kafka发送
 * @Author: yxkong
 * @Date: 2024/12/16
 * @version: 1.0
 */
@Service("kafkaEventSender")
public class KafkaEventSender implements EventSender {
    @Resource
    private IKafkaService kafkaService;

    @Override
    public boolean send(DomainEvent event, String topic, String key) {
        Pair<Boolean, String> pair = kafkaService.syncSend(topic, key, JsonUtils.toJson(event));
        return pair.getKey();
    }
}