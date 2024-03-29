package com.github.platform.core.loadbalancer;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
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
    //灰度轮训器
    private AtomicInteger nextGrayServerCyclicCounter;
    /**
     * nacos配置信息
     */
    private  NacosDiscoveryProperties nacosDiscoveryProperties;

    public GrayLoadBalancer( ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider
            , String serviceId) {
        this.serviceId = serviceId;
        this.serviceInstanceListSupplierProvider = serviceInstanceListSupplierProvider;
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
        String label = null;
        if(headers == null || StringUtils.isEmpty(headers.getFirst(RequestHeaderHolder.LABEL_KEY)) ){
            //标签为空，则随机有效列表
            if (log.isDebugEnabled()){
                log.debug("header or label is null , ");
            }
            label = RequestHeaderHolder.getLabel();
        }else {
            label = headers.getFirst(RequestHeaderHolder.LABEL_KEY);
        }
        if(StringUtils.isEmpty(label)){
            return getInstanceByWeight(serviceInstances);
        }
        //过滤指定label的实例
        String finalLabel = label;
        if (log.isDebugEnabled()){
            log.debug("label is {}",label);
        }
        List<ServiceInstance> instancesFilter = serviceInstances.stream()
                .filter(instance -> finalLabel.equals(instance.getMetadata().get(RequestHeaderHolder.LABEL_KEY)))
                .collect(Collectors.toList());

        if (instancesFilter.size() > 0) {
            serviceInstances = instancesFilter;
        }
        return getInstanceByWeight(serviceInstances);
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
            hostsWithWeight.add(new Pair<>(instance,Double.parseDouble(instance.getMetadata().get("nacos.weight"))));
        }
        Chooser<String, ServiceInstance> vipChooser = new Chooser<>(this.serviceId);
        vipChooser.refresh(hostsWithWeight);
        return new DefaultResponse(vipChooser.randomWithWeight()) ;
    }
    /**
     * 轮训的方式获取（不均匀，）
     * 参考 org.springframework.cloud.loadbalancer.core.RoundRobinLoadBalancer#getInstanceResponse
     * @param instances
     * @return
     */
    private Response<ServiceInstance> getInstanceByRibbon(List<ServiceInstance> instances){
        int pos = Math.abs(this.nextServerCyclicCounter.incrementAndGet());
        ServiceInstance instance = instances.get(pos &(instances.size()-1));
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
