package com.github.platform.core.cache.infra.configuration.properties;

import com.github.platform.core.common.constant.PropertyConstant;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * redis订阅服务属性
 * @Author: yxkong
 * @Date: 2024/8/21
 * @version: 1.0
 */
@Data
@Configuration
@ConfigurationProperties(prefix = PropertyConstant.DATA_REDIS_SUBSCRIBE)
public class RedisSubscribeProperties {
    /**开关*/
    private boolean enabled = false;
    /**订阅事件，匹配以xx开头的事件*/
    private List<String> events = new ArrayList<>();
}
