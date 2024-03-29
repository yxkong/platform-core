package com.github.platform.core.cache.infra.configuration;

import com.github.platform.core.cache.infra.service.impl.RedisMessageListenerService;
import com.github.platform.core.cache.infra.service.impl.RedisPublishServiceImpl;
import com.github.platform.core.cache.infra.service.impl.RedisSubscriberService;
import com.github.platform.core.common.constant.PropertyConstant;
import com.github.platform.core.common.constant.SpringBeanNameConstant;
import com.github.platform.core.common.constant.SpringBeanOrderConstant;
import com.github.platform.core.common.service.IPublishService;
import com.github.platform.core.standard.constant.SendTypeEnum;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import javax.annotation.Resource;

/**
 * redis订阅与发布配置
 * @author: yxkong
 * @date: 2023/9/11 12:10 PM
 * @version: 1.0
 */
@Configuration
@AutoConfigureAfter({RedisAutoConfiguration.class})
@ConditionalOnProperty(name = PropertyConstant.CON_REDIS_PUBLISH_ENABLED, havingValue = "true",matchIfMissing = false)
public class RedisPublishConfiguration {
    @Resource
    private RedisSubscriberService redisSubscriber;
    @Resource(name = "stringRedisTemplate")
    private StringRedisTemplate redisTemplate;
    /**
     * 发布服务，redis实现
     * @return
     */
    @Bean(SpringBeanNameConstant.PUBLISH_SERVICE)
    @Order(SpringBeanOrderConstant.PUBLISH_SERVICE_REDIS)
    @ConditionalOnMissingBean(IPublishService.class)
    public IPublishService publishService(){
        return new RedisPublishServiceImpl(redisTemplate);
    }
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
