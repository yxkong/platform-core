package com.github.platform.core.kafka.service.impl;

import com.github.platform.core.common.utils.JsonUtils;
import com.github.platform.core.common.utils.StringUtils;
import com.github.platform.core.kafka.service.IKafkaService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * kafka发送实现
 * @author: yxkong
 * @date: 2024/5/19 13:35
 * @version: 1.0
 */
@Service
@Slf4j
public class KafkaServiceImpl implements IKafkaService {
    @Resource(name = "bizKafkaTemplate")
    private KafkaTemplate<String, String> kafkaTemplate;
    @Override
    public void asyncSend(String topic, String key, String data) {
        ListenableFuture<SendResult<String, String>> listenableFuture ;
        if (StringUtils.isEmpty(key)) {
            listenableFuture = kafkaTemplate.send(topic, data);
        } else {
            listenableFuture = kafkaTemplate.send(topic, key, data);
        }
        listenableFuture.addCallback((t) -> log.info("推送成功数据到topic={}.msg={},offset:{}", topic, data,t.getRecordMetadata().hasOffset()),
                throwable -> log.error("推送失败数据到topic={}.,msg={}", topic, data, throwable));
    }

    @Override
    public Pair<Boolean,String> syncSend(String topic, String key, String data) {
        CompletableFuture<SendResult<String, String>> future = null;
        if (StringUtils.isEmpty(key)) {
            future = kafkaTemplate.send(topic, data).completable();
        } else {
            future = kafkaTemplate.send(topic, key, data).completable();
        }
        return future.handle((r,e)->{
            if (Objects.isNull(e)){
                log.info("推送数据到topic={}成功.msg={}", topic, data);
                return Pair.of(true,"推送成功！");
            } else {
                log.error("推送数据到topic={}失败.,msg={}", topic, data, e);
                return  Pair.of(false,e.getMessage());
            }
        }).join();
    }
}
