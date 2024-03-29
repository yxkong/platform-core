package com.github.platform.core.cache.infra.utils;

import com.github.platform.core.cache.infra.configuration.properties.CacheProperties;
import com.github.platform.core.common.utils.ApplicationContextHolder;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 缓存key根据类
 * @author: yxkong
 * @date: 2023/4/19 10:49 AM
 * @version: 1.0
 */

@Component
public class CacheKeyUtil {

    public static CacheProperties properties;

    private static CacheProperties get(){
        if (Objects.nonNull(properties)){
            return properties;
        } else {
            properties = ApplicationContextHolder.getBean(CacheProperties.class);
        }
        if (Objects.nonNull(properties)){
            return properties;
        }
        return new CacheProperties();
    }

    public static String configKey(){
        return get().getConfig();
    }
    public static String dictPre(){
        return get().getDict();
    }
    public static String repeatSubmitPre(){
        return get().getRepeatSubmit();
    }
    public static String sequencePre(){
        return get().getSequence();
    }
    public static String grayRulePre(){
        return get().getGrayRule();
    }

}
