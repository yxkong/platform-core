package com.github.platform.core.cache.infra.service.impl;

import com.github.platform.core.common.service.IPublishService;
import com.github.platform.core.common.utils.ApplicationContextHolder;
import com.github.platform.core.common.utils.JsonUtils;
import com.github.platform.core.standard.constant.SendTypeEnum;
import com.github.platform.core.standard.entity.dto.CommonPublishDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * redis 发布
 * @author: yxkong
 * @date: 2023/9/8 2:26 PM
 * @version: 1.0
 */
@Slf4j
public class RedisPublishServiceImpl implements IPublishService {

    private StringRedisTemplate redisTemplate;

    public RedisPublishServiceImpl(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public boolean publish(CommonPublishDto dto) {
        String json = JsonUtils.toJson(dto);
        if (log.isDebugEnabled()){
            log.debug("通过redis:{} 发布:{}",SendTypeEnum.REDIS.getChannel(),json);
        }
        redisTemplate.convertAndSend(SendTypeEnum.REDIS.getChannel(), json);
        return true;
    }


}
