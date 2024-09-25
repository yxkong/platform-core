package com.github.platform.core.cache.infra.configuration;

import com.github.platform.core.common.constant.SpringBeanOrderConstant;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * 主redis 服务，使用系统自带的redis
 * @author: yxkong
 * @date: 2021/6/9 10:40 上午
 * @version: 1.0
 */
@Configuration
@ConditionalOnClass(RedisOperations.class)
@EnableConfigurationProperties({RedisProperties.class})
@AutoConfigureBefore({CacheAutoConfiguration.class})
@Order(SpringBeanOrderConstant.REDIS_STARTER)
public class RedisAutoConfiguration {
    private final RedisConnectionFactory redisConnectionFactory;

    public RedisAutoConfiguration(RedisConnectionFactory redisConnectionFactory) {
        this.redisConnectionFactory = redisConnectionFactory;
    }

    @Bean
    @ConditionalOnMissingBean(name = "redisTemplate")
    public RedisTemplate<String, String> redisTemplate() {
        RedisTemplate<String, String> template = new RedisTemplate<>();
        configureSerializer(template, RedisSerializer.string());
        return template;
    }

    @Bean
    @ConditionalOnMissingBean(name = "objectRedisTemplate")
    public RedisTemplate<String, Object> objectRedisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        configureSerializer(template, CustomRedisCacheManagerConfiguration.buildRedisSerializer());
        return template;
    }

    @Bean
    @ConditionalOnMissingBean(name = "genericRedisTemplate")
    public RedisTemplate<String, Object> genericRedisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        configureSerializer(template, CustomRedisCacheManagerConfiguration.buildRedisSerializer());
        return template;
    }

    @Bean
    @ConditionalOnMissingBean(name = "stringRedisTemplate")
    public StringRedisTemplate stringRedisTemplate() {
        return new StringRedisTemplate(redisConnectionFactory);
    }

    private void configureSerializer(RedisTemplate template, RedisSerializer serializer) {
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(RedisSerializer.string());
        template.setValueSerializer(serializer);
        template.setHashKeySerializer(RedisSerializer.string());
        template.setHashValueSerializer(serializer);
        template.afterPropertiesSet();
    }

}
