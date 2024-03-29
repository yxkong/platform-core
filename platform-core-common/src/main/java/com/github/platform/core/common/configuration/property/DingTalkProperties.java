package com.github.platform.core.common.configuration.property;

import com.github.platform.core.common.annotation.ConditionalOnPropertyInList;
import com.github.platform.core.common.constant.PropertyConstant;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author yxkong
 * @description 钉钉告警
 */
@Data
@Configuration
@ConditionalOnPropertyInList(name = PropertyConstant.CON_NOTICE_TYPE,havingValue = "dingTalk")
@ConfigurationProperties(prefix = PropertyConstant.DATA_NOTICE_DING_TALK)
public class DingTalkProperties {
     private String baseUrl;
     private String token;
     private String secret;
}
