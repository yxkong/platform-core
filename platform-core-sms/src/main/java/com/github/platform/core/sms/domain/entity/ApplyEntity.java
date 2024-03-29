package com.github.platform.core.sms.domain.entity;

import com.github.platform.core.standard.annotation.DomainEntity;
import lombok.Data;

/**
 * 短信签名和模板申请业务实体
 * @author: yxkong
 * @date: 2022/6/23 11:11 AM
 * @version: 1.0
 */
@Data
@DomainEntity
public class ApplyEntity {

    /**签名*/
    private String smsSign;
    /**自定义模板id*/
    private String templateId;
    /**申请内容*/
    private String content;
    /**申请名称*/
    private String applyName;
    /**申请url*/
    private String applyUrl;
    /**申请原因*/
    private String applyReason;

    SmsAccount smsAccount;

}
