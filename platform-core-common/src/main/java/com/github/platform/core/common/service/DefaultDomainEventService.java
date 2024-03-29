package com.github.platform.core.common.service;

import com.github.platform.core.standard.entity.event.MsgContent;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author: yxkong
 * @date: 2023/4/17 5:02 PM
 * @version: 1.0
 */
public class DefaultDomainEventService extends AbstractDomainEventService{
    public DefaultDomainEventService(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
    @Override
    public void publishMq(MsgContent msgContent, String topic) {
    }

    @Override
    public void publishMq(MsgContent msgContent, String topic, String key) {

    }

    @Override
    public void batchPublishMq(List<MsgContent> list, String topic) {

    }

    @Override
    public void batchPublishMq(Map<String, MsgContent> list, String topic) {

    }

    @Override
    public void publishMq(String content, String topic) {

    }
}
