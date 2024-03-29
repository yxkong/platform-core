package com.github.platform.core.monitor.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 在线聊天实体
 * @author: yxkong
 * @date: 2023/6/9 11:26 AM
 * @version: 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatDto {
    /**消息id*/
    private Long id;
    /**消息发出者*/
    private String fromUser;
    /**消息接收者*/
    private String toUser;
    /**发送内容*/
    private String text;
    /**消息类型,全局broadcast，点对点p2p*/
    private String msgType;
    /**
     * 发送时间
     */
    private LocalDateTime sendTime;
    /**
     * 接收时间
     */
    private LocalDateTime receiveTime;
    /**消息入库时间*/
    private LocalDateTime createTime;

}
