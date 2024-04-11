package com.github.platform.core.sms.infra.rpc.external;

import com.github.platform.core.sms.infra.rpc.dto.ctyun.SendSmsCmd;
import com.github.platform.core.sms.infra.rpc.dto.ctyun.SendSmsDto;
import com.github.platform.core.sms.infra.rpc.feign.configuration.CtyunConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


/**
 * 天翼云feign客户端
 * @author: yxkong
 * @date: 2023/2/17 8:25 PM
 * @version: 1.0
 */
@FeignClient(url = "${platform.feign.url.sms.ctyun}",name="ctyunFeign",configuration = CtyunConfiguration.class)
public interface CtyunFeignClient {
    /**
     * 天翼云发送短信
     * @param sendSms
     * @return
     */
    @PostMapping( value = "/sms/api/v1",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    SendSmsDto sendSms(@RequestBody SendSmsCmd sendSms);

}
