package com.github.platform.core.common.configuration.feign;

import com.github.platform.core.common.constant.PropertyConstant;
import feign.Logger;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.openfeign.FeignLoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @author yxkong
 * @date 2021/6/30-14:47
 */
@Data
@Configuration
@ConfigurationProperties(prefix = PropertyConstant.FEIGN)
@Slf4j
public class FeignConfiguration {
    private String level;
    @Bean
    @Primary
    @ConditionalOnMissingBean
    public FeignLoggerFactory customizedFeignLoggerFactory() {
        log.warn("自定义日志工厂生效");
        return new CustomizedFeignLoggerFactory();
    }

    /**
     * 日志级别
     * 通过源码可以看到日志等级有 4 种，分别是：
     * NONE：不输出日志。
     * BASIC：只输出请求方法的 URL 和响应的状态码以及接口执行的时间。
     * HEADERS：将 BASIC 信息和请求头信息输出。
     * FULL：输出完整的请求信息。
     * 可以通过配置
     * feign.client.config.default.logger-level=full
     */
    @Bean
    Logger.Level feginLoggerLevel(){
        return Logger.Level.FULL;
    }
    /**
     * 创建Feign请求拦截器，在发送请求前设置认证的token,各个微服务将token设置到环境变量中来达到通用
     * 通过feign请求统一添加到header
     */
    @Bean
    public FeignBasicAuthRequestInterceptor basicAuthRequestInterceptor() {
        return new FeignBasicAuthRequestInterceptor();
    }

    public class FeignBasicAuthRequestInterceptor implements RequestInterceptor {

        public FeignBasicAuthRequestInterceptor() {
        }

        @Override
        public void apply(RequestTemplate template) {
        }
    }
}
