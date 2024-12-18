package com.github.platform.core.message.infra.configuration.properties;

import com.github.platform.core.common.constant.PropertyConstant;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 通知属性
 * @Author: yxkong
 * @Date: 2024/12/17
 * @version: 1.0
 */
@ConfigurationProperties(prefix =  PropertyConstant.DATA_NOTICE)
@Configuration
@Data
public class NoticeProperties {
    private String channelType;
}
