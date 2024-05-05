package com.github.platform.core.dingtalk.infra.gateway.impl;

import com.github.platform.core.common.utils.StringUtils;
import com.github.platform.core.dingtalk.domain.constant.DingMessageTemplateTypeEnum;
import com.github.platform.core.dingtalk.domain.gateway.IDingTalkIMGateway;
import com.github.platform.core.dingtalk.infra.configuration.DingProperties;
import com.github.platform.core.dingtalk.infra.service.IDingTalkService;
import com.github.platform.core.standard.util.LocalDateTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 钉钉IM服务
 * @author: yxkong
 * @date: 2024/2/27 10:46
 * @version: 1.0
 */
@Service
@Slf4j
public class DingTalkIMGatewayImpl implements IDingTalkIMGateway {

    @Resource
    private IDingTalkService dingTalkService;
    @Resource
    private DingProperties dingProperties;

    @Override
    public boolean workNoticeText(List<String> userList, String text) {
        return dingTalkService.workNoticeText(userList,text);
    }

    @Override
    public boolean workNoticeMarkDown(List<String> userList, String title, String text) {
        return dingTalkService.workNoticeMarkDown(userList,title,text);
    }

    @Override
    public String createGroup(List<String> userList, String ownerUserId, String title, List<String> subAdminList) {
        if (Objects.isNull(ownerUserId)){
            ownerUserId = dingProperties.getDefaultOwner();
        }
        return dingTalkService.createGroup(dingProperties.getGroupTemplateId(),userList,ownerUserId,title,subAdminList);
    }

    @Override
    public boolean sendMarkdownMessage(String groupId, String title, String markdown) {
        if (StringUtils.isEmpty(groupId)){
            return false;
        }
        Map<String, String> markdownMap = getMarkdownMap(title, markdown);
        return dingTalkService.sendMessage(groupId,dingProperties.getRobotCode(),null,null,true, DingMessageTemplateTypeEnum.markdown,markdownMap);
    }

    @Override
    public boolean sendProcessMessage(String groupId, String title, String nodeKey, String desc) {
        if (StringUtils.isEmpty(groupId)){
            return false;
        }
        String markdown =  String.format(
                "**【节点:】**  %s  \n  " +
                        "**【描述:】**  %s  \n  " +
                        "**【时间:】**  %s  \n  ",
                nodeKey, desc, LocalDateTimeUtil.dateTimeDefault()
        );
        Map<String, String> markdownMap = getMarkdownMap(title, markdown);
        return dingTalkService.sendMessage(groupId,dingProperties.getRobotCode(),null,null,true,DingMessageTemplateTypeEnum.markdown,markdownMap);
    }

    @Override
    public Pair<Boolean, String> groupUserOpt(String groupId, List<String> users, Boolean isAdd) {
        return dingTalkService.groupUserOpt(groupId,users,isAdd);
    }

    private Map<String,String> getMarkdownMap(String title, String markdown){
        Map<String,String> map = new HashMap<>();
        map.put("title",title);
        map.put("markdown_content",markdown);
        return map;
    }
}
