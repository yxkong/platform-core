package com.github.platform.core.sms.infra.convert;

import com.github.platform.core.sms.domain.common.entity.SysSmsTemplateBase;
import com.github.platform.core.sms.domain.common.entity.SysSmsTemplateStatusBase;
import com.github.platform.core.sms.domain.context.SendSmsContext;
import com.github.platform.core.sms.domain.context.SysSmsLogContext;
import com.github.platform.core.sms.domain.dto.SysSmsTemplateDto;
import com.github.platform.core.sms.domain.entity.SendSmsEntity;
import com.github.platform.core.sms.domain.entity.SmsSpTemplateEntity;
import com.github.platform.core.sms.domain.entity.SmsTemplateEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mappings;

/**
 * 短信转换
 * @author: yxkong
 * @date: 2022/6/23 10:40 AM
 * @version: 1.0
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SmsInfraConvert {

    SmsTemplateEntity target(SysSmsTemplateBase templateDO);


    SmsSpTemplateEntity target(SysSmsTemplateStatusBase templateStatus);
    @Mappings({
            @Mapping(target = "tempNo", source= "context.tempNo"),
    })
    SendSmsEntity of(SendSmsContext context, SmsSpTemplateEntity smsSpTemplate, String content);

    SysSmsLogContext of(SendSmsContext context, String proNo, String content, Integer status, Integer sendStatus);
}
