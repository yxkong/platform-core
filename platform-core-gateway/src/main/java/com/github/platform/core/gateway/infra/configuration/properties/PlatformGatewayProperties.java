package com.github.platform.core.gateway.infra.configuration.properties;

import com.github.platform.core.common.constant.PropertyConstant;
import com.github.platform.core.common.utils.StringUtils;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 网关属性
 * @Author: yxkong
 * @Date: 2024/9/5
 * @version: 1.0
 */
@Data
@Configuration
@ConfigurationProperties(prefix = PropertyConstant.DATA_GATEWAY)
public class PlatformGatewayProperties {
    /**w网关名称*/
    private String name;

    public String getName() {
        if (StringUtils.isEmpty(this.name)){
            return "platform";
        }
        return this.name;
    }
}
