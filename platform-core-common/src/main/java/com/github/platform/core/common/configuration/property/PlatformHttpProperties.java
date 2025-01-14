package com.github.platform.core.common.configuration.property;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

/**
 * http配置
 * @Author: yxkong
 * @Date: 2025/1/14
 * @version: 1.0
 */
@ConfigurationProperties(prefix =  "platform.http")
@Configuration
@Setter
public class PlatformHttpProperties {
    private Integer connectTimeout;
    private Integer readTimeout;

    public Integer getConnectTimeout() {
        if (Objects.isNull(connectTimeout)){
            return 1000;
        }
        return connectTimeout;
    }

    public Integer getReadTimeout() {
        if (Objects.isNull(readTimeout)){
            return 3000;
        }
        return readTimeout;
    }
}
