package com.github.platform.core.sys.infra.service.impl;

import com.github.platform.core.auth.entity.LoginUserInfo;
import com.github.platform.core.auth.entity.TokenCacheEntity;
import com.github.platform.core.auth.gateway.ITokenCacheGateway;
import com.github.platform.core.auth.service.IAuthorizationService;
import com.github.platform.core.auth.util.LoginUserInfoUtil;
import com.github.platform.core.common.utils.CollectionUtil;
import com.github.platform.core.common.utils.JsonUtils;
import com.github.platform.core.sys.domain.constant.LoginWayEnum;
import com.github.platform.core.sys.domain.context.LoginContext;
import com.github.platform.core.sys.domain.gateway.ISysLoginGateway;
import com.github.platform.core.sys.domain.gateway.ISysUserConfigGateway;
import com.github.platform.core.sys.domain.gateway.ISysUserGateway;
import com.github.platform.core.sys.domain.model.user.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 授权实现
 * @author: yxkong
 * @date: 2024/4/26 21:38
 * @version: 1.0
 */
@Service
@Slf4j
public class AuthorizationServiceImpl implements IAuthorizationService {

    @Resource
    private ISysUserGateway sysUserGateway;
    @Resource
    private ITokenCacheGateway tokenCacheGateway;
    @Resource(name = "normalLoginGateway")
    private ISysLoginGateway sysLoginGateway;
    @Override
    public void bearer(String secretKey) {
        //授权以后需要通过LoginUserInfoUtil 放入本地线程
        UserEntity userEntity = sysUserGateway.findBySecretKey(secretKey);
        //有token的时候
        List<TokenCacheEntity> list = tokenCacheGateway.findByLoginName(userEntity.getTenantId(), userEntity.getLoginName());
        if (CollectionUtil.isNotEmpty(list)){
            String loginInfoStr = list.get(0).getLoginInfo();
            LoginUserInfoUtil.setLoginUserInfo(JsonUtils.fromJson(loginInfoStr, LoginUserInfo.class));
        } else {
            sysUserGateway.generatorToken(userEntity, null, LoginWayEnum.bearer);
        }
    }

    @Override
    public void basic(Integer tenantId,String user, String pwd) {
        //授权以后需要通过LoginUserInfoUtil 放入本地线程
        LoginContext context = LoginContext.builder().loginName(user).pwd(pwd).loginWay(LoginWayEnum.normal).tenantId(tenantId).build();
        UserEntity userEntity = sysLoginGateway.login(context);
        sysUserGateway.generatorToken(userEntity, null, LoginWayEnum.bearer);
    }
}
