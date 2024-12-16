package com.github.platform.core.monitor.infra.websocket.service;

import com.github.platform.core.monitor.domain.ws.InMessage;

/**
 * websocket 消息处理
 * @author: yxkong
 * @date: 2023/6/13 11:09 AM
 * @version: 1.0
 */
public interface IWsMessageService {

    /**
     * 消息执行
     * @param wsMessage
     * @return
     */
    boolean executor(InMessage wsMessage, boolean join);
}
