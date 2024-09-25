package com.github.platform.core.gateway.infra.configuration;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.ReactiveDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.DefaultResponse;
import org.springframework.cloud.client.loadbalancer.Request;
import org.springframework.cloud.client.loadbalancer.Response;
import org.springframework.cloud.loadbalancer.core.ReactorLoadBalancer;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * 自定义负载均衡
 * @Author: yxkong
 * @Date: 2024/9/12
 * @version: 1.0
 */
public class CompositeReactorLoadBalancer implements ReactorLoadBalancer<ServiceInstance> {

    private final ReactiveDiscoveryClient nacosDiscoveryClient;
    private final ReactiveDiscoveryClient eurekaDiscoveryClient;

    public CompositeReactorLoadBalancer(ReactiveDiscoveryClient nacosDiscoveryClient, ReactiveDiscoveryClient eurekaDiscoveryClient) {
        this.nacosDiscoveryClient = nacosDiscoveryClient;
        this.eurekaDiscoveryClient = eurekaDiscoveryClient;
    }

    @Override
    public Mono<Response<ServiceInstance>> choose(Request request) {
        // 从 request 中动态获取 serviceName
        String serviceName = (String) request.getContext();
        // 先从 Nacos 中获取实例，如果 Nacos 没有，再从 Eureka 获取
        return nacosDiscoveryClient.getInstances(serviceName)
                .filter(Objects::nonNull)
                .switchIfEmpty(eurekaDiscoveryClient.getInstances(serviceName))
                .next()
                .map(DefaultResponse::new);
    }


    // 默认的选择方法
    @Override
    public Mono<Response<ServiceInstance>> choose() {
        return this.choose(REQUEST);
    }
}
