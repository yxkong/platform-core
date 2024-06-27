package com.github.platform.core.web.configuration;

import com.github.platform.core.common.configuration.property.PlatformProperties;
import com.github.platform.core.common.constant.PropertyConstant;
import com.github.platform.core.web.filter.HttpTraceLogFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * http trace日志
 * @author: yxkong
 * @date: 2023/3/13 2:20 PM
 * @version: 1.0
 */
@Configuration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@Slf4j
public class HttpFilterConfiguration {
    @Resource
    private PlatformProperties platformProperties;
    /**
     * 注册HttpTraceLogFilter
     * @return
     */
    @Bean
    @ConditionalOnProperty(name = PropertyConstant.CON_HTTP_TRACE_ENABLED, havingValue = "true",matchIfMissing = false)
    public FilterRegistrationBean<HttpTraceLogFilter> httpTraceLogFilterFilterRegistrationBean() {
        if (log.isDebugEnabled()){
            log.debug("添加 HttpTraceLogFilter");
        }
        FilterRegistrationBean<HttpTraceLogFilter> filterRegistrationBean = new FilterRegistrationBean<HttpTraceLogFilter>();
        filterRegistrationBean.setFilter(new HttpTraceLogFilter(platformProperties));
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.setName("httpTraceLogFilter");
        return filterRegistrationBean;
    }
//    @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
//    static class ServletTraceFilterConfiguration {
//        @Bean
//        @ConditionalOnProperty(name = PropertyConstant.CON_HTTP_TRACE_ENABLED, havingValue = "true",matchIfMissing = false)
//        public HttpTraceLogFilter httpTraceLogFilter() {
//            return new HttpTraceLogFilter();
//        }
//        //其他的fitler都可以放合理
//    }




}
