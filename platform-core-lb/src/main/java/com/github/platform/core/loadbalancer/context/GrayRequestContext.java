package com.github.platform.core.loadbalancer.context;

import org.springframework.cloud.client.loadbalancer.Request;

/**
 * 为了能够传递信息到LoadBalancer
 * @author: yxkong
 * @date: 2023/2/23 5:08 PM
 * @version: 1.0
 */
@Deprecated
public class GrayRequestContext<T> implements Request {
    private T exchange;

    public GrayRequestContext(T exchange) {
        this.exchange = exchange;
    }

    @Override
    public Object getContext() {
        return exchange;
    }
}
