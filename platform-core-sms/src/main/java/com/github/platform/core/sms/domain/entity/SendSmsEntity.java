package com.github.platform.core.sms.domain.entity;

import com.github.platform.core.standard.annotation.DomainEntity;
import lombok.Data;

import java.util.Map;

/**
 * 短信发送实体
 * @author: yxkong
 * @date: 2022/6/23 9:38 AM
 * @version: 1.0
 */
@Data
@DomainEntity
public class SendSmsEntity {
    /**
     * 短信模板实体
     */
    private String tempNo;
    /**
     * 短信服务商
     */
    private SmsSpTemplateEntity smsSpTemplate;
    /**
     * 发送手机号
     */
    private String mobile;
    /**
     * 模板对应的参数
     */
    private Map<String,Object> params;
    /**
     * 短信签名
     */
    private String signName;
    /**
     * 模板内容
     */
    private String template;
    /**
     * 短信内容
     */
    private String content;

}
