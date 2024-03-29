package com.github.platform.core.gateway.infra.configuration.properties;

import com.github.platform.core.common.constant.PropertyConstant;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 网关nacos配置
 *
 * @author: yxkong
 * @date: 2021/12/9 7:55 PM
 * @version: 1.0
 */
@ConfigurationProperties(PropertyConstant.DATA_GATEWAY_ROUTE_NACOS)
@Configuration
@Data
public class NacosRouteProperties {

    private String dataId;
    private String group;
    private long timeout=2000;
}
