package com.github.platform.core.cache.infra.configuration.properties;

import com.github.platform.core.common.constant.PropertyConstant;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * redisson配置
 * @author: yxkong
 * @date: 2024/6/22 13:35
 * @version: 1.0
 */
@Data
@Configuration
@ConfigurationProperties(prefix = PropertyConstant.DATA_REDISSON)
public class RedissonProperties {
    /**redis 地址*/
    private String host;
    /**redis 端口*/
    private Integer port;
    /**密码*/
    private String password;
    /**订阅最小空闲数*/
    private Integer subMinIdleSize;
    /**订阅最大连接数*/
    private Integer subMaxActive;
    /**连接池最小空闲数*/
    private Integer minIdleSize;
    /**连接池最大连接数*/
    private Integer maxActive ;
    /**db*/
    private int database = 0;
    /**监控周期*/
    private long monitoringInterval = 5000L;

    public String getAddress(){
        return "redis://" + this.host + ":" + this.port;
    }
}
