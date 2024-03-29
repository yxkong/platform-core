package com.github.platform.core.common.configuration;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.github.platform.core.common.constant.SpringBeanOrderConstant;
import com.github.platform.core.common.utils.StringUtils;
import com.github.platform.core.standard.util.LocalDateTimeUtil;
import lombok.Data;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @author yxkong
 * @date 2023/3/13 17:58
 * @describe jackson 配置
 */
@Configuration
@ConfigurationProperties(prefix = "spring.jackson")
@Data
public class JacksonConfiguration {
    private String dateFormat;

    /**
     * 指定 LocalDateTime 序列化格式
     * https://blog.csdn.net/qq_45740349/article/details/120805668
     * @return
     */
    @Bean
    @Order(-100)
    public Jackson2ObjectMapperBuilderCustomizer customizeLocalDateTimeFormat() {
        if (StringUtils.isEmpty(dateFormat)) {
            dateFormat = "yyyy-MM-dd HH:mm:ss";
        }
        return jacksonObjectMapperBuilder -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
            jacksonObjectMapperBuilder.serializerByType(LocalDateTime.class, new LocalDateTimeSerializer(formatter));
            jacksonObjectMapperBuilder.deserializerByType(LocalDateTime.class, new LocalDateTimeDeserializer(formatter));
        };
    }
    @Bean
    @Order(-99)
    public ObjectMapper objectMapper() {
        ObjectMapper  objectMapper = new ObjectMapper();
        // 添加JavaTimeModule，支持Java 8日期时间类型
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        // 设置LocalDateTime的序列化格式
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(LocalDateTimeUtil.DEFAULT_SHORT_DATETIME_FORMATTER));
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(LocalDateTimeUtil.DEFAULT_SHORT_DATE_FORMATTER));
        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(LocalDateTimeUtil.DEFAULT_TIME_FORMATTER));
        // 设置LocalDateTime的反序列化格式
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(LocalDateTimeUtil.DEFAULT_SHORT_DATETIME_FORMATTER));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(LocalDateTimeUtil.DEFAULT_SHORT_DATE_FORMATTER));
        javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(LocalDateTimeUtil.DEFAULT_TIME_FORMATTER));
        objectMapper.registerModule(javaTimeModule);
        //反序列化的时候如果多了其他属性,不抛出异常
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //如果是空对象的时候,不抛异常
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        return objectMapper;
    }
}
