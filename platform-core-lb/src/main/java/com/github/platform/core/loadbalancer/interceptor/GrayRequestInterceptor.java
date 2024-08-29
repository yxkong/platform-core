package com.github.platform.core.loadbalancer.interceptor;

import com.github.platform.core.loadbalancer.holder.RequestHeaderHolder;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

/**
 * 灰度拦截器，用于Feign请求时将label和version向下传递
 * @author: yxkong
 * @date: 2021/5/17 3:57 下午
 * @version: 1.0
 */
@Slf4j
public class GrayRequestInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate template) {
        template.header(RequestHeaderHolder.LABEL_KEY,RequestHeaderHolder.getLabel());
        template.header(RequestHeaderHolder.VERSION_KEY, RequestHeaderHolder.getVersion());
        template.header(RequestHeaderHolder.WEIGHT_KEY, RequestHeaderHolder.getWeight());
    }
}