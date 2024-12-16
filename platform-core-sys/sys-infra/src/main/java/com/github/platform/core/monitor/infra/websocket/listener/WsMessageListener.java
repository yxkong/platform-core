package com.github.platform.core.monitor.infra.websocket.listener;

import com.github.platform.core.common.utils.JsonUtils;
import com.github.platform.core.monitor.domain.ws.InMessage;
import com.github.platform.core.monitor.infra.websocket.service.IWsMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * websocket的消息订阅
 * @author: yxkong
 * @date: 2023/6/13 10:40 AM
 * @version: 1.0
 */
@Component
@Slf4j
public class WsMessageListener implements MessageListener {
    @Autowired
    private Map<String, IWsMessageService> wsMessageServiceMap;
    @Override
    public void onMessage(Message message, byte[] pattern) {
        String channel = new String(message.getChannel());
        String body = new String(message.getBody());
        if (log.isDebugEnabled()){
            log.debug("从redis的channel:{} 接收到message:{} ",channel,body);
        }
        InMessage wsMessage = JsonUtils.fromJson(body, InMessage.class);
        if ( null == wsMessage || !wsMessage.isValidBizType()){
            return;
        }
        String mapKey = wsMessage.getBizType()+"WsMessageService";
        wsMessageServiceMap.get(mapKey).executor(wsMessage,false);
    }
}
