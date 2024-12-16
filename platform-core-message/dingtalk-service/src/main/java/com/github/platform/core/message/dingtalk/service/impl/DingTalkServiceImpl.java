package com.github.platform.core.message.dingtalk.service.impl;

import com.github.platform.core.cache.infra.service.impl.RedisCacheServiceImpl;
import com.github.platform.core.common.service.BaseServiceImpl;
import com.github.platform.core.common.utils.CollectionUtil;
import com.github.platform.core.common.utils.JsonUtils;
import com.github.platform.core.common.utils.StringUtils;
import com.github.platform.core.message.dingtalk.context.DingTalkContext;
import com.github.platform.core.message.dingtalk.constant.DingMessageTemplateTypeEnum;
import com.github.platform.core.message.dingtalk.constant.DingUserTypeEnum;
import com.github.platform.core.message.dingtalk.feign.DingBaseFeignClient;
import com.github.platform.core.message.dingtalk.feign.DingContactFeignClient;
import com.github.platform.core.message.dingtalk.feign.DingIMFeignClient;
import com.github.platform.core.message.dingtalk.feign.command.*;
import com.github.platform.core.message.dingtalk.feign.dto.*;
import com.github.platform.core.message.dingtalk.feign.query.DingDeptQuery;
import com.github.platform.core.message.dingtalk.feign.query.DingDeptUserQuery;
import com.github.platform.core.message.dingtalk.feign.query.DingUserQuery;
import com.github.platform.core.message.dingtalk.service.IDingTalkService;
import com.github.platform.core.standard.constant.ResultStatusEnum;
import com.github.platform.core.standard.constant.SymbolConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 钉钉服务实现
 * @author: yxkong
 * @date: 2024/1/18 17:48
 * @version: 1.0
 */
@Slf4j
public class DingTalkServiceImpl extends BaseServiceImpl implements IDingTalkService {
    private DingTalkContext context;
    private DingBaseFeignClient baseFeignClient;
    private DingContactFeignClient contactFeignClient;
    private DingIMFeignClient imFeignClient;
    private RedisCacheServiceImpl cacheService;

    public DingTalkServiceImpl(DingTalkContext context, DingBaseFeignClient baseFeignClient, DingContactFeignClient contactFeignClient, DingIMFeignClient imFeignClient, RedisCacheServiceImpl cacheService) {
        this.context = context;
        this.baseFeignClient = baseFeignClient;
        this.contactFeignClient = contactFeignClient;
        this.imFeignClient = imFeignClient;
        this.cacheService = cacheService;
    }
    @Override
    public String getAppAccessToken() {

        String token = (String)cacheService.get(context.getTokenKey());
        if(StringUtils.isNotEmpty(token)){
            return token;
        }
        DingAppAccessTokenCmd cmd = new DingAppAccessTokenCmd(context.getAppKey(), context.getAppSecret());
        DingAppAccessTokenDto result = baseFeignClient.getAccessToken(cmd);

        if(Objects.isNull(result)){
            throw exception(ResultStatusEnum.NO_DATA.getStatus(),"获取accessToken异常！");
        }
        token = result.getAccessToken();
        Long expireIn = result.getExpireIn();
        if (log.isDebugEnabled()){
            log.debug("获取token：{}  有效时间：{} 秒",token,expireIn);
        }
        //钉钉有效时间为2小时，防止有问题，提前10分钟失效
        long expireTime = expireIn - 10*60;
        if (expireTime >0 ){
            cacheService.set(context.getTokenKey(), token, expireTime);
        }
        return token;
    }


    @Override
    public DingAccessUserDto getUserAccessUserInfo(String authCode) {
        DingUserAccessTokenCmd cmd  = new DingUserAccessTokenCmd(context.getAppKey(), context.getAppSecret(),authCode);
        DingUserAccessTokenDto result = baseFeignClient.getUserAccessToken(cmd);
        if (Objects.isNull(result)){
            throw exception(ResultStatusEnum.NO_DATA.getStatus(),"获取用户accessToken异常！");
        }
        DingAccessUserDto userDto = baseFeignClient.getUserInfo(result.getAccessToken(), "me");
        if (Objects.isNull(userDto)){
            throw exception(ResultStatusEnum.NO_DATA.getStatus(),"根据token获取用户信息异常！");
        }
        userDto.setRefreshToken(result.getRefreshToken());
        return userDto;
    }

    @Override
    public String getUserIdByMobile(String mobile) {
        String token = getAppAccessToken();
        DingResultBean<DingAccessUserDto> resultBean = contactFeignClient.getUserIdByMobile(token, new DingMobileQuery(mobile));
        if (resultBean.isSuc()){
            return resultBean.getResult().getUserId();
        }
        return null;
    }

    @Override
    public List<DingDeptDto> getAllDept(Long deptId) {
        List<DingDeptDto> rst = new ArrayList<>();
        String token = getAppAccessToken();
        getDept(token, deptId,rst);
        return rst;
    }
    private void getDept(String accessToken,Long deptId,List<DingDeptDto> rst){
        DingDeptQuery query = DingDeptQuery.builder().deptId(deptId).build();
        DingResultBean<List<DingDeptDto>> result = contactFeignClient.listSub(accessToken, query);
        if(!result.isSuc()){
            log.error("调用钉钉异常getDepartmentList{}", result);
        }
        List<DingDeptDto> list = result.getResult();
        if (CollectionUtil.isNotEmpty(list)){
            rst.addAll(list);
            list.forEach(s->{
                getDept(accessToken, s.getDeptId(),rst);
            });

        }
    }
    @Override
    public List<DingUserDto> getDeptUsers(Long deptId) {
        String token = getAppAccessToken();
        List<DingUserDto> rst = new ArrayList<>();
        DingDeptUserDto dto = new DingDeptUserDto();
        dto.setHasMore(true);
        /**最开始传0，后续传返回参数中的next_cursor值*/
        dto.setNextCursor(0);
        while(dto.isHasMore()){
            DingDeptUserQuery query = new DingDeptUserQuery();
            query.setDeptId(deptId);
            query.setSize(50);
            query.setCursor(dto.getNextCursor());
            DingResultBean<DingDeptUserDto> result = contactFeignClient.getDeptUsers(token, query);
            if(!result.isSuc()){
                log.error("调用钉钉异常getDepartmentUserInfo{}", result);
            }
            dto = result.getResult();
            rst.addAll(dto.getList());
        }
        return rst;
    }

    @Override
    public DingUserDto getUserInfo(String dingUserId) {
        String token = getAppAccessToken();
        DingUserQuery query = DingUserQuery.builder().userId(dingUserId).build();
        DingResultBean<DingUserDto> result = contactFeignClient.getUserInfo(token, query);
        if(!result.isSuc()){
            log.error("调用钉钉异常getUserInfo{}", result);
        }
        return result.getResult();
    }

    @Override
    public boolean workNoticeText(List<String> userList, String text) {
        if (validate(userList, text)) return false;
        String token = getAppAccessToken();
        DingWorkNoticeCmd cmd = new DingWorkNoticeCmd();
        if (CollectionUtil.isNotEmpty(userList)){
            cmd.setUserIdList(String.join(SymbolConstant.comma,userList));
        }
        cmd.addText(text);
        DingResultBean<String> response = imFeignClient.workNotice(token, cmd);
        if (response.isSuc() && StringUtils.isNotEmpty(response.getResult())){
            return true;
        }
        return false;
    }

    @Override
    public boolean workNoticeMarkDown(List<String> userList,String title, String text) {
        if (validate(userList, title)) return false;
        DingWorkNoticeCmd cmd = new DingWorkNoticeCmd();
        cmd.setAgentId(context.getAgentId());
        if (CollectionUtil.isNotEmpty(userList)){
            cmd.setUserIdList(String.join(SymbolConstant.comma,userList));
        }
        log.warn("工作通知：用户{} 标题：{} 内容：{}",cmd.getUserIdList(),title,text);
        String token = getAppAccessToken();
        cmd.addMarkdown(title,text);
        DingResultBean<String> response = imFeignClient.workNotice(token, cmd);
        if (response.isSuc() && StringUtils.isNotEmpty(response.getResult())){
            return true;
        }
        return false;
    }

    private static boolean validate(List<String> userList, String  title) {
        if (CollectionUtil.isEmpty(userList) || StringUtils.isEmpty(title)) {
            return true;
        }
        return false;
    }

    @Override
    public String createGroup(String templateId,List<String> userList, String ownerUserId, String title,List<String> subAdminList) {
        String token = getAppAccessToken();
        DingCreateGroupCmd cmd = DingCreateGroupCmd.builder().title(title)
                .ownerUserId(ownerUserId)
                .templateId(templateId)
                .build();
        if (CollectionUtil.isNotEmpty(userList)){
            cmd.setUserIds(String.join(SymbolConstant.comma,userList));
        }
        if (CollectionUtil.isNotEmpty(subAdminList)){
            cmd.setSubAdminIds(String.join(SymbolConstant.comma,subAdminList));
        }

        DingResultBean<DingCreateGroupDto> response = imFeignClient.createGroup(token, cmd);
        if(response.isSuccess()){
            return response.getResult().getOpenConversationId();
        }
        return null;
    }

    @Override
    public boolean sendMessage(String groupId, String robotCode, List<String> users, DingUserTypeEnum userType, boolean atAll, DingMessageTemplateTypeEnum templateType, Map<String,String> map) {
        String token = getAppAccessToken();
        String message = JsonUtils.toJson(map);
        DingSendMessageCmd cmd = DingSendMessageCmd.builder()
                .isAtAll(atAll)
                .targetOpenConversationId(groupId)
                .robotCode(robotCode)
                .msgTemplateId(templateType.getType())
                .build();

        if (DingMessageTemplateTypeEnum.isMsgParamMap(templateType)){
            cmd.setMsgParamMap(message);
        } else {
            cmd.setMsgMediaIdParamMap(message);
        }
        if (!atAll && CollectionUtil.isNotEmpty(users)){
            if (DingUserTypeEnum.mobile.equals(userType)){
                cmd.setAtMobiles(String.join(SymbolConstant.comma,users));
            }
            if (DingUserTypeEnum.userId.equals(userType)){
                cmd.setAtUsers(String.join(SymbolConstant.comma,users));
            }
        }
        DingSendMessageDto result = imFeignClient.sendMessage(token, cmd);
        if(result.isSuccess()){
            if (log.isDebugEnabled()){
                log.debug("groupId:{}, atAll:{} ,message:{} 发送 msgId为：{}",groupId,atAll,message,result.getOpenMsgId());
            }
            return true;
        }
        return false;
    }

    @Override
    public Pair<Boolean,String> groupUserOpt(String groupId, List<String> users, Boolean isAdd) {
        if (StringUtils.isEmpty(groupId) || CollectionUtil.isEmpty(users)){
            return Pair.of(false,"groupId或users 为空");
        }
        String token = getAppAccessToken();
        DingGroupUserCmd cmd = DingGroupUserCmd.builder().openConversationId(groupId).userIds(String.join(SymbolConstant.comma, users)).build();
        DingResultBean dingResultBean = null;
        if (isAdd){
            dingResultBean = imFeignClient.groupAddUser(token, cmd);
        } else {
            dingResultBean = imFeignClient.groupDeleteUser(token, cmd);
        }
        return Pair.of(dingResultBean.isSuc(),dingResultBean.getMsg());
    }

}
