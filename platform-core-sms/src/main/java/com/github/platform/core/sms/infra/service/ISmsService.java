package com.github.platform.core.sms.infra.service;

import com.github.platform.core.sms.domain.entity.ApplyEntity;
import com.github.platform.core.sms.domain.entity.SendSmsEntity;
import com.github.platform.core.sms.domain.entity.SendSmsResultEntity;
import com.github.platform.core.sms.domain.entity.SmsAccount;

/**
 * 短信发送抽象服务
 * @author: yxkong
 * @date: 2022/6/22 10:42 PM
 * @version: 1.0
 */
public interface ISmsService {


    /**
     * 申请模签名
     * @param apply
     * @return
     */
	SendSmsResultEntity applySign(ApplyEntity apply);

    /**
     * 申请模板
     * @param apply
     * @return
     */
	SendSmsResultEntity applyTemplate(ApplyEntity apply);

    /**
     * 发送短信，返回自己封装的实体
     * @param smsSend
     * @return
     */
    SendSmsResultEntity sendSms(SendSmsEntity smsSend);

    /**
     * 查询账户余额
     * @param account
     * @return
     */
    int queryAmt(SmsAccount account);

}
