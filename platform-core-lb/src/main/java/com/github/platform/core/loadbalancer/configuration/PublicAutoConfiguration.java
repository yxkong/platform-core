package com.github.platform.core.loadbalancer.configuration;

import com.github.platform.core.loadbalancer.interceptor.GrayClientHttpRequestInterceptor;
import com.github.platform.core.loadbalancer.interceptor.GrayRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * 灰度注入
 * @author: yxkong
 * @date: 2023/2/28 1:31 PM
 * @version: 1.0
 */
@Configuration
public class PublicAutoConfiguration {
    private static final Integer DEFAULT_READ_TIMEOUT = 3000;
    private static final Integer DEFAULT_CONNECT_TIMEOUT = 1000;
    @Bean
    public ClientHttpRequestFactory simpleClientHttpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        // 单位为ms
        factory.setReadTimeout(DEFAULT_READ_TIMEOUT);
        // 单位为ms
        factory.setConnectTimeout(DEFAULT_CONNECT_TIMEOUT);
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
