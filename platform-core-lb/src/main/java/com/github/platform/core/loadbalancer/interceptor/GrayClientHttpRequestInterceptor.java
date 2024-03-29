package com.github.platform.core.loadbalancer.interceptor;

import com.github.platform.core.loadbalancer.holder.RequestHeaderHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.support.HttpRequestWrapper;

import java.io.IOException;

/**
 * 灰度拦截器，用于RestTemplate请求时将label和version向下传递
 * @author: yxkong
 * @date: 2023/2/23 7:17 PM
 * @version: 1.0
 */
@Slf4j
public class GrayClientHttpRequestInterceptor implements ClientHttpRequestInterceptor {
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        HttpRequestWrapper requestWrapper = new HttpRequestWrapper(request);
        //这里可以把接收到的请求头传递过去
        if (log.isDebugEnabled()){
            log.debug("add RestTemplate header");
        }
        requestWrapper.getHeaders().add(RequestHeaderHolder.LABEL_KEY,RequestHeaderHolder.getLabel());
        requestWrapper.getHeaders().add(RequestHeaderHolder.VERSION_KEY, RequestHeaderHolder.getVersion());
        requestWrapper.getHeaders().add(RequestHeaderHolder.WEIGHT_KEY, RequestHeaderHolder.getWeight());
        return execution.execute(requestWrapper, body);
    }
}
