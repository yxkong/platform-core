package com.github.platform.core.sms.infra.service.impl;

import com.github.platform.core.common.utils.JsonUtils;
import com.github.platform.core.sms.domain.entity.ApplyEntity;
import com.github.platform.core.sms.domain.entity.SendSmsEntity;
import com.github.platform.core.sms.domain.entity.SendSmsResultEntity;
import com.github.platform.core.sms.domain.entity.SmsAccount;
import com.github.platform.core.sms.infra.convert.SendMessageConvert;
import com.github.platform.core.sms.infra.rpc.dto.ctyun.SendSmsCmd;
import com.github.platform.core.sms.infra.rpc.dto.ctyun.SendSmsDto;
import com.github.platform.core.sms.infra.rpc.external.CtyunFeignClient;
import com.github.platform.core.sms.infra.service.ISmsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 阿里云短信实现
 * @author: yxkong
 * @date: 2023/2/17 3:37 PM
 * @version: 1.0
 */
@Service("aliyunSmsService")
@Slf4j
public class AliyunSmsService implements ISmsService {
    @Autowired
    private CtyunFeignClient feignClient;
    @Autowired
    private SendMessageConvert convert;
    @Override
    public SendSmsResultEntity applySign(ApplyEntity apply) {
        return null;
    }

    @Override
    public SendSmsResultEntity applyTemplate(ApplyEntity apply) {
        return null;
    }

    @Override
    public SendSmsResultEntity sendSms(SendSmsEntity smsSend) {
        SendSmsCmd sendSmsCmd = convert.target(smsSend);
        sendSmsCmd.setAction("SendSms");
        sendSmsCmd.setTemplateParam(JsonUtils.toJson(smsSend.getParams()));
        if (log.isDebugEnabled()){
            log.debug("天翼云发送实体："+JsonUtils.toJson(sendSmsCmd));
        }
        SendSmsDto dto = feignClient.sendSms(sendSmsCmd);
        int status = "OK".equals(dto.getCode()) ? 1 : -1;
        String msg =dto.getCode()+ dto.getMessage();
        return SendSmsResultEntity.builder().msgId(dto.getRequestId()).status(status).errorMsg(msg).build();
    }

    @Override
    public int queryAmt(SmsAccount account) {
        return 0;
    }
}
