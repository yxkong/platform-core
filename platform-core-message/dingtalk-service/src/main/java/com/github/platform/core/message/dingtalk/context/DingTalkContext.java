package com.github.platform.core.message.dingtalk.context;

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
public class DingTalkContext {
    /**应用id*/
    private Long agentId;
    /** 钉钉企业的 appKey*/
    private String appKey ;
    /** 钉钉的 密钥*/
    private String appSecret;
    /**钉钉开放平台上，开发者设置的token*/
    private String token;
    /**钉钉开放台上，开发者设置的EncodingAESKey*/
    private String encodingAesKey;
    /** 企业自建应用-事件订阅, 使用appKey,
     *  企业自建应用-注册回调地址, 使用corpId
     *  第三方企业应用, 使用suiteKey
     */
    private String corpId;
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
