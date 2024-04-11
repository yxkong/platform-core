package com.github.platform.core.auth.aspect;

import com.github.platform.core.auth.constants.AuthTypeEnum;
import com.github.platform.core.auth.entity.LoginUserInfo;
import com.github.platform.core.auth.service.ITokenService;
import com.github.platform.core.auth.util.LoginUserInfoUtil;
import com.github.platform.core.common.constant.SpringBeanOrderConstant;
import com.github.platform.core.common.utils.JsonUtils;
import com.github.platform.core.common.utils.StringUtils;
import com.github.platform.core.standard.constant.HeaderConstant;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * 登录信息转换
 */
@Aspect
@Component
@Order(SpringBeanOrderConstant.LOGIN_INFO_ASPECT)
@ConditionalOnWebApplication(type = Type.SERVLET)
@Slf4j
public class LoginInfoConvertAspect {
    @Resource
    private HttpServletRequest httpRequest;
    @Autowired
    private ITokenService tokenService;


    @Around("execution(* *..controller..*.*(..))")
    public Object around(ProceedingJoinPoint jp) throws Throwable {
        String methodName = jp.getSignature().getName();
        String className = jp.getTarget().getClass().getName();
        LoginUserInfo loginInfo = null;
        try {
            /**  将loginToken 放入到本地线程里  **/
            String loginStr = getLoginInfoString();
            String token = getToken();
            LoginUserInfoUtil.clearContext();
            if (StringUtils.isBlank(loginStr) && Objects.nonNull(token) && !HeaderConstant.DEFAULT_TOKEN.equals(token)) {
                loginStr = tokenService.getLoginInfoStr(AuthTypeEnum.SYS,token);
                if (log.isTraceEnabled()){
                    log.trace("请求的,uri:{},loginInfoStr:{}", httpRequest.getRequestURI(),loginStr);
                }
            }
            if (StringUtils.isEmpty(loginStr)){
                //无需登陆请求
                loginInfo = new LoginUserInfo();
                loginInfo.setToken(getToken());
            } else {
                loginInfo = JsonUtils.fromJson(loginStr, LoginUserInfo.class);
            }
            MDC.put(HeaderConstant.TRACE_ID,getTraceId());
            LoginUserInfoUtil.setLoginUserInfo(loginInfo);
            if (log.isTraceEnabled()){
                log.trace("className:{} ,methodName:{} 用户登陆信息为:{}",className,methodName, loginStr);
            }
            return jp.proceed(jp.getArgs());
        } catch (Exception e) {
            throw e;
        } finally {
            MDC.clear();
            LoginUserInfoUtil.clearContext();
        }
    }
    private String getTraceId(){
        String traceId = httpRequest.getHeader(HeaderConstant.TRACE_ID);

        if (StringUtils.isNotEmpty(traceId)){
            return traceId;
        }
        return StringUtils.generateTraceId();
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

        if (StringUtils.isNotBlank(loginStr)) {
            loginStr = new String(Base64Utils.decodeUrlSafe(loginStr.getBytes()));
            if (log.isTraceEnabled()){
                log.trace("通过网关过来请求的,uri:{},loginInfoStr:{}", httpRequest.getRequestURI(),loginStr);
            }
            return loginStr;
        }
        // 记录没有从网关请求的ip和接口
        String token = httpRequest.getHeader(HeaderConstant.TOKEN);
        if (log.isTraceEnabled()){
            log.trace("没有从网关请求(登陆接口)的,uri:{},token:{}", httpRequest.getRequestURI(), token);
        }
        return null;
    }
}
