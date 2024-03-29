package com.github.platform.core.gateway.infra.listener;

import com.github.platform.core.gateway.domain.gateway.RouteDataGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 初始化网关
 * @author: yxkong
 * @date: 2023/2/27 2:51 PM
 * @version: 1.0
 */
@Component
@Slf4j
public class InitGatewayImpl implements ApplicationListener<ApplicationStartedEvent> {
    @Resource
    private RouteDataGateway routeDataGateway;
    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        try {
            //初始化网关
            routeDataGateway.initAll();
            //配置修改后刷新网关
            routeDataGateway.refresh();
        } catch (Exception e) {
            log.error("init gateway is error!", e);
        }
        log.info("init gateway is finished");
    }
}
