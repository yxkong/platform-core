package com.github.platform.core.cache.infra.configuration.properties;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * token redis配置
 * @Author: yxkong
 * @Date: 2024/9/11
 * @version: 1.0
 */
@Configuration("tokenRedisProperties")
@ConfigurationProperties(prefix = "platform.redis.api-token")
@ConditionalOnProperty(prefix = "platform.redis.api-token", name = "enabled",havingValue = "true",matchIfMissing = false)
@Data
public class TokenRedisProperties {
    private String enabled;
    private int database = 0;
    private String url;
    private String host = "localhost";
    private String username;
    private String password;
    private int port = 6379;
    private boolean ssl;
    private Duration timeout;
    private Duration connectTimeout;
    private String clientName;
    private RedisProperties.ClientType clientType;
    private RedisProperties.Sentinel sentinel;
    private RedisProperties.Cluster cluster;
    private final RedisProperties.Jedis jedis = new RedisProperties.Jedis();
    private final RedisProperties.Lettuce lettuce = new RedisProperties.Lettuce();

}
