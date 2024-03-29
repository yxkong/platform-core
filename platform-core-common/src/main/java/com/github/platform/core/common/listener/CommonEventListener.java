package com.github.platform.core.common.listener;

import com.github.platform.core.common.event.CommonPublishEvent;
import com.github.platform.core.common.service.IEventHandlerService;
import com.github.platform.core.common.utils.ApplicationContextHolder;
import com.github.platform.core.common.utils.JsonUtils;
import com.github.platform.core.common.utils.StringUtils;
import com.github.platform.core.standard.entity.dto.CommonPublishDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 公共Event 监听器
 * @author: yxkong
 * @date: 2023/9/8 2:28 PM
 * @version: 1.0
 */
@Component
@Slf4j
public class CommonEventListener {
    @EventListener
    public void handleCustomEvent(CommonPublishEvent customEvent) {
        CommonPublishDto dto = customEvent.getCommonPublishDto();
        String handlerBean = dto.getHandlerBean();
        if (StringUtils.isEmpty(handlerBean) ){
            log.error("无效事件:{}", JsonUtils.toJson(dto));
            return;
        }
        IEventHandlerService eventHandlerService = ApplicationContextHolder.getBean(handlerBean, IEventHandlerService.class);
        if (Objects.isNull(eventHandlerService)){
            log.error("未找到bean:{} ,事件详情:{}", handlerBean,JsonUtils.toJson(dto));
            return;
        }
        Pair result = eventHandlerService.handler(dto);
        if (log.isWarnEnabled()){
            log.warn("bean:{}  处理事件：{} ,结果：{}/{} ",handlerBean,JsonUtils.toJson(dto),result.getKey(),result.getValue());
        }
    }

}
