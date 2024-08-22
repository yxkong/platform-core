package com.github.platform.core.cache.infra.configuration;

import com.github.platform.core.cache.infra.service.impl.RedisPublishServiceImpl;
import com.github.platform.core.common.constant.PropertyConstant;
import com.github.platform.core.common.constant.SpringBeanNameConstant;
import com.github.platform.core.common.constant.SpringBeanOrderConstant;
import com.github.platform.core.common.service.IPublishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;

/**
 * redis发布配置
 * @author: yxkong
 * @date: 2023/9/11 12:10 PM
 * @version: 1.0
 */
@Configuration
@AutoConfigureAfter({RedisAutoConfiguration.class})
@ConditionalOnProperty(name = PropertyConstant.CON_REDIS_PUBLISH_ENABLED, havingValue = "true",matchIfMissing = false)
@Slf4j
public class RedisPublishConfiguration {
    @Resource(name = "stringRedisTemplate")
    private StringRedisTemplate redisTemplate;
    /**
     * 发布服务，redis实现
     * @return
     */
    @Bean(SpringBeanNameConstant.REDIS_PUBLISH_SERVICE)
    @Order(SpringBeanOrderConstant.PUBLISH_SERVICE_REDIS)
    public IPublishService publishService(){
        return new RedisPublishServiceImpl(redisTemplate);
    }
}
