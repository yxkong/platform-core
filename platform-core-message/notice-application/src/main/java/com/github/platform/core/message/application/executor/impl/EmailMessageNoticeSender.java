package com.github.platform.core.message.application.executor.impl;

import com.github.platform.core.common.utils.CollectionUtil;
import com.github.platform.core.common.utils.StringUtils;
import com.github.platform.core.message.domain.dto.SysNoticeChannelConfigDto;
import com.github.platform.core.message.domain.gateway.ISysNoticeChannelConfigGateway;
import com.github.platform.core.message.infra.utils.EmailUtil;
import com.github.platform.core.standard.constant.MessageNoticeChannelTypeEnum;
import com.github.platform.core.standard.constant.ResultStatusEnum;
import com.github.platform.core.standard.constant.SymbolConstant;
import com.github.platform.core.standard.exception.ApplicationException;
import com.github.platform.core.sys.domain.dto.SysUserDto;
import com.github.platform.core.sys.domain.gateway.ISysUserGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 钉钉发送实现
 * @Author: yxkong
 * @Date: 2024/12/17
 * @version: 1.0
 */
@Service("emailMessageNoticeSender")
@Slf4j
public class EmailMessageNoticeSender extends AbstractMessageNoticeSender{
    @Resource
    private ISysUserGateway sysUserGateway;
    @Resource
    private ISysNoticeChannelConfigGateway sysNoticeChannelConfigGateway;
    @Override
    Map<String, String> getRecipient(String templateRecipient,List<String> users,Integer tenantId) {
        Map<String, String> rst = new HashMap<>();
        if (Objects.nonNull(templateRecipient)){
            //添加固定用户
            String[] splits = templateRecipient.split(SymbolConstant.comma);
            for(String str:splits){
                rst.put(str,str);
            }
        }
        //获取邮箱
        List<SysUserDto> sysUserDtos = sysUserGateway.queryByUsers(users, tenantId);
        if (CollectionUtil.isEmpty(sysUserDtos)){
            return Collections.emptyMap();
        }
        rst.putAll(sysUserDtos.stream().filter(s->Objects.nonNull(s.getEmail()))
                .collect(Collectors.toMap(SysUserDto::getLoginName, SysUserDto::getEmail)));
        return rst;
    }

    @Override
    boolean sendMessage(List<String> userList,String carbonCopy, String title, String content,String groupId, Integer tenantId) {
        //查询邮件配置
        SysNoticeChannelConfigDto dto = sysNoticeChannelConfigGateway.findChannel(MessageNoticeChannelTypeEnum.EMAIL.getType(),tenantId);
        if (Objects.isNull(dto)){
            log.error("未找到对应租户{}的邮件配置",tenantId);
            throw new ApplicationException(ResultStatusEnum.NO_DATA.getStatus(),"未找到对应租户的邮件配置");
        }
        // 校验邮件配置是否完整
        if (Objects.isNull(dto.getHost()) || Objects.isNull(dto.getAppKey()) || Objects.isNull(dto.getAppSecret())) {
            log.error("未找到对应租户{}的邮件配置不完整，请检查host,appKey和appSecret",tenantId);
            throw new ApplicationException(ResultStatusEnum.PARAM_ERROR.getStatus(), "邮件配置不完整");
        }
        int port = Objects.nonNull(dto.getPort()) ? dto.getPort() : 465;
        List<String> cc = new ArrayList<>();
        if (StringUtils.isNotEmpty(carbonCopy)){
            cc = Arrays.asList(carbonCopy.split(SymbolConstant.comma));
        }
        return EmailUtil.sendEmail(dto.getHost(),port,dto.getAppKey(),dto.getAppSecret()
                ,userList,cc,null,title,content,true);
    }
}
