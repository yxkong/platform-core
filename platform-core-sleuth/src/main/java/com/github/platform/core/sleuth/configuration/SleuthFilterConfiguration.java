package com.github.platform.core.sleuth.configuration;

import com.github.platform.core.common.constant.PropertyConstant;
import com.github.platform.core.sleuth.configuration.filter.TracingFilter;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: yxkong
 * @date: 2021/7/27 3:15 下午
 * @version: 1.0
 */
@Data
@Configuration
@ConditionalOnProperty(name = PropertyConstant.CON_SLEUTH_TRACING_ENABLED, havingValue = "true",matchIfMissing = false)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@Slf4j
public class SleuthFilterConfiguration {
    @Bean
    public FilterRegistrationBean<TracingFilter> tracingFilterRegistrationBean() {
        if (log.isDebugEnabled()){
            log.debug("添加 TracingFilter");
        }
        FilterRegistrationBean<TracingFilter> filterRegistrationBean = new FilterRegistrationBean<TracingFilter>();
        filterRegistrationBean.setFilter(new TracingFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.setName("tracingFilter");
        return filterRegistrationBean;
    }
}