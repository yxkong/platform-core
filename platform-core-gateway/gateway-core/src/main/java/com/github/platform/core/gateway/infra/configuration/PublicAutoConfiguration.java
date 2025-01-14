package com.github.platform.core.gateway.infra.configuration;

import com.github.platform.core.common.configuration.property.PlatformHttpProperties;
import com.github.platform.core.loadbalancer.interceptor.GrayClientHttpRequestInterceptor;
import com.github.platform.core.loadbalancer.interceptor.GrayRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * 灰度注入
 * @author: yxkong
 * @date: 2023/2/28 1:31 PM
 * @version: 1.0
 */
@Configuration
public class PublicAutoConfiguration {
    @Resource
    private PlatformHttpProperties httpProperties;
    @Bean
    public ClientHttpRequestFactory simpleClientHttpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        // 单位为ms
        factory.setReadTimeout(httpProperties.getReadTimeout());
        // 单位为ms
        factory.setConnectTimeout(httpProperties.getConnectTimeout());
        return factory;
    }
    @Bean
    public RestTemplate restTemplate(ClientHttpRequestFactory factory) {
        RestTemplate restTemplate = new RestTemplate(factory);
        restTemplate.getInterceptors().add(new GrayClientHttpRequestInterceptor());
        return restTemplate;
    }
    @Bean
    public GrayRequestInterceptor grayRequestInterceptor(){
        return new GrayRequestInterceptor();
    }

}
