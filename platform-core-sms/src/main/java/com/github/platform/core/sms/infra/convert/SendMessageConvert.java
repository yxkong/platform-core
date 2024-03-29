package com.github.platform.core.sms.infra.convert;

import com.github.platform.core.sms.domain.entity.SendSmsEntity;
import com.github.platform.core.sms.infra.rpc.dto.ctyun.SendSmsCmd;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mappings;

/**
 * @author hdy
 * @version 1.0
 * @date 2022-06-15 16:05:46
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SendMessageConvert {
    @Mappings({
            @Mapping(target = "phoneNumber", source = "sendSms.mobile"),
            @Mapping(target = "templateCode", source = "sendSms.smsSpTemplate.tempId")
//            @Mapping(target = "msg", expression = "java(com.arbitration.infrastructure.common.util.StringUtils.urlEncoder(smsSend.getContent()))"),
    })
    SendSmsCmd target (SendSmsEntity sendSms);

//    @Mappings({
//        @Mapping(target = "uid", source = "account.accountName"),
//        @Mapping(target = "pwd", source = "password"),
//
//    })
//    public BoShiTongVo smsAmtBstConvert (SmsAccount account);
//
//    @Mappings({
//        @Mapping(target = "uid", source = "apply.smsAccount.accountName"),
//        @Mapping(target = "pwd", source = "smsAccount.password"),
//        @Mapping(target = "signtext", expression = "java(com.arbitration.infrastructure.common.util.StringUtils.urlEncoder(apply.getSmsSign()))"),
//        @Mapping(target = "templateid", source = "apply.templateId"),
//    })
//    public BoShiTongTempVo smsTempBstConvert (ApplyEntity apply);
//
//    @Mappings({
//        @Mapping(target = "uid", source = "apply.smsAccount.accountName"),
//        @Mapping(target = "pwd", source = "smsAccount.password"),
//        @Mapping(target = "sign", expression = "java(com.arbitration.infrastructure.common.util.StringUtils.urlEncoder(apply.getSmsSign()))"),
//        @Mapping(target = "name", expression = "java(com.arbitration.infrastructure.common.util.StringUtils.urlEncoder(apply.getApplyName()))"),
//        @Mapping(target = "url", expression = "java(com.arbitration.infrastructure.common.util.StringUtils.urlEncoder(apply.getApplyUrl()))"),
//        @Mapping(target = "reason", expression = "java(com.arbitration.infrastructure.common.util.StringUtils.urlEncoder(apply.getApplyReason()))"),
//
//    })
//    public BoShiTongSignVo smsSignBstConvert (ApplyEntity apply);

}
