package com.github.platform.core.sms.infra.service;

import com.github.platform.core.sms.domain.dto.SysSmsTemplateDto;
import com.github.platform.core.sms.domain.entity.SmsSpTemplateEntity;

/**
 * 短信路由服务
 * @author: yxkong
 * @date: 2022/11/24 10:13 上午
 * @version: 1.0
 */
public interface ISmsRouterService {
    String DEFAULT_ROUTER = "defaultSmsRouterService";

    /**
     * 根据短信模板，路由到有效的厂商
     * @param smsTemplate
     * @return
     */
    SmsSpTemplateEntity router(SysSmsTemplateDto smsTemplate);
}
