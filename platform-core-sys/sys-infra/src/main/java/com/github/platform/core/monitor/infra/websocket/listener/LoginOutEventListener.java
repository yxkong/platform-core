package com.github.platform.core.monitor.infra.websocket.listener;

import com.github.platform.core.auth.entity.LoginUserInfo;
import com.github.platform.core.monitor.domain.ws.InMessage;
import com.github.platform.core.monitor.domain.ws.OutMessage;
import com.github.platform.core.monitor.infra.websocket.service.IWsMessageService;
import com.github.platform.core.sys.infra.event.LoginOutEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 登录日志监听
 * @author: yxkong
 * @date: 2023/5/30 6:45 PM
 * @version: 1.0
 */
@Component
@Slf4j
public class LoginOutEventListener implements ApplicationListener<LoginOutEvent> {
    @Resource(name = "wsLogoutWsMessageService")
    private IWsMessageService messageService;

    @Override
    public void onApplicationEvent(LoginOutEvent event) {
        LoginUserInfo loginInfo = event.getLoginUserInfo();
        if (log.isDebugEnabled()){
            log.debug("loginOut token:{}", loginInfo.getToken());
        }
        InMessage wsMessage = InMessage.builder()
                .fromUser(loginInfo.getLoginName())
                .toUser(loginInfo.getLoginName())
                .loginInfo(loginInfo)
                .build();
        messageService.executor(wsMessage,true);

    }
}
