package com.github.platform.core.sys.application.executor;

import com.github.platform.core.sys.domain.dto.VerifyCodeResult;
import com.github.platform.core.sys.domain.context.LoginContext;
import com.github.platform.core.sys.domain.context.ModifyPwdContext;
import com.github.platform.core.sys.domain.dto.resp.LoginResult;

/**
 * 登录执行器
 * @author: yxkong
 * @date: 2023/12/27 11:00
 * @version: 1.0
 */
public interface IAuthExecutor {
    /**
     * 获取验证码
     * @param context
     * @return
     */
    VerifyCodeResult getVerifyCode(LoginContext context);

    /**
     * 登录
     * @param context
     * @return
     */
    LoginResult login(LoginContext context);

    /**
     * 修改密码
     * @param context
     */
    void modifyPwd(ModifyPwdContext context);

    /**
     * 退出
     * @return
     */
    Boolean logout();
}
