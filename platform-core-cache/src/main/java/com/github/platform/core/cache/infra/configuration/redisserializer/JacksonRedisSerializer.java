package com.github.platform.core.cache.infra.configuration.redisserializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.github.platform.core.standard.util.LocalDateTimeUtil;
import com.google.common.cache.RemovalListener;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 自定义jackson redis序列化
 * 解决 Jackson 无法反序列化的问题 java.lang.ClassCastException: java.util.LinkedHashMap cannot be cast to
 * @author: yxkong
 * @date: 2024/1/4 09:57
 * @version: 1.0
 */
public class JacksonRedisSerializer<T> implements RedisSerializer<T> {
    private final Type type;
    private  ObjectMapper objectMapper;

    public JacksonRedisSerializer(ObjectMapper objectMapper,Type type) {
        this.objectMapper = objectMapper;
        this.type = type;
    }

    @Override
    public byte[] serialize(T t) throws SerializationException {
        try {
            return objectMapper.writeValueAsString(t).getBytes(StandardCharsets.UTF_8);
        } catch (JsonProcessingException e) {
            throw new SerializationException("serialize fail", e);
        }
    }

    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        try {
            JavaType javaType = objectMapper.constructType(type);
            return objectMapper.readValue(new String(bytes), javaType);
        } catch (Exception e) {
            throw new SerializationException("deserialize by type fail", e);
        }
    }
}