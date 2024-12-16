package com.github.platform.core.sys.infra.listener;

import com.github.platform.core.auth.entity.LoginUserInfo;
import com.github.platform.core.common.utils.JsonUtils;
import com.github.platform.core.persistence.mapper.sys.LoginLogMapper;
import com.github.platform.core.sys.domain.common.entity.SysLoginLogBase;
import com.github.platform.core.sys.infra.convert.SysLoginLogInfraConvert;
import com.github.platform.core.sys.infra.event.LoginEvent;
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
public class LoginEventListener implements ApplicationListener<LoginEvent> {
    @Resource
    private SysLoginLogInfraConvert convert;
    @Resource
    private LoginLogMapper loginLogMapper;
    @Override
    public void onApplicationEvent(LoginEvent event) {
        LoginUserInfo loginUserInfo = event.getLoginUserInfo();
        if (log.isDebugEnabled()){
            log.debug("login log:{}", JsonUtils.toJson(loginUserInfo));
        }
        SysLoginLogBase sysLoginLogBase = convert.toSysLoginLogBase(loginUserInfo);
        try {
            sysLoginLogBase.setId(null);
            loginLogMapper.insert(sysLoginLogBase);
        } catch (Exception e) {
            log.error("保存{}登录日志失败：",loginUserInfo.getLoginName(),e);
        }
    }
}
