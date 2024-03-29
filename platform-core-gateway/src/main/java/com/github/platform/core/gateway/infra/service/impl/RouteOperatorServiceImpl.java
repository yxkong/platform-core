package com.github.platform.core.gateway.infra.service.impl;

import com.github.platform.core.gateway.infra.service.RouteOperatorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
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
public class RouteOperatorServiceImpl implements RouteOperatorService {


    @Resource
    private  ApplicationEventPublisher publisher;
    @Resource
    private RouteDefinitionRepositoryImpl routeDefinitionRepository;

    @Override
    public boolean add(RouteDefinition routeDefinition){
        try {
            if (Objects.isNull(routeDefinition) || !StringUtils.hasText(routeDefinition.getId())
                    || Objects.nonNull(routeDefinition.getPredicates())){
                return false;
            }
            routeDefinitionRepository.save(Mono.just(routeDefinition)).subscribe();
            //添加以后必须刷新
            publisher.publishEvent(new RefreshRoutesEvent(this));
            return true;
        } catch (Exception e) {
            log.error("add route {} is error",routeDefinition.getId(),e);
            return false;
        }
    }
    @Override
    public Boolean delete(String id){
        try {
            routeDefinitionRepository.delete(Mono.just(id)).subscribe();
            //删除以后必须刷新
            publisher.publishEvent(new RefreshRoutesEvent(this));
            return true;
        } catch (Exception e) {
           log.error("delete route {} is error",id,e);
           return false;
        }
    }
    @Override
    public Boolean update(RouteDefinition routeDefinition){
        try {
            if (Objects.nonNull(routeDefinition) &&  !StringUtils.hasText(routeDefinition.getId())){
                return false;
            }
            routeDefinitionRepository.delete(Mono.just(routeDefinition.getId())).subscribe();
            routeDefinitionRepository.save(Mono.just(routeDefinition)).subscribe();
            publisher.publishEvent(new RefreshRoutesEvent(this));
            return true;
        } catch (Exception e) {
            log.error("update route {} is error",routeDefinition.getId(),e);
            return false;
        }

    }


    @Override
    public Boolean refresh(List<RouteDefinition> list){
        try {
            if (Objects.nonNull(list)){
                routeDefinitionRepository.clear();
                list.forEach(routeDefinition -> {
                    routeDefinitionRepository.save(Mono.just(routeDefinition)).subscribe();
                });
            }
            publisher.publishEvent(new RefreshRoutesEvent(this));
            return true;
        } catch (Exception e) {
            log.error("refresh route  is error",e);
            return false;
        }
    }

}
