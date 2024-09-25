package com.github.platform.core.gateway.infra.service.impl.route;

import com.github.platform.core.common.service.IEventHandlerService;
import com.github.platform.core.common.utils.JsonUtils;
import com.github.platform.core.gateway.infra.configuration.properties.PlatformGatewayProperties;
import com.github.platform.core.gateway.infra.service.IRouteOperatorService;
import com.github.platform.core.standard.entity.dto.CommonPublishDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * 新增路由事件处理
 * @Author: yxkong
 * @Date: 2024/8/9
 * @version: 1.0
 */
@Service
@Slf4j
public class RouteAddEventHandler implements IEventHandlerService<String> {
    @Resource
    private IRouteOperatorService routeOperatorService;
    @Resource(name = "platformGatewayProperties")
    private PlatformGatewayProperties gatewayProperties;
    @Override
    public Pair<Boolean, java.lang.String> handler(CommonPublishDto<String> dto) {
        String data = dto.getData();
        log.info("接收到新增路由配置事件:{}",data);
        if (!Objects.equals(dto.getTargetService(), gatewayProperties.getName())){
            return Pair.of(true,"非本网关数据，不处理！");
        }
        RouteDefinition routeDefinition = JsonUtils.fromJson(data, RouteDefinition.class);
        boolean update = routeOperatorService.add(routeDefinition);
        return Pair.of(update,update?"新增路由配置成功！":"新增路由配置失败！");
    }
}
