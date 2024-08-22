package com.github.platform.core.cache.infra.configuration;

import com.github.platform.core.cache.infra.configuration.properties.RedissonProperties;
import com.github.platform.core.common.constant.PropertyConstant;
import com.github.platform.core.common.utils.StringUtils;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * redisson 配置
 * @author: yxkong
 * @date: 2024/6/24 11:26
 * @version: 1.0
 */
@Configuration
@ConditionalOnClass(Redisson.class)
@ConditionalOnProperty(name = PropertyConstant.CON_REDISSON_ENABLED, havingValue = "true")
@EnableConfigurationProperties({RedissonProperties.class})
@Slf4j
@Setter
public class RedissonConfiguration  {

    @Resource
    private RedissonProperties redissonProperties;

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        SingleServerConfig serverConfig = config.useSingleServer();
        serverConfig.setAddress(redissonProperties.getAddress());
        if (StringUtils.isNotEmpty(redissonProperties.getPassword())){
            serverConfig.setPassword(redissonProperties.getPassword());
        }
        if (Objects.nonNull(redissonProperties.getDatabase())){
            serverConfig.setDatabase(redissonProperties.getDatabase());
        }
        if (Objects.nonNull(redissonProperties.getMaxActive())){
            serverConfig.setConnectionPoolSize(redissonProperties.getMaxActive());
        }
        if (Objects.nonNull(redissonProperties.getMinIdleSize())){
            serverConfig.setConnectionMinimumIdleSize(redissonProperties.getMinIdleSize());
        }
        if (Objects.nonNull(redissonProperties.getSubMaxActive())){
            serverConfig.setSubscriptionConnectionPoolSize(redissonProperties.getSubMaxActive());
        }
        if (Objects.nonNull(redissonProperties.getSubMinIdleSize())){
            serverConfig.setSubscriptionConnectionMinimumIdleSize(redissonProperties.getSubMinIdleSize());
        }
        return Redisson.create(config);
    }
}
