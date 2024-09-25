package com.github.platform.core.cache.infra.configuration;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.github.platform.core.cache.domain.constant.CacheConstant;
import com.github.platform.core.cache.domain.constant.CacheNameEnum;
import com.github.platform.core.cache.infra.configuration.properties.RedisCacheExpiresProperties;
import com.github.platform.core.standard.constant.SymbolConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author: yxkong
 * @date: 2021/6/10 11:09 上午
 * @version: 1.0
 */
@EnableCaching
@Configuration
@AutoConfigureAfter({CacheAutoConfiguration.class})
@ConditionalOnClass(RedisOperations.class)
@EnableConfigurationProperties({RedisProperties.class, CacheProperties.class, RedisCacheExpiresProperties.class})
@Slf4j
public class CustomRedisCacheManagerConfiguration {
    private final CacheProperties cacheProperties;
    private final RedisCacheExpiresProperties redisCacheExpiresProperties;

    public CustomRedisCacheManagerConfiguration(CacheProperties cacheProperties,RedisCacheExpiresProperties redisCacheExpiresProperties) {
        this.cacheProperties = cacheProperties;
        this.redisCacheExpiresProperties = redisCacheExpiresProperties;
    }

    /**
     * 如果引入了redisson
     *    @Qualifier("redissonConnectionFactory") RedisConnectionFactory redisConnectionFactory,
     * @param redisConnectionFactory
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(name = CacheConstant.cacheManager)
    public RedisCacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory) {
        //默认1小时
        long defaultExpireTime = CacheNameEnum.C1H.getExpireTime();
        //注意ttl为毫秒级
//        if (Objects.nonNull(cacheProperties.getRedis().getTimeToLive())){
//            defaultExpireTime = cacheProperties.getRedis().getTimeToLive().getSeconds();
//        }

        Map<String, RedisCacheConfiguration> redisCacheConfigurations = getRedisCacheConfigurationMap();
        return RedisCacheManager.builder(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory))
                .cacheDefaults(determineConfiguration(defaultExpireTime))
                .withInitialCacheConfigurations(redisCacheConfigurations)
                .build();
    }
    private Map<String, RedisCacheConfiguration> getRedisCacheConfigurationMap() {
        return Optional.ofNullable(redisCacheExpiresProperties.getCacheExpires())
                .orElseGet(Collections::emptyMap)
                .entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> determineConfiguration(entry.getValue())
                ));
    }
    /**
     * 根据配置的的缓存名和失效时间，配置缓存的 TTL（失效时间）
     * @param seconds
     * @return
     */
    private RedisCacheConfiguration determineConfiguration(long seconds) {
        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(seconds))
                .computePrefixWith(name ->name + SymbolConstant.colon)
                .disableCachingNullValues()
                .serializeValuesWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(buildRedisSerializer()));
    }

    public static RedisSerializer<Object> buildRedisSerializer() {
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addDeserializer(LocalDate.class,
                new LocalDateDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        javaTimeModule.addDeserializer(LocalDateTime.class,
                new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        javaTimeModule.addSerializer(LocalDate.class,
                new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        javaTimeModule.addSerializer(LocalDateTime.class,
                new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        ObjectMapper objectMapper = new ObjectMapper()
                .registerModule(javaTimeModule)
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY)
                .activateDefaultTyping(
                        BasicPolymorphicTypeValidator.builder().allowIfBaseType(Object.class).build(),
                        ObjectMapper.DefaultTyping.NON_FINAL,
                        JsonTypeInfo.As.PROPERTY
                );
        return new GenericJackson2JsonRedisSerializer(objectMapper);
    }
}