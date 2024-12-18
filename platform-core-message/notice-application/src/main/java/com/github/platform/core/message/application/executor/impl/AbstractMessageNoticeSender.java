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
        if (Objects.nonNull(templateDto.getRecipient())){
            //添加固定用户
            String[] fixedUsers = templateDto.getRecipient().split(SymbolConstant.comma);
            for (String fixedUser : fixedUsers){
                noticeContext.addRecipient(fixedUser);
            }

        }
        Map<String, String> userMap = getRecipient(noticeContext.getRecipient(),domainEvent.getTenantId());
        VelocityContext context = new VelocityContext(noticeContext.getMetas());
        context.put("userMap",userMap);
        List<String> openIds = new ArrayList<>(userMap.values());
        context.put("users",openIds);
        context.put("sendTime",domainEvent.getSendTime());
        context.put("env", ApplicationContextHolder.getProfile());
        String groupId = getGroupId(noticeContext);
        context.put("hasGroup", StringUtils.isNotEmpty(groupId));
        String title = VelocityUtil.stringTemplateMerge(context, "noticeTitle:"+templateDto.getId(), templateDto.getTitle());
        String content = VelocityUtil.stringTemplateMerge(context, "noticeText:"+templateDto.getId() , templateDto.getContext());
        return sendMessage(groupId,openIds,title,content, domainEvent.getTenantId());
    }

    private static String getGroupId(MessageNoticeContext noticeContext) {
        MessageNoticeContext.NoticeChannelInfo noticeChannelInfo = noticeContext.getNoticeChannelInfo();
        if (Objects.isNull(noticeChannelInfo) || StringUtils.isEmpty(noticeChannelInfo.getGroupId())){
           return null;
        }
        return noticeChannelInfo.getGroupId();
    }


    /**
     * 根据登录用户获取对应通知的用户信息
     * @param users 系统用户登录名
     * @param tenantId 租户ID
     * @return
     */
    abstract Map<String,String> getRecipient(List<String> users,Integer tenantId);

    /**
     * 发送消息
     * @param groupId
     * @param userList
     * @param title
     * @param content
     * @return
     */
    abstract boolean sendMessage(String groupId,List<String> userList,  String title,String content,Integer tenantId);
}
