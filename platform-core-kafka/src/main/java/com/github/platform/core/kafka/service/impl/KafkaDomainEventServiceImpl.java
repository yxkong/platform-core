package com.github.platform.core.kafka.service.impl;

import com.github.platform.core.common.service.AbstractDomainEventService;
import com.github.platform.core.common.utils.JsonUtils;
import com.github.platform.core.standard.entity.event.MsgContent;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * kafka件发送事件
 * @author: yxkong
 * @date: 2023/4/17 11:30 AM
 * @version: 1.0
 */
@Slf4j
public class KafkaDomainEventServiceImpl extends AbstractDomainEventService {

    private KafkaTemplate<Object, Object> kafkaTemplate;
    private String serviceName;

    public KafkaDomainEventServiceImpl(KafkaTemplate<Object, Object> kafkaTemplate, String serviceName, ApplicationContext applicationContext) {
        this.kafkaTemplate = kafkaTemplate;
        this.serviceName = serviceName;
        this.applicationContext = applicationContext;
    }

    @Override
    public void publishMq(MsgContent msgContent, String topic) {
        publishUseKafka(msgContent, topic, null);
    }

    @Override
    public void publishMq(MsgContent msgContent, String topic, String key) {
        publishUseKafka(msgContent, topic, key);
    }
    @Override
    public void batchPublishMq(List<MsgContent> list, String topic) {
        if (Objects.isNull(list)){
            return;
        }
        for (MsgContent msgContent :list){
            publishUseKafka(msgContent,topic,null);
        }
    }

    @Override
    public void batchPublishMq(Map<String, MsgContent> maps, String topic) {
        for (Map.Entry<String, MsgContent> entry : maps.entrySet()) {
            publishUseKafka(entry.getValue(), entry.getKey());
        }
    }

    @Override
    public void publishMq(String content, String topic) {
        kafkaTemplate.send(topic, content).addCallback(result -> {
        }, e -> log.error("发送kafka数据失败!msg:{}, topic = {}", content, topic, e));
    }

    public void publishUseKafka(MsgContent msgContent, String topic) {
        publishUseKafka(msgContent, topic, null);
    }

    private void publishUseKafka(MsgContent msgContent, String topic, String key) {
        msgContent.setServiceName(serviceName);
        String data = JsonUtils.toJson(msgContent);
        ListenableFuture<SendResult<Object, Object>> listenableFuture = null;
        if (StringUtils.isBlank(key)) {
            listenableFuture = kafkaTemplate.send(topic, data);
        } else {
            listenableFuture = kafkaTemplate.send(topic, key, data);
        }
        listenableFuture.addCallback((t) -> log.info("发送kafka数据成功.topic={},msg={}", topic, data),
                throwable -> log.error("发送kafka数据失败.topic={},msg={}", topic, data, throwable));
    }

}
