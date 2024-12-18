package com.github.platform.core.cache.infra.service.impl;

import com.github.platform.core.common.service.EventSender;
import com.github.platform.core.common.utils.JsonUtils;
import com.github.platform.core.standard.constant.SendTypeEnum;
import com.github.platform.core.standard.entity.event.DomainEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * redis发送
 * @Author: yxkong
 * @Date: 2024/12/16
 * @version: 1.0
 */
@Service("redisEventSender")
@Slf4j
public class RedisEventSender implements EventSender {
    @Resource(name = "stringRedisTemplate")
    private StringRedisTemplate redisTemplate;
    @Override
    public boolean send(DomainEvent event, String topic, String key) {
        String json = JsonUtils.toJson(event);
        if (log.isDebugEnabled()){
            log.debug("通过redis:{} 发布:{}",SendTypeEnum.REDIS.getChannel(),json);
        }
        redisTemplate.convertAndSend(SendTypeEnum.REDIS.getChannel(), json);
        return true;
    }
}
