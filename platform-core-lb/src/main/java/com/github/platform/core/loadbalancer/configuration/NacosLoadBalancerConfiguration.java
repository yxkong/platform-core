package com.github.platform.core.loadbalancer.configuration;

import com.alibaba.cloud.nacos.ConditionalOnNacosDiscoveryEnabled;
import com.alibaba.cloud.nacos.loadbalancer.ConditionalOnLoadBalancerNacos;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;
import org.springframework.context.annotation.Configuration;

/**
 * @author: yxkong
 * @date: 2023/2/28 12:07 PM
 * @version: 1.0
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties
@ConditionalOnLoadBalancerNacos
@ConditionalOnNacosDiscoveryEnabled
@LoadBalancerClients(defaultConfiguration = LoadBalanceAutoConfiguration.class)
public class NacosLoadBalancerConfiguration {
}
