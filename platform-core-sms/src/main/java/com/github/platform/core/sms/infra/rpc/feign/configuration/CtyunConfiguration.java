package com.github.platform.core.sms.infra.rpc.feign.configuration;

import com.github.platform.core.persistence.mapper.sms.SysSmsServiceProviderMapper;
import com.github.platform.core.sms.infra.rpc.feign.interceptor.CtyunInterceptor;
import org.springframework.context.annotation.Bean;

/**
 * 天翼云feign拦截器
 * @author: yxkong
 * @date: 2023/3/1 2:30 PM
 * @version: 1.0
 */
public class CtyunConfiguration {
    @Bean
    public CtyunInterceptor ctyunInterceptor(SysSmsServiceProviderMapper serviceProviderMapper){
        return new CtyunInterceptor(serviceProviderMapper);
    }
}
