package com.github.platform.core.monitor.infra.websocket;

import com.github.platform.core.monitor.domain.dto.ChatDto;
import com.github.platform.core.monitor.domain.ws.InMessage;
import com.github.platform.core.monitor.domain.ws.OutMessage;
import com.github.platform.core.monitor.infra.websocket.constant.WsConstant;
import com.github.platform.core.standard.constant.ResultStatusEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * websocket消息工具类
 * @author: yxkong
 * @date: 2023/7/3 7:07 PM
 * @version: 1.0
 */
public class WsMessageUtil {

    public static OutMessage get(InMessage m){
        List<ChatDto> list = new ArrayList<>();
        list.add(getChat(m,WsConstant.MSG_TYPE_P2P));
        return get(m,list);
    }
    public static OutMessage get(InMessage m,ChatDto dto){
        List<ChatDto> list = new ArrayList<>();
        list.add(dto);
        return get(m,list);
    }
    public static OutMessage get(InMessage m, List<ChatDto> list){
        return get(m.getFromUser(),m.getToUser(),list);
    }
    public static OutMessage get(String fromUser,String toUser, List<ChatDto> list){
        return OutMessage.builder()
                .fromUser(fromUser)
                .toUser(toUser)
                .bizType(WsConstant.BIZ_TYPE_CHAT)
                .status(ResultStatusEnum.SUCCESS.getStatus())
                .data(list)
                .build();
    }

    public static ChatDto getChat(InMessage m,String msgType){
        return ChatDto.builder()
                .msgType(msgType)
                .fromUser(m.getFromUser())
                .toUser(m.getToUser())
                .text(m.getText())
                .build();
    }
}
