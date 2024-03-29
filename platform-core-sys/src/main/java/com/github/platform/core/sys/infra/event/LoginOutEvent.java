package com.github.platform.core.sys.infra.event;

import com.github.platform.core.auth.entity.LoginUserInfo;
import com.github.platform.core.sys.domain.model.event.LoginEntity;
import org.springframework.context.ApplicationEvent;

/**
 * 退出事件
 * @author: yxkong
 * @date: 2023/6/8 6:53 PM
 * @version: 1.0
 */

public class LoginOutEvent extends ApplicationEvent {
    private LoginUserInfo loginInfo;
    public LoginOutEvent(LoginUserInfo loginInfo) {
        super(loginInfo);
        this.loginInfo = loginInfo;
    }

    public LoginUserInfo getLoginUserInfo() {
        return loginInfo;
    }
}
