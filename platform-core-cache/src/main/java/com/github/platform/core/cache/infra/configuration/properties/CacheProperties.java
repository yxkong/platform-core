package com.github.platform.core.cache.infra.configuration.properties;

import com.github.platform.core.cache.domain.constant.CacheConstant;
import com.github.platform.core.common.constant.PropertyConstant;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 缓存key处理（除了登录相关的）
 * @author: yxkong
 * @date: 2023/4/6 3:36 PM
 * @version: 1.0
 */
@Data
@Configuration
@ConfigurationProperties(prefix = PropertyConstant.DATA_CACHE_PREFIX)
public class CacheProperties {
    /**config配置缓存前缀 */
    private String config = CacheConstant.config;
    /**字典配置缓存前缀 */
    private String dict = CacheConstant.dict;
    /**防重校验缓存前缀*/
    private String repeatSubmit = CacheConstant.repeatSubmit;
    /**序列号生成序号缓存前缀*/
    private String sequence = CacheConstant.sequence;
    /**灰度缓存key*/
    private String grayRule = CacheConstant.grayRule;
}
