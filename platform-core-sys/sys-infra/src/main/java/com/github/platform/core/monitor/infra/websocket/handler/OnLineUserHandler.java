package com.github.platform.core.monitor.infra.websocket.handler;

import com.github.platform.core.auth.entity.LoginUserInfo;
import com.github.platform.core.auth.service.ITokenService;
import com.github.platform.core.cache.domain.constant.CacheConstant;
import com.github.platform.core.common.utils.JsonUtils;
import com.github.platform.core.common.utils.StringUtils;
import com.github.platform.core.monitor.domain.dto.ChatDto;
import com.github.platform.core.monitor.domain.ws.InMessage;
import com.github.platform.core.monitor.domain.ws.OutMessage;
import com.github.platform.core.monitor.infra.websocket.WebSocketSessionManager;
import com.github.platform.core.monitor.infra.websocket.WsMessageUtil;
import com.github.platform.core.monitor.domain.constant.WsConstant;
import com.github.platform.core.monitor.infra.websocket.service.IWsMessageService;
import com.github.platform.core.standard.constant.ResultStatusEnum;
import com.github.platform.core.standard.util.LocalDateTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 在线用户websocket处理器
 * @author: yxkong
 * @date: 2023/6/9 10:38 AM
 * @version: 1.0
 */
@Component
@Slf4j
public class OnLineUserHandler extends TextWebSocketHandler {
    @Resource
    private WebSocketSessionManager sessionManager;
    @Resource(name = CacheConstant.sysTokenService)
    private ITokenService tokenService;
    @Autowired
    private Map<String, IWsMessageService> wsMessageServiceMap;
    /**
     * socket建立成事件
     * @param session
     * @throws Exception
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String loginName = (String) session.getAttributes().get("loginName");
        String key = (String) session.getAttributes().get("key");
        sessionManager.add(key,session);
        if (log.isDebugEnabled()){
            log.debug("loginName:{},连接成功",loginName);
        }
        List<ChatDto> list = new ArrayList<>();
        list.add(ChatDto.builder().fromUser("admin").msgType(WsConstant.MSG_TYPE_BROADCAST).toUser(loginName).text("广播消息").sendTime(LocalDateTimeUtil.dateTime()).build());
        list.add(ChatDto.builder().fromUser("admin").msgType(WsConstant.MSG_TYPE_P2P).toUser(loginName).text("点对点消息").sendTime(LocalDateTimeUtil.dateTime()).build());
        /**从数据库拉取未读取消息，并发状态改为已发送*/
        OutMessage message = WsMessageUtil.get(WsConstant.ROBOT, loginName, list);
        session.sendMessage(new TextMessage(JsonUtils.toJson(message)));
    }

    /**
     * 接收文本消息
     * @param session
     * @param message
     * @throws Exception
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String token = (String) session.getAttributes().get("token");
        String loginStr = tokenService.getLoginInfoStr(token);
        //已经退出了,就不让发送信息
        if (StringUtils.isEmpty(loginStr)){
            if (log.isDebugEnabled()){
                log.debug("token:{} 已过期",token);
            }
            ;
            OutMessage wsMessage = OutMessage.builder()
                    .status(ResultStatusEnum.TOKEN_INVALID.getStatus())
                    .message(ResultStatusEnum.TOKEN_INVALID.getMessage())
                    .build();
            session.sendMessage(new TextMessage(JsonUtils.toJson(wsMessage)));
            session.close();
            return;
        }
        if (log.isDebugEnabled()){
            log.debug("接收消息内容：",message.getPayload());
        }
        LoginUserInfo loginUserInfo = JsonUtils.fromJson(loginStr, LoginUserInfo.class);

        InMessage wsMessage = JsonUtils.fromJson(message.getPayload(), InMessage.class);
        if (null != wsMessage && wsMessage.isValidBizType()){
            /**获取接收用户的登录信息*/
            String loginInfo = tokenService.getLoginInfoStr(wsMessage.getLoginInfo().getTenantId(),wsMessage.getToUser());
            wsMessage.setLoginInfoStr(loginInfo);
            String mapKey = wsMessage.getBizType()+"WsMessageService";
            boolean executor = wsMessageServiceMap.get(mapKey).executor(wsMessage,true);
        } else {
            log.error("不识别的消息：{}",message.getPayload());
        }

    }

    /**
     * 断开链接
     * @param session
     * @param status
     * @throws Exception
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String key = (String) session.getAttributes().get("key");
        sessionManager.removeAndCloseWithLocal(key);
    }
}
