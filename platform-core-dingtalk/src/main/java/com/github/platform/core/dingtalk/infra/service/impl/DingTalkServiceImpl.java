package com.github.platform.core.dingtalk.infra.service.impl;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.github.platform.core.common.service.BaseServiceImpl;
import com.github.platform.core.common.utils.CollectionUtil;
import com.github.platform.core.common.utils.JsonUtils;
import com.github.platform.core.dingtalk.domain.constant.DingMessageTemplateTypeEnum;
import com.github.platform.core.dingtalk.domain.constant.DingUserTypeEnum;
import com.github.platform.core.dingtalk.infra.configuration.DingProperties;
import com.github.platform.core.dingtalk.infra.rpc.external.DingBaseFeignClient;
import com.github.platform.core.dingtalk.infra.rpc.external.DingContactFeignClient;
import com.github.platform.core.dingtalk.infra.rpc.external.DingIMFeignClient;
import com.github.platform.core.dingtalk.infra.rpc.external.command.DingAppAccessTokenCmd;
import com.github.platform.core.dingtalk.infra.rpc.external.command.DingCreateGroupCmd;
import com.github.platform.core.dingtalk.infra.rpc.external.command.DingGroupUserCmd;
import com.github.platform.core.dingtalk.infra.rpc.external.command.DingSendMessageCmd;
import com.github.platform.core.dingtalk.infra.rpc.external.dto.*;
import com.github.platform.core.dingtalk.infra.rpc.external.query.DingDeptQuery;
import com.github.platform.core.dingtalk.infra.rpc.external.query.DingDeptUserQuery;
import com.github.platform.core.dingtalk.infra.rpc.external.query.DingUserQuery;
import com.github.platform.core.dingtalk.infra.service.IDingTalkService;
import com.github.platform.core.standard.constant.SymbolConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 钉钉服务实现
 * @author: yxkong
 * @date: 2024/1/18 17:48
 * @version: 1.0
 */
@Service
@Slf4j
public class DingTalkServiceImpl extends BaseServiceImpl implements IDingTalkService {

    @Resource
    private DingProperties dingProperties;
    @Resource
    private DingBaseFeignClient baseFeignClient;
    @Resource
    private DingContactFeignClient contactFeignClient;
    @Resource
    private DingIMFeignClient imFeignClient;
    @Resource(name="stringRedisTemplate")
    private RedisTemplate redisTemplate;
    @Override
    public String getAccessToken() {
        String token = (String) redisTemplate.opsForValue().get(dingProperties.getTokenKey());
        if(StringUtils.isNotEmpty(token)){
            return token;
        }
        DingAppAccessTokenCmd cmd = new DingAppAccessTokenCmd(dingProperties.getAppKey(),dingProperties.getAppSecret());
        DingAppAccessTokenDto result = baseFeignClient.getAccessToken(cmd);

        if(Objects.isNull(result)){
            exception("1000","获取accessToken异常！");
        }
        token = result.getAccessToken();
        Long expireIn = result.getExpireIn();
        if (log.isDebugEnabled()){
            log.debug("获取token：{}  有效时间：{} 秒",token,expireIn);
        }
        //钉钉有效时间为2小时，防止有问题，提前10分钟失效
        long expireTime = expireIn - 10*60;
        if (expireTime >0 ){
            redisTemplate.opsForValue().set(dingProperties.getTokenKey(), token, expireTime, TimeUnit.SECONDS);
        }
        return token;
    }

    @Override
    public List<DingDeptDto> getALLDept(Long deptId) {
        List<DingDeptDto> rst = new ArrayList<>();
        String token = getAccessToken();
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
        String token = getAccessToken();
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
        String token = getAccessToken();
        DingUserQuery query = DingUserQuery.builder().userId(dingUserId).build();
        DingResultBean<DingUserDto> result = contactFeignClient.getUserInfo(token, query);
        if(!result.isSuc()){
            log.error("调用钉钉异常getUserInfo{}", result);
        }
        return result.getResult();
    }

    @Override
    public String createGroup(String templateId,List<String> userList, String ownerUserId, String title,List<String> subAdminList) {
        String token = getAccessToken();
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
        String token = getAccessToken();
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
        String token = getAccessToken();
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
