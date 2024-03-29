package com.github.platform.core.sms.infra.service;

import com.github.platform.core.sms.domain.dto.SysSmsTemplateDto;
import com.github.platform.core.sms.domain.dto.SysSmsTemplateStatusDto;

import java.util.List;

/**
 * 短信厂商路由
 *
 * @author: yxkong
 * @date: 2024/3/27 14:12
 * @version: 1.0
 */
public interface ISmsSpRoutingStrategy {
    /**
     * 路由
     * @param smsRoute
     * @param list
     * @return
     */
    SysSmsTemplateStatusDto route(SysSmsTemplateDto smsRoute,List<SysSmsTemplateStatusDto> list);
}
