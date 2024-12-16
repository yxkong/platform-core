package com.github.platform.core.message.infra.gateway.impl;

import com.github.platform.core.cache.infra.service.impl.RedisCacheServiceImpl;
import com.github.platform.core.common.gateway.BaseGatewayImpl;
import com.github.platform.core.common.utils.CollectionUtil;
import com.github.platform.core.common.utils.JsonUtils;
import com.github.platform.core.common.utils.StringUtils;
import com.github.platform.core.message.dingtalk.constant.DingMessageTemplateTypeEnum;
import com.github.platform.core.message.dingtalk.constant.DingUserTypeEnum;
import com.github.platform.core.message.dingtalk.context.DingTalkContext;
import com.github.platform.core.message.dingtalk.feign.DingBaseFeignClient;
import com.github.platform.core.message.dingtalk.feign.DingContactFeignClient;
import com.github.platform.core.message.dingtalk.feign.DingIMFeignClient;
import com.github.platform.core.message.dingtalk.feign.dto.DingAccessUserDto;
import com.github.platform.core.message.dingtalk.feign.dto.DingDeptDto;
import com.github.platform.core.message.dingtalk.feign.dto.DingUserDto;
import com.github.platform.core.message.dingtalk.service.IDingTalkService;
import com.github.platform.core.message.dingtalk.service.impl.DingTalkServiceImpl;
import com.github.platform.core.message.domain.dto.SysNoticeChannelConfigDto;
import com.github.platform.core.message.domain.gateway.IDingTalkNoticeGateway;
import com.github.platform.core.message.domain.gateway.ISysNoticeChannelConfigGateway;
import com.github.platform.core.standard.constant.MessageNoticeChannelTypeEnum;
import com.github.platform.core.standard.constant.ResultStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**R
 * 钉钉网关服务
 * @Author: yxkong
 * @Date: 2024/12/10
 * @version: 1.0
 */
@Service
@Slf4j
public class DingTalkNoticeGatewayImpl extends BaseGatewayImpl implements IDingTalkNoticeGateway {
    @Resource
    private ISysNoticeChannelConfigGateway sysNoticeChannelConfigGateway;
    @Resource
    private DingBaseFeignClient baseFeignClient;
    @Resource
    private DingContactFeignClient contactFeignClient;
    @Resource
    private DingIMFeignClient imFeignClient;
    @Resource
    private RedisCacheServiceImpl cacheService;

    private DingTalkContext getByTenantId(Integer tenantId){
        SysNoticeChannelConfigDto dto = sysNoticeChannelConfigGateway.findChannel(MessageNoticeChannelTypeEnum.DING_TALK.getType(),tenantId);
        if (Objects.isNull(dto)){
            throw exception(ResultStatusEnum.NO_DATA.getStatus(),"未找到对应渠道的钉钉配置");
        }
        DingTalkContext context = JsonUtils.fromJson(dto.getConfig(),DingTalkContext.class);
        context.setAppKey(dto.getAppKey());
        context.setAppSecret(dto.getAppSecret());
        if (StringUtils.isNotEmpty(dto.getTokenKey())){
            context.setTokenKey(dto.getTokenKey());
        }
        return context;
    }
    private IDingTalkService getDingTalkService(Integer tenantId){
        DingTalkContext context = getByTenantId(tenantId);
        return new DingTalkServiceImpl(context,baseFeignClient,contactFeignClient,imFeignClient,cacheService);
    }
    private Pair<IDingTalkService,DingTalkContext> getDingTalkServiceWithContext(Integer tenantId){
        DingTalkContext context = getByTenantId(tenantId);
        DingTalkServiceImpl dingTalkService = new DingTalkServiceImpl(context, baseFeignClient, contactFeignClient, imFeignClient, cacheService);
        return Pair.of(dingTalkService,context);
    }
    @Override
    public DingAccessUserDto getUserAccessUserInfo(String authCode, Integer tenantId) {
        IDingTalkService dingTalkService = getDingTalkService(tenantId);
        return dingTalkService.getUserAccessUserInfo(authCode);
    }



    @Override
    public String getUserIdByMobile(String mobile, Integer tenantId) {
        IDingTalkService dingTalkService = getDingTalkService(tenantId);
        return dingTalkService.getUserIdByMobile(mobile);
    }

    @Override
    public List<DingDeptDto> getAllDept(Long deptId, Integer tenantId) {
        IDingTalkService dingTalkService = getDingTalkService(tenantId);
        return dingTalkService.getAllDept(deptId);
    }

    @Override
    public List<DingUserDto> getDeptUsers(Long deptId, Integer tenantId) {
        IDingTalkService dingTalkService = getDingTalkService(tenantId);
        return dingTalkService.getDeptUsers(deptId);
    }

    @Override
    public DingUserDto getUserInfo(String dingUserId, Integer tenantId) {
        IDingTalkService dingTalkService = getDingTalkService(tenantId);
        return dingTalkService.getUserInfo(dingUserId);
    }

    @Override
    public boolean workNoticeText(List<String> userList, String text, Integer tenantId) {
        IDingTalkService dingTalkService = getDingTalkService(tenantId);
        return dingTalkService.workNoticeText(userList,text);
    }

    @Override
    public boolean workNoticeMarkDown(List<String> userList, String title, String text, Integer tenantId) {
        IDingTalkService dingTalkService = getDingTalkService(tenantId);
        return dingTalkService.workNoticeMarkDown(userList,title,text);
    }

    @Override
    public String createGroup(String templateId, List<String> userList, String ownerUserId, String title, List<String> subAdminList, Integer tenantId) {
        Pair<IDingTalkService, DingTalkContext> pair = getDingTalkServiceWithContext(tenantId);
        DingTalkContext context = pair.getValue();
        if (StringUtils.isNotEmpty(context.getDefaultOwner())){
            ownerUserId = context.getDefaultOwner();
        }
        if (StringUtils.isEmpty(templateId)){
            templateId = context.getGroupTemplateId();
        }
        return pair.getKey().createGroup(templateId,userList,ownerUserId,title,subAdminList);
    }

    @Override
    public boolean sendMarkdownMessage(String groupId, String title, String markdown, Integer tenantId) {
        Map<String, String> markdownMap = getMarkdownMap(title, markdown);
        return sendMessage(groupId,null,null,true, DingMessageTemplateTypeEnum.markdown,markdownMap,tenantId);
    }

    @Override
    public boolean sendMarkdownMessage(String groupId, String title, String markdown, List<String> users, DingUserTypeEnum type, Integer tenantId) {
        Map<String, String> map = getMarkdownMap(title, markdown);
        boolean atAll = CollectionUtil.isEmpty(users) ? true :false;
        return sendMessage(groupId,users,type,atAll, DingMessageTemplateTypeEnum.markdown,map,tenantId);
    }
    private Map<String,String> getMarkdownMap(String title, String markdown){
        Map<String,String> map = new HashMap<>();
        map.put("title",title);
        map.put("markdown_content",markdown);
        return map;
    }
    @Override
    public boolean sendMessage(String groupId, List<String> users, DingUserTypeEnum userType, boolean atAll, DingMessageTemplateTypeEnum templateType, Map<String, String> map, Integer tenantId) {
        Pair<IDingTalkService, DingTalkContext> pair = getDingTalkServiceWithContext(tenantId);
        return pair.getKey().sendMessage(groupId, pair.getValue().getRobotCode(), users, userType, atAll, templateType, map);
    }

    @Override
    public Pair<Boolean, String> groupUserOpt(String groupId, List<String> users, Boolean isAdd, Integer tenantId) {
        IDingTalkService dingTalkService = getDingTalkService(tenantId);
        return dingTalkService.groupUserOpt(groupId, users, isAdd);
    }
}
