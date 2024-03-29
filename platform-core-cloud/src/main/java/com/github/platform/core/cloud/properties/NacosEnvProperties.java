package com.github.platform.core.cloud.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * nacos环境配置
 * @author: yxkong
 * @date: 2023/3/2 5:21 PM
 * @version: 1.0
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "env.nacos")
public class NacosEnvProperties {
    private Boolean enabled;
    private Map<String,String> map = new HashMap<>();
}
