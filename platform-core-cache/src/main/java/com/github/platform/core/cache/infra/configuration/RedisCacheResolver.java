package com.github.platform.core.cache.infra.configuration;

import com.github.platform.core.common.utils.StringUtils;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheWriter;

/**
 * <TODO>
 *
 * @author: yxkong
 * @date: 2024/6/20 11:34
 * @version: 1.0
 */
public class RedisCacheResolver extends RedisCache {
    private final String name;
    private final RedisCacheWriter cacheWriter;
    private final ConversionService conversionService;

    protected RedisCacheResolver(String name, RedisCacheWriter cacheWriter, RedisCacheConfiguration cacheConfig) {
        super(name, cacheWriter, cacheConfig);
        this.name = name;
        this.cacheWriter = cacheWriter;
        this.conversionService = cacheConfig.getConversionService();
    }

    /**
     *
     * @Title: evict
     * @Description: 重写删除的方法
     * @param  @param key
     * @throws
     *
     */
    @Override
    public void evict(Object key) {
        if (key instanceof String) {
            String keyString = key.toString();
            // 后缀删除
            if (StringUtils.endsWith(keyString, "*")) {
                evictLikeSuffix(keyString);
                return;
            }
        }
        // 删除指定的key
        super.evict(key);
    }

    /**
     * 后缀匹配匹配
     *
     * @param key
     */
    private void evictLikeSuffix(String key) {
        byte[] pattern = this.conversionService.convert(this.createCacheKey(key), byte[].class);
        this.cacheWriter.clean(this.name, pattern);
    }
}
