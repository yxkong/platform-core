package com.github.platform.core.common.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * 工具类
 * @author: yxkong
 * @date: 2023/1/4 10:02 AM
 * @version: 1.0
 */
@Component
@Slf4j
public class CommonUtil {

    public static ObjectMapper OBJECT_MAPPER;
    public static ApplicationContext APPLICATION_CONTEXT;
    @Autowired
    public void setJacksonObjectMapper(ObjectMapper objectMapper) {
        CommonUtil.OBJECT_MAPPER = objectMapper;
    }
    @Autowired
    public void setApplicationContext(ApplicationContext applicationContext){
        CommonUtil.APPLICATION_CONTEXT = applicationContext;
    }


}
