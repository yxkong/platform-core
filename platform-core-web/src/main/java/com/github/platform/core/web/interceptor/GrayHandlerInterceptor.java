package com.github.platform.core.web.interceptor;

import com.github.platform.core.loadbalancer.holder.GrayLabel;
import com.github.platform.core.loadbalancer.holder.RequestHeaderHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 灰度拦截器
 * @author: yxkong
 * @date: 2023/2/27 1:58 PM
 * @version: 1.0
 */
@Slf4j
public class GrayHandlerInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        RequestHeaderHolder.remove();
        GrayLabel label = GrayLabel.builder()
                .label(request.getHeader(RequestHeaderHolder.LABEL_KEY))
                .version(request.getHeader(RequestHeaderHolder.VERSION_KEY))
                .weight(request.getHeader(RequestHeaderHolder.WEIGHT_KEY))
                .build();
        RequestHeaderHolder.set(label);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        RequestHeaderHolder.remove();
    }
}
