package com.github.platform.core.gateway.infra.service.impl.route;

import com.github.platform.core.common.service.IEventHandlerService;
import com.github.platform.core.common.utils.JsonUtils;
import com.github.platform.core.gateway.infra.service.IRouteOperatorService;
import com.github.platform.core.standard.entity.dto.CommonPublishDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 删除路由事件处理
 * @Author: yxkong
 * @Date: 2024/8/9
 * @version: 1.0
 */
@Service
@Slf4j
public class RouteDeleteEventHandler implements IEventHandlerService<String> {
    @Resource
    private IRouteOperatorService routeOperatorService;
    @Override
    public Pair<Boolean, String> handler(CommonPublishDto<String> dto) {
        String data = dto.getData();
        log.info("接收到删除路由配置事件:{}",data);
        RouteDefinition routeDefinition = JsonUtils.fromJson(data, RouteDefinition.class);
        boolean update = routeOperatorService.delete(routeDefinition.getId());

        return Pair.of(update,update?"删除路由配置成功":"删除路由配置失败！");
    }
}