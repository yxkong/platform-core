package com.github.platform.core.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.platform.core.standard.exception.ParamsRuntimeException;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

/**
 * @author: yxkong
 * @date: 2021/5/19 9:40 上午
 * @version: 1.0
 */
@Slf4j
public class JsonUtils {
    public static ObjectMapper OBJECT_MAPPER;
    static {
        OBJECT_MAPPER = ApplicationContextHolder.getBean(ObjectMapper.class);
    }

    /**
     * 将json转换成字符串
     * @param object
     * @return
     */
    public static String toJson(Object object) {
        if (object == null) {
            return null;
        }
        try {
            return OBJECT_MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("对象转json失败", e);
            throw new ParamsRuntimeException(e.getMessage());
        }
    }

    /**
     * 将任意对象转成 目标类型（主要用于泛型）
     * @param object 任意对象
     * @param clazz 目标类型
     * @return
     * @param <T>
     */
    public static <T> T convertValue(Object object,Class<T> clazz){
        return OBJECT_MAPPER.convertValue(object,clazz);
    }

    /**
     * 将json转成对应的对象
     *
     * @param json
     * @param clazz  转化的类对象
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        if (StringUtils.isEmpty(json)){
            return null;
        }
        try {
            return OBJECT_MAPPER.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            log.error("json转对象失败", e);
            throw new ParamsRuntimeException(e.getMessage());
        }
    }

    /**
     * 按类型转化
     * @param json
     * @param type 例：  new TypeReference<Map<String, Object>>() {}
     * @author yxkong
     */
    public static <T> T fromJson(String json, TypeReference<T> type) {
        try {
            return OBJECT_MAPPER.readValue(json, type);
        } catch (JsonProcessingException e) {
            log.error("json转对象失败", e);
            throw new ParamsRuntimeException(e.getMessage());
        }
    }

    /**
     * json字符串转map
     * @param json json字符串
     * @return
     */
    public static Map<String,Object> toMap(String json){
        try {
            return OBJECT_MAPPER.readValue(json, new TypeReference<Map<String, Object>>() {});
        } catch (JsonProcessingException e) {
            log.error("json转对象失败", e);
            throw new ParamsRuntimeException(e.getMessage());
        }
    }
    /**
     * json字符串转List<Map<String,Object>>
     * @param json json字符串
     * @return
     */
    public static List<Map<String,Object>> toListMap(String json){
        try {
            return OBJECT_MAPPER.readValue(json, new TypeReference<List<Map<String, Object>>>() {});
        } catch (JsonProcessingException e) {
            log.error("json转对象失败", e);
            throw new ParamsRuntimeException(e.getMessage());
        }
    }



}
