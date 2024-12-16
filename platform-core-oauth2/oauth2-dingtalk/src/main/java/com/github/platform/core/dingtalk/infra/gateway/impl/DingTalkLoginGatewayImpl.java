package com.github.platform.core.dingtalk.infra.gateway.impl;

import com.github.platform.core.message.dingtalk.feign.dto.DingAccessUserDto;
import com.github.platform.core.message.domain.gateway.IDingTalkNoticeGateway;
import com.github.platform.core.standard.constant.UserChannelEnum;
import com.github.platform.core.standard.exception.InfrastructureException;
import com.github.platform.core.sys.domain.context.LoginContext;
import com.github.platform.core.sys.domain.gateway.ISysLoginGateway;
import com.github.platform.core.sys.domain.gateway.ISysUserGateway;
import com.github.platform.core.sys.domain.gateway.IThirdUserGateway;
import com.github.platform.core.sys.domain.model.user.ThirdUserEntity;
import com.github.platform.core.sys.domain.model.user.UserEntity;
import com.github.platform.core.sys.domain.service.SysUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 支付宝登录网关
 * @author: yxkong
 * @date: 2023/4/4 12:30 PM
 * @version: 1.0
 */
@Service("dingTalkLoginGateway")
public class DingTalkLoginGatewayImpl implements ISysLoginGateway {
    @Resource
    private IDingTalkNoticeGateway dingTalkNoticeGateway;
    @Resource
    private IThirdUserGateway thirdUserGateway;
    @Resource
    private ISysUserGateway userGateway;
    @Override
    public UserEntity login(LoginContext context)throws InfrastructureException {
        // 钉钉验证
        DingAccessUserDto userInfo = dingTalkNoticeGateway.getUserAccessUserInfo(context.getVerifySeq(),context.getTenantId());
        String openId = dingTalkNoticeGateway.getUserIdByMobile(userInfo.getMobile(),context.getTenantId());
        /**获取或添加用户*/
        SysUserService userService = new SysUserService(userGateway,thirdUserGateway);
        ThirdUserEntity thirdUser = ThirdUserEntity.builder()
                .loginName(userInfo.getMobile())
                .nickName(userInfo.getNick())
                .userName(userInfo.getNick())
                .channel(UserChannelEnum.thirdDing)
                .email(userInfo.getEmail())
                .mobile(userInfo.getMobile())
                .unionId(userInfo.getUnionId())
                .openId(openId)
                .build();
        //租户
        return userService.quietSysUerWithMobile(thirdUser);
    }
}
