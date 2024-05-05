package com.github.platform.core.dingtalk.application.executor;

import com.github.platform.core.sys.domain.dto.resp.LoginResult;

/**
 * 钉钉登录执行器
 * @author: yxkong
 * @date: 2024/4/18 21:14
 * @version: 1.0
 */
public interface IDingLoginExecutor {
    /**
     * 验证
     * @param authCode
     * @return
     */
    LoginResult auth(String authCode,Integer tenantId);
}
