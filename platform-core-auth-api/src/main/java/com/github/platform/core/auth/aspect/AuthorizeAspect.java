package com.github.platform.core.auth.aspect;

import com.github.platform.core.auth.annotation.NoLogin;
import com.github.platform.core.auth.annotation.RequiredLogin;
import com.github.platform.core.auth.service.ITokenService;
import com.github.platform.core.auth.util.LoginInfoUtil;
import com.github.platform.core.cache.domain.constant.CacheConstant;
import com.github.platform.core.common.constant.SpringBeanOrderConstant;
import com.github.platform.core.common.utils.JsonUtils;
import com.github.platform.core.common.utils.StringUtils;
import com.github.platform.core.standard.constant.HeaderConstant;
import com.github.platform.core.standard.entity.common.LoginInfo;
import com.github.platform.core.standard.exception.NoAuthForDataOptException;
import com.github.platform.core.standard.exception.NoLoginException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.MDC;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Objects;

import static org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type.SERVLET;

/**
 * 基于 Spring Aop 的注解鉴权
 *
 * @author yxkong
 */
@Slf4j
@Aspect
@Component
@ConditionalOnWebApplication(type = SERVLET)
@Order(SpringBeanOrderConstant.AUTHORIZE_ASPECT)
public class AuthorizeAspect {
    @Resource
    private HttpServletRequest httpRequest;
    @Resource(name = CacheConstant.apiTokenService)
    private ITokenService tokenService;
    /**
     * 构建
     */
    public AuthorizeAspect() {
    }

    /**
     * 定义AOP签名 (切入所有使用鉴权注解的方法)
     */
    public static final String POINTCUT_SIGN = " @annotation(com.github.platform.core.auth.annotation.RequiredLogin) || "
            + "@annotation(com.github.platform.core.auth.annotation.NoLogin)  ";

    /**
     * 声明AOP签名
     */
    @Pointcut(POINTCUT_SIGN)
    public void pointcut() {
    }

    /**
     * 环绕切入
     *
     * @param joinPoint 切面对象
     * @return 底层方法执行后的返回值
     * @throws Throwable 底层方法抛出的异常
     */
//    @Around("pointcut()")
    @Around("execution(* *..controller..*.*(..))")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        // 注解鉴权
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        try{
            LoginInfoUtil.clearContext();
            //获取用户信息
            authHandler();
            //鉴权
            checkMethodAnnotation(signature.getMethod());
            // 执行原有逻辑
            return joinPoint.proceed();
        }catch (Exception e){
            throw e;
        }finally {
//            MDC.clear();
            LoginInfoUtil.clearContext();
        }

    }

    private void authHandler() {
        LoginInfo loginInfo = null;
        /**  将loginToken 放入到本地线程里  **/
        String loginStr = getLoginInfoString();
        String token = getToken();
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
//        MDC.put(HeaderConstant.TRACE_ID,getTraceId());
        LoginInfoUtil.setLoginInfo(loginInfo);
        if (log.isTraceEnabled()){
            log.trace("用户登陆信息为:{}", JsonUtils.toJson(loginInfo));
        }

    }

    /**
     * 对一个Method对象进行注解检查,
     * api 项目，默认必须登录，不需要登录的接口加NoLogin即可
     */
    public boolean checkMethodAnnotation(Method method) {
        NoLogin noLogin = method.getAnnotation(NoLogin.class);
        if (Objects.nonNull(noLogin)){
            return true;
        }
        if (LoginInfoUtil.isLogin()){
            return true;
        }
         throw new NoLoginException();
    }
    private String getToken() {
        return httpRequest.getHeader(HeaderConstant.TOKEN);
    }
    private String getTraceId(){
        String traceId = httpRequest.getHeader(HeaderConstant.TRACE_ID);

        if (StringUtils.isNotEmpty(traceId)){
            return traceId;
        }
        return StringUtils.generateTraceId();
    }

    /**
     * 获取登录的用户信息（网关已经放入header）
     *
     * @return String
     */
    private String getLoginInfoString() {
        String loginStr = httpRequest.getHeader(HeaderConstant.LOGIN_INFO);
        if (StringUtils.isNotBlank(loginStr)) {
            if (log.isTraceEnabled()){
                log.trace("通过网关请求的,uri:{},loginInfoStr:{}", httpRequest.getRequestURI(),loginStr);
            }
            return new String(Base64Utils.decodeUrlSafe(loginStr.getBytes()));
        }
        // 记录没有从网关请求的ip和接口
        String token = httpRequest.getHeader(HeaderConstant.TOKEN);
        if (log.isTraceEnabled()){
            log.trace("没有从网关请求(登陆接口)的,uri:{},token:{}", httpRequest.getRequestURI(), token);
        }
        return null;
    }
}
