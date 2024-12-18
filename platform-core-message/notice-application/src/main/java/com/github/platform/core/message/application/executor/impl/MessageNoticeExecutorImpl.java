package com.github.platform.core.message.application.executor.impl;

import com.github.platform.core.common.utils.JsonUtils;
import com.github.platform.core.common.utils.StringUtils;
import com.github.platform.core.message.application.executor.IMessageNoticeExecutor;
import com.github.platform.core.message.application.executor.IMessageNoticeSender;
import com.github.platform.core.message.domain.context.SysNoticeEventLogContext;
import com.github.platform.core.message.domain.dto.SysNoticeEventLogDto;
import com.github.platform.core.message.domain.dto.SysNoticeTemplateDto;
import com.github.platform.core.message.domain.gateway.ISysNoticeEventLogGateway;
import com.github.platform.core.message.domain.gateway.ISysNoticeTemplateGateway;
import com.github.platform.core.message.infra.configuration.properties.NoticeProperties;
import com.github.platform.core.standard.constant.StatusEnum;
import com.github.platform.core.standard.entity.context.MessageNoticeContext;
import com.github.platform.core.standard.entity.event.DomainEvent;
import com.github.platform.core.standard.util.LocalDateTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Objects;

/**
 * 消息通知执行器实现
 * @Author: yxkong
 * @Date: 2024/12/16
 * @version: 1.0
 */
@Service
@Slf4j
public class MessageNoticeExecutorImpl implements IMessageNoticeExecutor {

    @Resource
    private ISysNoticeTemplateGateway sysNoticeTemplateGateway;
    @Resource
    private ISysNoticeEventLogGateway sysNoticeEventLogGateway;
    @Autowired
    private Map<String, IMessageNoticeSender> senderMap;
    @Resource
    private NoticeProperties noticeProperties;
    @Override
    public boolean execute(DomainEvent domainEvent) {
        MessageNoticeContext noticeContext = JsonUtils.convertValue(domainEvent.getData(),MessageNoticeContext.class) ;
        if (StringUtils.isEmpty(noticeContext.getNoticeChannelInfo().getChannelType())){
            noticeContext.getNoticeChannelInfo().setChannelType(noticeProperties.getChannelType());
        }
        Long logId =  null;
        SysNoticeEventLogDto dto = sysNoticeEventLogGateway.findByMsgId(domainEvent.getMsgId());
        if(Objects.isNull(dto)){
            logId = logRecord(domainEvent, noticeContext);
        } else {
            // 已经发送成功的，不再处理
            if (dto.isOn()){
                return true;
            }
            logId = dto.getId();
        }
        SysNoticeTemplateDto templateDto = getSysNoticeTemplateDto(noticeContext, domainEvent);

        if (Objects.isNull(templateDto)){
            updateLog(logId,null, StatusEnum.ERROR.getStatus(), "对应模板不存在！");
            log.error("eventType:{} 租户：{} 对应的模板不存在",noticeContext.getEventType(),domainEvent.getTenantId());
            return false;
        }
        String channelType = Objects.nonNull(noticeContext.getNoticeChannelInfo().getChannelType()) ? noticeContext.getNoticeChannelInfo().getChannelType(): templateDto.getChannelType();
        // 查到通道
        IMessageNoticeSender messageNoticeSender = senderMap.get(channelType+"MessageNoticeSender");
        if (Objects.isNull(messageNoticeSender)){
            updateLog(logId,null, StatusEnum.ERROR.getStatus(), "对应通道实现不存在！");
            log.error("channelType:{} 租户：{} 对应的通道实现不存在",channelType,domainEvent.getTenantId());
            return false;
        }
        boolean send = messageNoticeSender.send(domainEvent, noticeContext, templateDto);
        if (send){
            updateLog(logId, domainEvent.getMsgId(), StatusEnum.ON.getStatus(), "");
        }
        return send;
    }

    private SysNoticeTemplateDto getSysNoticeTemplateDto(MessageNoticeContext noticeContext, DomainEvent domainEvent) {
        SysNoticeTemplateDto templateDto = sysNoticeTemplateGateway.findByTempNo(noticeContext.getTempNo());
        if (Objects.isNull(templateDto)){
            templateDto =  sysNoticeTemplateGateway.findEventType(noticeContext.getEventType(), domainEvent.getTenantId());
        }
        return templateDto;
    }
    private void updateLog(Long id,String msgId,Integer status,String remark){
        SysNoticeEventLogContext sysNoticeEventLogContext = SysNoticeEventLogContext.builder()
                .id(id)
                .msgId(msgId)
                .status(status)
                .remark(remark)
                .build();
        sysNoticeEventLogGateway.update(sysNoticeEventLogContext);
    }

    private Long logRecord(DomainEvent domainEvent,MessageNoticeContext noticeContext){
        SysNoticeEventLogContext sysNoticeEventLogContext = SysNoticeEventLogContext.builder()
                .eventType(noticeContext.getEventType())
                .module(domainEvent.getAppInfo().getModule())
                .node(domainEvent.getAppInfo().getNode())
                .msgId(domainEvent.getMsgId())
                .payload(JsonUtils.toJson(domainEvent))
                .createBy(domainEvent.getUserInfo().getLoginName())
                .createTime(LocalDateTimeUtil.dateTime())
                .status(StatusEnum.OFF.getStatus())
                .tenantId(domainEvent.getTenantId())
                .build();
        return sysNoticeEventLogGateway.insert(sysNoticeEventLogContext).getId();
    }

}