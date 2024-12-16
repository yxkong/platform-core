package com.github.platform.core.sys.infra.event;

import com.github.platform.core.auth.entity.LoginUserInfo;
import org.springframework.context.ApplicationEvent;

/**
 * 登录事件
 * @author: yxkong
 * @date: 2023/5/30 6:31 PM
 * @version: 1.0
 */
public class LoginEvent extends ApplicationEvent {

    private LoginUserInfo loginInfo;
    public LoginEvent(LoginUserInfo loginInfo) {
        super(loginInfo);
        this.loginInfo = loginInfo;
    }
    public LoginUserInfo getLoginUserInfo() {
        return loginInfo;
    }

}
