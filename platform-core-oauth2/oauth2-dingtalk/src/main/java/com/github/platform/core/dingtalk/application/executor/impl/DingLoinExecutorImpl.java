package com.github.platform.core.dingtalk.application.executor.impl;

import com.github.platform.core.dingtalk.application.executor.IDingLoginExecutor;
import com.github.platform.core.sys.application.executor.IAuthExecutor;
import com.github.platform.core.sys.domain.constant.LoginWayEnum;
import com.github.platform.core.sys.domain.constant.VerifyTypeEnum;
import com.github.platform.core.sys.domain.context.LoginContext;
import com.github.platform.core.sys.domain.dto.resp.LoginResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 钉钉登录
 * @author: yxkong
 * @date: 2024/4/18 21:16
 * @version: 1.0
 */
@Service
@Slf4j
public class DingLoinExecutorImpl implements IDingLoginExecutor {
    @Resource
    private IAuthExecutor authExecutor;
    @Override
    public LoginResult auth(String authCode,Integer tenantId) {
        LoginContext context = new LoginContext();
        context.setVerifySeq(authCode);
        context.setLoginWay(LoginWayEnum.thirdDingTalk);
        context.setVerifyType(VerifyTypeEnum.DEFAULT);
        context.setTenantId(tenantId);
        return authExecutor.login(context);
    }
}
