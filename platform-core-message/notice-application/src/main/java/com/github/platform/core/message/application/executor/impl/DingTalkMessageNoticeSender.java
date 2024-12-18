package com.github.platform.core.message.application.executor.impl;

import com.github.platform.core.common.utils.CollectionUtil;
import com.github.platform.core.common.utils.StringUtils;
import com.github.platform.core.message.dingtalk.constant.DingUserTypeEnum;
import com.github.platform.core.message.domain.gateway.IDingTalkNoticeGateway;
import com.github.platform.core.standard.constant.UserChannelEnum;
import com.github.platform.core.sys.domain.dto.SysThirdUserDto;
import com.github.platform.core.sys.domain.gateway.ISysCommonGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 钉钉发送实现
 * @Author: yxkong
 * @Date: 2024/12/17
 * @version: 1.0
 */
@Service("dingTalkMessageNoticeSender")
@Slf4j
public class DingTalkMessageNoticeSender extends AbstractMessageNoticeSender{
    @Resource
    private IDingTalkNoticeGateway dingTalkNoticeGateway;
    @Resource
    private ISysCommonGateway sysCommonGateway;
    @Override
    Map<String, String> getRecipient(List<String> users,Integer tenantId) {
        List<SysThirdUserDto> thirdUserDtos = sysCommonGateway.queryChannelUsers(users, UserChannelEnum.thirdDing,tenantId);
        if (CollectionUtil.isEmpty(thirdUserDtos)){
            return Collections.emptyMap();
        }
        return thirdUserDtos.stream()
                .collect(Collectors.toMap(SysThirdUserDto::getLoginName, SysThirdUserDto::getOpenId));
    }

    @Override
    boolean sendMessage(String groupId, List<String> userList, String title, String content,Integer tenantId) {
        if (StringUtils.isEmpty(groupId)){
            // 发送工作通知
            return dingTalkNoticeGateway.workNoticeMarkDown(userList,title,content,tenantId);
        }else {
            return dingTalkNoticeGateway.sendMarkdownMessage(groupId,title, content,userList,DingUserTypeEnum.userId, tenantId);
        }
    }
}
