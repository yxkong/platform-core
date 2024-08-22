package com.github.platform.core.cache.infra.configuration;

import com.github.platform.core.cache.infra.service.impl.RedisMessageListenerService;
import com.github.platform.core.cache.infra.service.impl.RedisSubscriberService;
import com.github.platform.core.common.constant.PropertyConstant;
import com.github.platform.core.standard.constant.SendTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import javax.annotation.Resource;

/**
 * redis订阅配置
 * @author: yxkong
 * @date: 2023/9/11 12:10 PM
 * @version: 1.0
 */
@Configuration
@AutoConfigureAfter({RedisAutoConfiguration.class})
@ConditionalOnProperty(name = PropertyConstant.CON_REDIS_SUBSCRIBE_ENABLED, havingValue = "true",matchIfMissing = false)
@Slf4j
public class RedisSubscribeConfiguration {
    @Resource
    private RedisSubscriberService redisSubscriber;

    @Bean
    public MessageListenerAdapter messageListener() {
        /**
         * 指定订阅的方法名
         */
        return new MessageListenerAdapter(redisSubscriber, "handleMessage");
    }
    /**
     * redis消息监听器容器
     * @param factory
     * @return
     */
    @Bean
    public RedisMessageListenerContainer redisContainer(RedisConnectionFactory factory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(factory);
        container.addMessageListener(messageListener(), new PatternTopic(SendTypeEnum.REDIS.getChannel()));
        return container;
    }
    @Bean
    public RedisMessageListenerService redisMessageListenerService(RedisMessageListenerContainer redisMessageListenerContainer){
        return new RedisMessageListenerService(redisMessageListenerContainer);
    }
}
