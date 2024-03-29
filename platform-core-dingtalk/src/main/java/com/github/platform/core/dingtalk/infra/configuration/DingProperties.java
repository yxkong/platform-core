package com.github.platform.core.dingtalk.infra.configuration;

import com.github.platform.core.common.constant.PropertyConstant;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 钉钉配置属性
 * @author: yxkong
 * @date: 2024/1/18 17:54
 * @version: 1.0
 */
@Data
@Configuration
@ConfigurationProperties(prefix = PropertyConstant.DATA_DING_TALK)
public class DingProperties {
    /** 钉钉的 appKey*/
    private String appKey ;
    /** 钉钉的 密钥*/
    private String appSecret;
    /**
     * 默认管理员
     */
    private String defaultOwner;
    /**群模板id*/
    private String groupTemplateId;
    /**机器人编码*/
    private String robotCode;
    private String tokenKey = "p:dt:accessToken";
}
