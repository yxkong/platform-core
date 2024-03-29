package com.github.platform.core.sms.application.executor;

import com.github.platform.core.sms.domain.context.SendSmsContext;
import com.github.platform.core.standard.entity.dto.ResultBean;

/**
 * 短信应用服务
 * @author: yxkong
 * @date: 2023/3/1 2:49 PM
 * @version: 1.0
 */
public interface ISmsExecutor {

    /**
     *
     * @param smsContext
     * @return
     */
    ResultBean sendSms(SendSmsContext smsContext);

}
