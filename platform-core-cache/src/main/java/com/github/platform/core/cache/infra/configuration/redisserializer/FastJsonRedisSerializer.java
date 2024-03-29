package com.github.platform.core.cache.infra.configuration.redisserializer;

import com.alibaba.fastjson2.JSON;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.lang.reflect.Type;

/**
 * 自定义FastJson redis序列化，使用FastJson的话，需要修改
 * 解决 java.lang.ClassCastException: com.alibaba.fastjson.JSONObject cannot be cast to
 * @author: yxkong
 * @date: 2024/1/4 10:09
 * @version: 1.0
 */
public class FastJsonRedisSerializer<T> implements RedisSerializer<T> {
    private final Type type;

    public FastJsonRedisSerializer(Type type) {
        this.type = type;
    }

    @Override
    public byte[] serialize(T t) throws SerializationException {
        return JSON.toJSONBytes(t);
    }

    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        return JSON.parseObject(bytes,type);
    }
}