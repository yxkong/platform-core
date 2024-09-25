package com.github.platform.core.cache.infra.service.impl;

import com.github.platform.core.cache.infra.configuration.properties.RedisSubscribeProperties;
import com.github.platform.core.common.service.IEventHandlerService;
import com.github.platform.core.common.service.IRedisReceiveMessageService;
import com.github.platform.core.common.utils.ApplicationContextHolder;
import com.github.platform.core.common.utils.JsonUtils;
import com.github.platform.core.common.utils.StringUtils;
import com.github.platform.core.standard.entity.dto.CommonPublishDto;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.Optional;

/**
 * redis订阅者服务
 * @author: yxkong
 * @date: 2023/9/8 2:57 PM
 * @version: 1.0
 */
@Service
@Slf4j
public class RedisSubscriberService implements IRedisReceiveMessageService {
    @Resource
    private RedisSubscribeProperties redisSubscribeProperties;

    @Override
    public Pair<Boolean, String> handleMessage(String message) {
        CommonPublishDto dto = null;
        try{
            dto = JsonUtils.fromJson(message,CommonPublishDto.class);
        } catch (Exception e){
            log.error("无效事件，结构化异常！",e);
            return Pair.of(false,"无效事件");
        }
        if (Objects.isNull(dto) || StringUtils.isEmpty(dto.getHandlerBean()) ){
            log.error("无效事件:{}", JsonUtils.toJson(dto));
            return Pair.of(false,"无效事件");
        }
        //添加配置，用于配置处理哪些事件
        String handlerBean = dto.getHandlerBean();
        String xx = redisSubscribeProperties.getEvents().stream().filter(event -> handlerBean.startsWith(event)).findAny().orElse(null);
        if (Objects.isNull(xx)){
            log.warn("事件类型:{}  不在处理范围内,事件详情:{}", handlerBean,JsonUtils.toJson(dto));
            return Pair.of(false,"事件不在处理范围内，:"+handlerBean);
        }

        if (!ApplicationContextHolder.containsBean(handlerBean)){
            log.error("未找到订阅对应的bean:{} ,事件详情:{}", handlerBean,JsonUtils.toJson(dto));
            return Pair.of(false,"未找到bean:"+handlerBean);
        }
        IEventHandlerService eventHandlerService = ApplicationContextHolder.getBean(handlerBean, IEventHandlerService.class);

        Pair<Boolean,String> result = eventHandlerService.handler(dto);
        if (log.isWarnEnabled()){
            log.warn("bean:{}  处理事件：{} ,结果：{}/{} ",handlerBean,message,result.getKey(),result.getValue());
        }
        return result;
    }
}
