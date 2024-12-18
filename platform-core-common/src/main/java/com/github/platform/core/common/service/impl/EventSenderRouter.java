package com.github.platform.core.common.service.impl;

import com.github.platform.core.common.configuration.property.DomainEventProperties;
import com.github.platform.core.common.service.EventSender;
import com.github.platform.core.standard.entity.event.DomainEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 事件发送路由
 * @Author: yxkong
 * @Date: 2024/12/16
 * @version: 1.0
 */
@Service("eventSenderRouter")
@Slf4j
public class EventSenderRouter implements EventSender {
    @Autowired
    private  Map<String, EventSender> senderMap;

    @Resource
    private DomainEventProperties domainEventProperties;

    @Override
    public boolean send(DomainEvent event, String topic, String key) {
        EventSender sender = getSender();
        return sender.send(event,topic,key);
    }
    private EventSender getSender(){
        return senderMap.getOrDefault(domainEventProperties.getType()+"EventSender",senderMap.get("kafkaEventSender"));
    }
}
