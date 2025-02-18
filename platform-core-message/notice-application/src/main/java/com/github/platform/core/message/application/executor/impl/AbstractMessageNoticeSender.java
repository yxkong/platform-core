package com.github.platform.core.message.application.executor.impl;

import com.github.platform.core.common.utils.ApplicationContextHolder;
import com.github.platform.core.common.utils.StringUtils;
import com.github.platform.core.common.utils.VelocityUtil;
import com.github.platform.core.message.application.executor.IMessageNoticeSender;
import com.github.platform.core.message.domain.dto.SysNoticeTemplateDto;
import com.github.platform.core.standard.constant.SymbolConstant;
import com.github.platform.core.standard.entity.context.MessageNoticeContext;
import com.github.platform.core.standard.entity.event.DomainEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.VelocityContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 通知发送
 * @Author: yxkong
 * @Date: 2024/12/16
 * @version: 1.0
 */
@Slf4j
public abstract class AbstractMessageNoticeSender implements IMessageNoticeSender {
    @Override
    public boolean send(DomainEvent domainEvent, MessageNoticeContext noticeContext, SysNoticeTemplateDto templateDto) {
        List<String> recipient = noticeContext.getRecipient();
        if (Objects.isNull(recipient)){
            recipient = new ArrayList<>();
        }
        Map<String, String> userMap = getRecipient(templateDto.getRecipient(),recipient,domainEvent.getTenantId());
        VelocityContext context = new VelocityContext(noticeContext.getMetas());
        context.put("title",noticeContext.getTitle());
        context.put("eventType",noticeContext.getEventType());
        context.put("userMap",userMap);
        List<String> users = new ArrayList<>(userMap.values());
        context.put("users",users);
        context.put("sendTime",domainEvent.getSendTime());
        context.put("env", ApplicationContextHolder.getProfile());
        String groupId = getGroupId(noticeContext);
        context.put("hasGroup", StringUtils.isNotEmpty(groupId));
        String title = VelocityUtil.stringTemplateMerge(context, "noticeTitle:"+templateDto.getId(), templateDto.getTitle());
        String content = VelocityUtil.stringTemplateMerge(context, "noticeText:"+templateDto.getId() , templateDto.getContext());
        return sendMessage(users,templateDto.getCarbonCopy(),title,content,groupId, domainEvent.getTenantId());
    }

    private static String getGroupId(MessageNoticeContext noticeContext) {
        MessageNoticeContext.NoticeChannelInfo noticeChannelInfo = noticeContext.getNoticeChannelInfo();
        if (Objects.isNull(noticeChannelInfo) || StringUtils.isEmpty(noticeChannelInfo.getGroupId())){
            return null;
        }
        return noticeChannelInfo.getGroupId();
    }


    /**
     * 获取对应的用户信息
     * @param templateRecipient 模板配置的用户
     * @param users 系统用户登录名
     * @param tenantId 租户ID
     * @return
     */
    abstract Map<String,String> getRecipient(String templateRecipient,List<String> users,Integer tenantId);

    /**
     * 发送消息
     * @param userList 接收人
     * @param carbonCopy 抄送人
     * @param title 标题
     * @param content 内容
     * @param groupId 群组ID
     * @param tenantId 租户ID
     * @return
     */
    abstract boolean sendMessage(List<String> userList, String carbonCopy, String title,String content,String groupId,Integer tenantId);
}
