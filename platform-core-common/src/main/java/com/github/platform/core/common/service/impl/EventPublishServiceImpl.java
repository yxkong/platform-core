package com.github.platform.core.common.service.impl;

import com.github.platform.core.common.event.CommonPublishEvent;
import com.github.platform.core.common.service.IPublishService;
import com.github.platform.core.common.utils.JsonUtils;
import com.github.platform.core.standard.entity.dto.CommonPublishDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

/**
 * 系统事件发送，默认实现
 * @author: yxkong
 * @date: 2023/9/8 12:22 PM
 * @version: 1.0
 */
@Slf4j
public class EventPublishServiceImpl implements IPublishService {
    private ApplicationContext applicationContext;
    public EventPublishServiceImpl(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public boolean publish(CommonPublishDto dto) {
        CommonPublishEvent event = new CommonPublishEvent(this,dto);
        if (log.isWarnEnabled()){
            log.warn("发送消息内容：{}", JsonUtils.toJson(dto));
        }
        this.applicationContext.publishEvent(event);
        return true;
    }
}
