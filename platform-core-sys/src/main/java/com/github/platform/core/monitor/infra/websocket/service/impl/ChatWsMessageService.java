package com.github.platform.core.monitor.infra.websocket.service.impl;

import com.github.platform.core.auth.entity.LoginUserInfo;
import com.github.platform.core.common.utils.JsonUtils;
import com.github.platform.core.monitor.domain.dto.ChatDto;
import com.github.platform.core.monitor.domain.ws.InMessage;
import com.github.platform.core.monitor.domain.ws.OutMessage;
import com.github.platform.core.monitor.infra.websocket.WebSocketSessionManager;
import com.github.platform.core.monitor.infra.websocket.WsMessageUtil;
import com.github.platform.core.monitor.infra.websocket.constant.WsConstant;
import com.github.platform.core.monitor.infra.websocket.service.IWsMessageService;
import com.github.platform.core.standard.util.LocalDateTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 聊天消息处理
 * @author: yxkong
 * @date: 2023/6/13 11:10 AM
 * @version: 1.0
 */
@Service("wsChatWsMessageService")
@Slf4j
public class ChatWsMessageService implements IWsMessageService {
    @Resource
    private WebSocketSessionManager sessionManager;
    @Resource(name = "stringRedisTemplate")
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public boolean executor(InMessage wsMessage, boolean join) {
        LocalDateTime dateTime = LocalDateTimeUtil.dateTime();
        String message =  wsMessage.getText();
        ChatDto dto = ChatDto.builder()
                .fromUser(wsMessage.getFromUser())
                .toUser(wsMessage.getToUser())
                .receiveTime(wsMessage.getSendTime())
                .text(message)
                .build();
        try {
            if (join){
                dto.setReceiveTime(dateTime);
                dto.setCreateTime(dateTime);
                wsMessage.setSendTime(dateTime);
                // TODO 消息先入库,初始状态
            }
            LoginUserInfo loginInfo = wsMessage.getLoginInfo();
            // 先本地发送，发送失败，就扔到队列里
            OutMessage outMessage = WsMessageUtil.get(wsMessage,dto);
            boolean sendToUser = sessionManager.sendToUser(loginInfo.getWebSocketKey(), JsonUtils.toJson(outMessage));
            if (sendToUser){
                //TODO 发送成功，更新消息状态
            } else if (!sendToUser && join ){
                stringRedisTemplate.convertAndSend(WsConstant.BIZ_TYPE_CHAT,JsonUtils.toJson(wsMessage));
            }
        } catch (IOException e) {
            log.error("",e);
        }
        return false;
    }

}
