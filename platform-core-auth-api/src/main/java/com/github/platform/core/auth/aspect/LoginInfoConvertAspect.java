package com.github.platform.core.auth.aspect;

import com.github.platform.core.auth.service.ITokenService;
import com.github.platform.core.auth.util.LoginInfoUtil;
import com.github.platform.core.common.constant.SpringBeanOrderConstant;
import com.github.platform.core.common.utils.JsonUtils;
import com.github.platform.core.common.utils.StringUtils;
import com.github.platform.core.standard.constant.HeaderConstant;
import com.github.platform.core.standard.entity.common.LoginInfo;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

import static org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type.SERVLET;

/**
 * 集成到了api中，必须有servlet才会实例化
 */
@Aspect
@Component
@Order(SpringBeanOrderConstant.LOGIN_INFO_ASPECT)
//@ConditionalOnBean(ITokenService.class)
@ConditionalOnWebApplication(type = SERVLET)
@Slf4j
public class LoginInfoConvertAspect {

    @Resource
    private HttpServletRequest httpRequest;
    @Resource
    private ITokenService tokenService;

    @Around("execution(* *..controller..*.*(..))")
    public Object around(ProceedingJoinPoint jp) throws Throwable {
        String methodName = jp.getSignature().getName();
        String className = jp.getTarget().getClass().getName();
        LoginInfo loginInfo = null;
        try {
            /**  将loginToken 放入到本地线程里  **/
            String loginStr = getLoginInfoString();
            String token = getToken();
            LoginInfoUtil.clearContext();
            if (StringUtils.isBlank(loginStr) && Objects.nonNull(token) && !HeaderConstant.DEFAULT_TOKEN.equals(token)) {
                loginStr = tokenService.getLoginInfoStr(token);
            }
            if (StringUtils.isEmpty(loginStr)){
                //无需登陆请求
                loginInfo = new LoginInfo();
                loginInfo.setToken(getToken());
            } else {
                loginInfo = JsonUtils.fromJson(loginStr, LoginInfo.class);
            }
            LoginInfoUtil.setLoginInfo(loginInfo);
            if (log.isTraceEnabled()){
                log.trace("用户登陆信息为:{}", JsonUtils.toJson(loginInfo));
            }
            return jp.proceed(jp.getArgs());
        } catch (Exception e) {
            log.error("请求类:{},method={},loginInfo={}", className, methodName, JsonUtils.toJson(loginInfo), e);
            throw e;
        } finally {
            LoginInfoUtil.clearContext();
        }
    }

    private String getToken() {
        return httpRequest.getHeader(HeaderConstant.TOKEN);
    }

    /**
     * 获取登录的用户信息（网关已经放入header）
     *
     * @return String
     */
    private String getLoginInfoString() {
        String loginStr = httpRequest.getHeader(HeaderConstant.LOGIN_INFO);
        if (log.isDebugEnabled()){
            log.debug("请求的,uri:{},loginInfoStr:{}", httpRequest.getRequestURI(),loginStr);
        }
        if (StringUtils.isNotBlank(loginStr)) {
            return new String(Base64Utils.decodeUrlSafe(loginStr.getBytes()));
        }
        // 记录没有从网关请求的ip和接口
        String token = httpRequest.getHeader(HeaderConstant.TOKEN);
        if (log.isDebugEnabled()){
            log.debug("没有从网关请求(登陆接口)的,uri:{},token:{}", httpRequest.getRequestURI(), token);
        }
        return null;
    }
}
