package com.github.platform.core.loadbalancer;

import com.alibaba.nacos.client.naming.utils.Chooser;
import com.alibaba.nacos.client.naming.utils.Pair;
import com.github.platform.core.loadbalancer.holder.RequestHeaderHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.*;
import org.springframework.cloud.loadbalancer.core.NoopServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author: yxkong
 * @date: 2023/2/23 5:20 PM
 * @version: 1.0
 */
@Slf4j
public class GrayLoadBalancer implements ReactorServiceInstanceLoadBalancer {
    /**
     *  loadbalancer 提供的访问当前服务的名称
     */
    private final String serviceId;
    /**
     * loadbalancer提供的访问服务列表
     */
    private ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider;
    //普通server轮训器
    private AtomicInteger nextServerCyclicCounter;
    private final Environment environment;

    public GrayLoadBalancer( ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider
            , String serviceId, Environment environment) {
        this.serviceId = serviceId;
        this.serviceInstanceListSupplierProvider = serviceInstanceListSupplierProvider;
        this.environment = environment;
        this.nextServerCyclicCounter = new AtomicInteger(0);
    }

    @Override
    public Mono<Response<ServiceInstance>> choose(Request request) {
        HttpHeaders headers = null;
        if(request instanceof RequestDataContext){
            headers = ((RequestDataContext) request).getClientRequest().getHeaders();
        }else if(request instanceof DefaultRequest){
            if( request.getContext() instanceof HttpHeaders){
                headers  = (HttpHeaders)request.getContext();
            }else if( request.getContext() instanceof RetryableRequestContext){
                RetryableRequestContext context =(RetryableRequestContext) request.getContext();
                headers = context.getClientRequest().getHeaders();
            }
        }
        //获取所有可用的服务提供者
        ServiceInstanceListSupplier supplier = serviceInstanceListSupplierProvider.getIfAvailable(NoopServiceInstanceListSupplier::new);
        HttpHeaders finalHeaders = headers;
        return supplier.get().next().map(serviceInstances -> getInstanceResponse(serviceInstances, finalHeaders));
    }

    private Response<ServiceInstance> getInstanceResponse(List<ServiceInstance> serviceInstances, HttpHeaders headers) {
        if (serviceInstances.isEmpty()) {
            log.warn("No servers available for service: " + this.serviceId);
            return new EmptyResponse();
        }
        String label = headers != null ? headers.getFirst(RequestHeaderHolder.LABEL_KEY) : RequestHeaderHolder.getLabel();

        if (StringUtils.isEmpty(label)) {
            // 如果没有灰度标签，走普通的负载均衡逻辑
            return getInstanceByDefault(serviceInstances);
        }

        // 根据灰度标签进行过滤
        List<ServiceInstance> instancesFilter = serviceInstances.stream()
                .filter(instance -> label.equals(instance.getMetadata().get(RequestHeaderHolder.LABEL_KEY)))
                .collect(Collectors.toList());

        if (instancesFilter.isEmpty()) {
            log.warn("No servers available for gray label: " + label);
            return new EmptyResponse();
        }

        // 根据权重选择灰度实例
        return getInstanceByWeight(instancesFilter);
    }

    /**
     * 根据权重选择
     * see com.alibaba.nacos.client.naming.core.Balancer#getHostByRandomWeight
     * @param instances
     * @return
     */
    private Response<ServiceInstance> getInstanceByWeight(List<ServiceInstance> instances){
        List<Pair<ServiceInstance>> hostsWithWeight = new ArrayList<>();
        for (ServiceInstance instance : instances) {
            // 兼容Nacos和Eureka的权重字段
            String weightStr = instance.getMetadata().getOrDefault("nacos.weight",
                    instance.getMetadata().getOrDefault("eureka.weight", "1.0"));
            double weight = Double.parseDouble(weightStr);
            hostsWithWeight.add(new Pair<>(instance, weight));
        }
        Chooser<String, ServiceInstance> vipChooser = new Chooser<>(this.serviceId);
        vipChooser.refresh(hostsWithWeight);
        return new DefaultResponse(vipChooser.randomWithWeight());
    }
    /**
     * 根据配置选择默认负载均衡策略
     */
    private Response<ServiceInstance> getInstanceByDefault(List<ServiceInstance> instances) {
        String strategy = environment.getProperty("loadbalancer.default-strategy", "random");
        switch (strategy.toLowerCase()) {
            case "round-robin":
                return getInstanceByRoundRobin(instances);
            case "random":
            default:
                return getInstanceByRandom(instances);
        }
    }

    /**
     * 轮训的方式获取（不均匀，）
     * 参考 org.springframework.cloud.loadbalancer.core.RoundRobinLoadBalancer#getInstanceResponse
     * @param instances
     * @return
     */
    private Response<ServiceInstance> getInstanceByRoundRobin(List<ServiceInstance> instances){
        int pos = Math.abs(this.nextServerCyclicCounter.incrementAndGet());
        // 考虑灰度实例和非灰度实例的权重差异
        ServiceInstance instance = instances.get(pos % instances.size());
        // 防止溢出，当达到Integer.MAX_VALUE时，尝试重置为0
        if (pos >= Integer.MAX_VALUE - 1) {
            // 只有一个线程能够成功重置，避免并发问题
            this.nextServerCyclicCounter.compareAndSet(Integer.MAX_VALUE, 0);
        }
        return new DefaultResponse(instance);
    }

    /**
     * 随机方式获取
     * @param instances
     * @return
     */
    private Response<ServiceInstance> getInstanceByRandom(List<ServiceInstance> instances) {
        if (instances.isEmpty()) {
            if (log.isWarnEnabled()) {
                log.warn("No servers available for service: " + serviceId);
            }
            return new EmptyResponse();
        }
        int index = ThreadLocalRandom.current().nextInt(instances.size());
        ServiceInstance instance = instances.get(index);
        return new DefaultResponse(instance);
    }
}
