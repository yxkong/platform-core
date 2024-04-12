package com.github.platform.core.sms.infra.rpc.external;

import com.github.platform.core.sms.infra.rpc.dto.ctyun.SendSmsCmd;
import com.github.platform.core.sms.infra.rpc.dto.ctyun.SendSmsDto;
import com.github.platform.core.sms.infra.rpc.feign.configuration.CtyunConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


/**
 * 阿里云feign客户端
 * @author: yxkong
 * @date: 2023/2/17 7:25 PM
 * @version: 1.0
 */
@FeignClient(url = "${platform.feign.url.sms.aliyun}",name="aliyunSmsFeign",configuration = CtyunConfiguration.class)
public interface AliyunSmsFeignClient {
    /**
     * 阿里云发送短信
     * @param sendSms
     * @return
     */
    @PostMapping( value = "/sms/api/v1",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    SendSmsDto sendSms(@RequestBody SendSmsCmd sendSms);

}
