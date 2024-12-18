package com.github.platform.core.message.application.executor;

import com.github.platform.core.message.domain.dto.SysNoticeTemplateDto;
import com.github.platform.core.standard.entity.context.MessageNoticeContext;
import com.github.platform.core.standard.entity.event.DomainEvent;

/**
 * 消息通知发送器
 * @Author: yxkong
 * @Date: 2024/12/16
 * @version: 1.0
 */
public interface IMessageNoticeSender {
    /**
     * 发送
     * @param domainEvent
     * @param noticeContext
     * @param templateDto
     * @return
     */
    boolean send(DomainEvent domainEvent, MessageNoticeContext noticeContext, SysNoticeTemplateDto templateDto);
}
