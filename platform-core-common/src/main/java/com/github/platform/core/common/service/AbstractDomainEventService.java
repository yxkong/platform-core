package com.github.platform.core.common.service;

import com.github.platform.core.common.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;

import java.util.List;

/**
 * 领域事件服务实现
 * @author: yxkong
 * @date: 2023/5/31 4:54 PM
 * @version: 1.0
 */
@Slf4j
public abstract class AbstractDomainEventService implements IDomainEventService {
    protected ApplicationContext applicationContext;

    @Override
    public void publish(Object domainEvent) {
        if (log.isDebugEnabled()){
            log.debug("接收到的事件：{},数据体:{}",domainEvent.getClass().getName(), JsonUtils.toJson(domainEvent));
        }
        this.applicationContext.publishEvent(domainEvent);
    }

    @Override
    public void batchPublish(List<Object> domainEvents) {
        for (Object domainEvent : domainEvents) {
            this.applicationContext.publishEvent(domainEvent);
        }
    }
}
