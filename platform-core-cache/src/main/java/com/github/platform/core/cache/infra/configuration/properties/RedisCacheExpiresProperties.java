package com.github.platform.core.cache.infra.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;
import java.util.Objects;

/**
 * @author: yxkong
 * @date: 2021/6/10 11:08 上午
 * @version: 1.0
 */
@ConfigurationProperties(prefix = "spring.cache.redis")
public class RedisCacheExpiresProperties {
    private Map<String, Long> cacheExpires;

    public Map<String, Long> getCacheExpires() {
        return cacheExpires;
    }

    public void setCacheExpires(Map<String, Long> cacheExpires) {
        String tokenKey  = "pst";
        if (Objects.nonNull(cacheExpires) && cacheExpires.containsKey(tokenKey)){
            cacheExpires.put("p:s:t",cacheExpires.get(tokenKey));
        }
        this.cacheExpires = cacheExpires;
    }
}