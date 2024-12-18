package com.github.platform.core.common.configuration.property;

import com.github.platform.core.common.constant.PropertyConstant;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 领域事件属性
 * @Author: yxkong
 * @Date: 2024/12/16
 * @version: 1.0
 */
@Configuration
@ConfigurationProperties(prefix =  PropertyConstant.DATA_DOMAIN_TYPE)
@Data
public class DomainEventProperties {
    /**发送类型*/
    private String type;
    /**默认发送器*/
    private String defaultSender;
}
