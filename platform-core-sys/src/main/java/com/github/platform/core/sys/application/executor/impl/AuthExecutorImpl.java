package com.github.platform.core.sys.application.executor.impl;

import com.github.platform.core.auth.entity.LoginUserInfo;
import com.github.platform.core.auth.util.LoginUserInfoUtil;
import com.github.platform.core.common.service.BaseExecutor;
import com.github.platform.core.standard.util.ExceptionUtil;
import com.github.platform.core.standard.constant.ResultStatusEnum;
import com.github.platform.core.standard.exception.CommonException;
import com.github.platform.core.standard.util.LocalDateTimeUtil;
import com.github.platform.core.sys.application.constant.SysAppResultEnum;
import com.github.platform.core.sys.application.dto.VerifyCodeResult;
import com.github.platform.core.sys.application.executor.IAuthExecutor;
import com.github.platform.core.sys.application.factory.StrategyFactory;
import com.github.platform.core.sys.domain.constant.LoginWayEnum;
import com.github.platform.core.sys.domain.constant.VerifyTypeEnum;
import com.github.platform.core.sys.domain.context.LoginContext;
import com.github.platform.core.sys.domain.context.ModifyPwdContext;
import com.github.platform.core.sys.domain.dto.resp.LoginResult;
import com.github.platform.core.sys.domain.gateway.ISysLoginGateway;
import com.github.platform.core.sys.domain.gateway.ISysUserGateway;
import com.github.platform.core.sys.domain.model.sms.VerifyEntity;
import com.github.platform.core.sys.domain.model.user.UserEntity;
import com.github.platform.core.sys.domain.service.VerifyStrategy;
import com.github.platform.core.sys.infra.convert.SysUserInfraConvert;
import com.github.platform.core.sys.infra.event.LoginEvent;
import com.github.platform.core.sys.infra.event.LoginOutEvent;
import com.github.platform.core.sys.infra.util.UserAgentUtil;
import com.github.platform.core.web.util.WebUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 登录执行器
 *
 * @author: yxkong
 * @date: 2022/4/26 2:35 PM
 * @version: 1.0
 */
@Service
@Slf4j
public class AuthExecutorImpl extends BaseExecutor implements IAuthExecutor {
    @Autowired
    private Map<String, VerifyStrategy> verifyStrategyMap;
    @Resource
    private ISysUserGateway userGateway;
    @Autowired
    private Map<String, ISysLoginGateway> loginGatewayMap;
    @Resource
    private SysUserInfraConvert userConvert;
    /**
     * 获取短信验证码
     *
     * @param context
     * @return
     */
    @Override
    public VerifyCodeResult getVerifyCode(LoginContext context) {
        VerifyStrategy strategy = StrategyFactory.getOrDefault(verifyStrategyMap, VerifyTypeEnum.CAPTCHA);
        VerifyEntity verifyEntity = userConvert.verify(context);
        strategy.verify(verifyEntity);
        UserEntity userEntity = UserEntity.builder().loginName(context.getLoginName()).mobile(context.getMobile()).build();
        strategy = StrategyFactory.getOrDefault(verifyStrategyMap, VerifyTypeEnum.SMS);
        return strategy.getCode(userEntity);
    }

    @Override
    public LoginResult login(LoginContext context) {
        LoginWayEnum loginWay = context.getLoginWay();
        VerifyEntity verifyEntity = userConvert.verify(context);
        VerifyStrategy strategy = StrategyFactory.getOrDefault(verifyStrategyMap, context.getVerifyType());
        LoginUserInfo loginUserInfo = null;
        try {
            strategy.verify(verifyEntity);
            ISysLoginGateway loginGateway = loginGatewayMap.get(loginWay.getBeanName());
            UserEntity userEntity = loginGateway.login(context);
            String token = userGateway.generatorToken(userEntity,userEntity.getDefaultRoles(), loginWay);
            loginUserInfo = LoginUserInfoUtil.getLoginUserInfo();
            loginUserInfo.setMessage("登录成功！");
            return new LoginResult(token,userEntity);
        }catch (Exception e) {
            if (e instanceof CommonException){
                CommonException ce = (CommonException)e;
                loginUserInfo = initLoginInfo(context.getLoginName(),context.getLoginWay(),ce.getStatus(),ce.getMessage());
                throw e;
            } else {
                loginUserInfo = initLoginInfo(context.getLoginName(),context.getLoginWay(), ResultStatusEnum.ERROR.getStatus(), ExceptionUtil.getMessage(e));
                exception(SysAppResultEnum.LOGIN_ERROR,e);
            }
        }finally {
            LoginUserInfoUtil.setLoginUserInfo(loginUserInfo);
            strategy.finish(verifyEntity);
            sendEvent(new LoginEvent(loginUserInfo));
        }
        return null;
    }
    private LoginUserInfo initLoginInfo(String loginName, LoginWayEnum loginWay,String status,String message){
        Pair<String, String> osAndBrowser = UserAgentUtil.getOsAndBrowser();
        return LoginUserInfo.builder()
                .loginName(loginName)
                .loginWay(loginWay.getType())
                .loginTime(LocalDateTimeUtil.dateTimeDefault())
                .requestIp(WebUtil.getIpAddress())
                .browser(osAndBrowser.getValue())
                .os(osAndBrowser.getKey())
                .status(status)
                .message(message)
                .build();
    }
    @Override
    public void modifyPwd(ModifyPwdContext context) {
        String loginName = LoginUserInfoUtil.getLoginName();
        UserEntity userEntity = userGateway.baseAccountCheck(loginName, context.getOldPwd());
        context.setId(userEntity.getId());
        userGateway.modifyPwd(context);
    }
    @Override
    public Boolean logout() {
        sendEvent(new LoginOutEvent(LoginUserInfoUtil.getLoginUserInfo()));
        userGateway.logout();
        return true;
    }
}
