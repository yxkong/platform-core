//package com.github.platform.core.loadbalancer;
//
//import org.springframework.beans.factory.ObjectProvider;
//import org.springframework.cloud.client.ServiceInstance;
//import org.springframework.cloud.client.loadbalancer.DefaultResponse;
//import org.springframework.cloud.client.loadbalancer.Request;
//import org.springframework.cloud.client.loadbalancer.Response;
//import org.springframework.cloud.loadbalancer.core.NoopServiceInstanceListSupplier;
//import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;
//import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
//import reactor.core.publisher.Mono;
//
///**
// * 加权轮训实现
// * @author: yxkong
// * @date: 2023/2/23 3:54 PM
// * @version: 1.0
// */
//public class WeightedLoadBalancer  implements ReactorServiceInstanceLoadBalancer {
//
//    final String serviceId;
//    ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider;
//
//    public WeightedLoadBalancer(String serviceId, ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider) {
//        this.serviceId = serviceId;
//        this.serviceInstanceListSupplierProvider = serviceInstanceListSupplierProvider;
//    }
//
//    @Override
//    public Mono<Response<ServiceInstance>> choose(Request request) {
//        ServiceInstanceListSupplier supplier = serviceInstanceListSupplierProvider
//                .getIfAvailable(NoopServiceInstanceListSupplier::new);
//
//        return supplier.get(request)//获取目标IP列表
//                .next()
//                .map(serviceInstances/*目标IP列表*/ -> {//加权算法筛选出目标IP
//                    //获取加权配置信息
//                    //从中筛选出权重最大的IP实例
//                    //减少当前IP的权重值
//                    //返回当前IP实例
//                    return new DefaultResponse(/*目标实例*/);
//                });
//    }
//}
