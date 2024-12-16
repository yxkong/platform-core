package com.github.platform.core.gateway.infra.service.impl;

import com.github.platform.core.common.utils.CollectionUtil;
import com.github.platform.core.gateway.infra.service.IRouteOperatorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * gateway路由规则封装
 *
 * @author: yxkong
 * @date: 2021/12/9 4:50 PM
 * @version: 1.0
 */
@Service
@Slf4j
public class RouteOperatorServiceImpl implements IRouteOperatorService {


    @Resource
    private  ApplicationEventPublisher publisher;
    @Resource
    private RouteDefinitionRepositoryImpl routeDefinitionRepository;

    @Override
    public boolean add(RouteDefinition routeDefinition) {
        try {
            if (Objects.isNull(routeDefinition) || !StringUtils.hasText(routeDefinition.getId())) {
                return false;
            }

            return Boolean.TRUE.equals(routeDefinitionRepository.save(Mono.just(routeDefinition))
                    .then(Mono.fromRunnable(() -> publisher.publishEvent(new RefreshRoutesEvent(this))))
                    .thenReturn(true)
                    .onErrorResume(e -> {
                        log.error("add route {} is error", routeDefinition.getId(), e);
                        return Mono.just(false);
                    })
                    .block());
        } catch (Exception e) {
            log.error("add route {} is error", routeDefinition.getId(), e);
            return false;
        }
    }

    @Override
    public boolean delete(String id){
        try {
            //删除以后必须刷新
            return Boolean.TRUE.equals(routeDefinitionRepository.delete(Mono.just(id))
                    .doOnSuccess(v -> publisher.publishEvent(new RefreshRoutesEvent(this))).thenReturn(true)
                    .onErrorResume(e -> {
                        log.error("update route {} is error", id, e);
                        return Mono.just(false);
                    })
                    .block());
        } catch (Exception e) {
           log.error("delete route {} is error",id,e);
           return false;
        }
    }
    @Override
    public boolean update(RouteDefinition routeDefinition){
        if (Objects.nonNull(routeDefinition) &&  !StringUtils.hasText(routeDefinition.getId())){
            return false;
        }
        //删除路由,保存新的路由定义,发布刷新事件
        // 先解包 routeDefinition.getId() 再传给 delete 方法
        String routeId = routeDefinition.getId();

        try {
            // 忽略未找到的情况继续保存
            // 发布刷新事件
            return Boolean.TRUE.equals(routeDefinitionRepository.delete(Mono.just(routeId))
                    // 忽略未找到的情况继续保存
                    .onErrorResume(NotFoundException.class, e -> Mono.empty())
                    .then(routeDefinitionRepository.save(Mono.just(routeDefinition)))
                    // 发布刷新事件
                    .doOnSuccess(v -> publisher.publishEvent(new RefreshRoutesEvent(this)))
                    .thenReturn(true)
                    .onErrorResume(e -> {
                        log.error("Update route {} failed", routeId, e);
                        return Mono.just(false);
                    })
                    .block());
        } catch (Exception e) {
            log.error("Update route {} is error", routeId, e);
            return false;
        }

    }

    @Override
    public boolean refresh(List<RouteDefinition> list){
        try {
            if (CollectionUtil.isEmpty(list)){
                return false;
            }
            routeDefinitionRepository.getRouteDefinitions().collectList().flatMap(existingRoutes -> {
                existingRoutes.forEach(route -> routeDefinitionRepository.delete(Mono.just(route.getId())).subscribe());
                list.forEach(route -> routeDefinitionRepository.save(Mono.just(route)).subscribe());
                return Mono.empty();
            }).subscribe();
            publisher.publishEvent(new RefreshRoutesEvent(this));
            return true;

        } catch (Exception e) {
            log.error("refresh route  is error",e);
            return false;
        }
    }

}
