package com.github.platform.core.loadbalancer.listener;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.registry.NacosAutoServiceRegistration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;

/**
 * 应用关闭监听器
 *
 * @author: yxkong
 * @date: 2023/2/27 11:06 AM
 * @version: 1.0
 */
@Slf4j
public class ApplicationClosedEventListener implements ApplicationListener<ContextClosedEvent> {
//    @Autowired
//    private NacosDiscoveryProperties nacosDiscoveryProperties;
//    @Autowired
//    private NacosAutoServiceRegistration nacosAutoServiceRegistration;
    @Override
    public void onApplicationEvent(ContextClosedEvent contextClosedEvent) {
//        String service = nacosDiscoveryProperties.getService();
//        String groupName = nacosDiscoveryProperties.getGroup();
//        String clusterName = nacosDiscoveryProperties.getClusterName();
//        String ip = nacosDiscoveryProperties.getIp();
//        int port = nacosDiscoveryProperties.getPort();
//        log.info("application accepts the shutdown request, serviceName:{}, groupName:{}, clusterName:{}, ip:{}, port:{}", service, groupName, clusterName, ip, port);
//        try {
//            //deregister service and nacos service shutDown
//            nacosAutoServiceRegistration.destroy();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        log.info("nacos service registry close serviceName:{}, groupName:{}, clusterName:{}, ip:{}, port:{} finish",service, groupName, clusterName, ip, port);
    }
}
