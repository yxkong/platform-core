package com.github.platform.core.cache.infra.service.impl;

import com.github.platform.core.common.service.IEventHandlerService;
import com.github.platform.core.common.service.IRedisReceiveMessageService;
import com.github.platform.core.common.utils.ApplicationContextHolder;
import com.github.platform.core.common.utils.JsonUtils;
import com.github.platform.core.common.utils.StringUtils;
import com.github.platform.core.standard.entity.dto.CommonPublishDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * redis订阅者服务
 * @author: yxkong
 * @date: 2023/9/8 2:57 PM
 * @version: 1.0
 */
@Service
@Slf4j
public class RedisSubscriberService implements IRedisReceiveMessageService {
    @Override
    public Pair<Boolean, String> handleMessage(String message) {
        CommonPublishDto dto = JsonUtils.fromJson(message,CommonPublishDto.class);
        String handlerBean = dto.getHandlerBean();
        if (StringUtils.isEmpty(handlerBean) ){
            log.error("无效事件:{}", JsonUtils.toJson(dto));
            return Pair.of(false,"无效事件");
        }
        IEventHandlerService eventHandlerService = ApplicationContextHolder.getBean(handlerBean, IEventHandlerService.class);
        if (Objects.isNull(eventHandlerService)){
            log.error("未找到bean:{} ,事件详情:{}", handlerBean,JsonUtils.toJson(dto));
            return Pair.of(false,"未找到bean:"+handlerBean);
        }
        Pair result = eventHandlerService.handler(dto);
        if (log.isWarnEnabled()){
            log.warn("bean:{}  处理事件：{} ,结果：{}/{} ",handlerBean,message,result.getKey(),result.getValue());
        }
        return result;
    }
}
