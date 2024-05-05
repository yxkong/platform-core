package com.github.platform.core.auth.aspect;

import com.github.platform.core.auth.annotation.NoLogin;
import com.github.platform.core.auth.annotation.RequiredLogin;
import com.github.platform.core.auth.annotation.RequiredRoles;
import com.github.platform.core.auth.configuration.properties.AuthProperties;
import com.github.platform.core.auth.constants.AuthTypeEnum;
import com.github.platform.core.auth.entity.LoginUserInfo;
import com.github.platform.core.auth.service.IAuthorizationService;
import com.github.platform.core.auth.service.ITokenService;
import com.github.platform.core.auth.util.AuthUtil;
import com.github.platform.core.auth.util.LoginUserInfoUtil;
import com.github.platform.core.common.constant.SpringBeanOrderConstant;
import com.github.platform.core.common.utils.JsonUtils;
import com.github.platform.core.common.utils.StringUtils;
import com.github.platform.core.standard.constant.HeaderConstant;
import com.github.platform.core.standard.constant.SymbolConstant;
import com.github.platform.core.standard.exception.NoAuthForDataOptException;
import com.github.platform.core.standard.exception.NoLoginException;
import com.github.platform.core.standard.util.Base64;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @author wangxiaozhou
 */
@Slf4j
@Aspect
@Component
@ConditionalOnWebApplication(type = SERVLET)
@Order(SpringBeanOrderConstant.AUTHORIZE_ASPECT)
public class AuthorizeAspect {
    @Autowired
    private ITokenService tokenService;

    @Autowired(required = false)
    private IAuthorizationService authorizationService;
    @Resource
    private AuthProperties authProperties;
    @Resource
    private HttpServletRequest httpRequest;
    /**
     * 构建
     */
    public AuthorizeAspect() {
    }

    /**
     * 定义AOP签名 (切入所有使用鉴权注解的方法)
     */
    public static final String POINTCUT_SIGN = " @annotation(com.github.platform.core.auth.annotation.RequiredLogin) || "
            +" @annotation(com.github.platform.core.auth.annotation.NoLogin) ||"
            + "@annotation(com.github.platform.core.auth.annotation.RequiredPermissions) || "
            + "@annotation(com.github.platform.core.auth.annotation.RequiredRoles)";

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
    @Around("execution(* *..controller..*.*(..))")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        // 注解鉴权
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        try{
            authHandler();
            checkMethodAnnotation(signature.getMethod());
            // 执行原有逻辑
            return joinPoint.proceed(joinPoint.getArgs());
        }catch (Exception e){
            throw e;
        }finally {
            MDC.clear();
            LoginUserInfoUtil.clearContext();
        }
    }

    /**
     * 授权处理
     */
    private void authHandler(){
        String authorization = getAuthorization();
        if (StringUtils.isNotEmpty(authorization)){
            String loginStr = null;
            //密钥认证
            if (authorization.startsWith(HeaderConstant.AUTH_BEARER)) {
                // 令牌认证，直接使用
                String secretKey = authorization.substring(HeaderConstant.AUTH_BEARER.length()).trim();
                loginStr = authorizationService.bearer(secretKey);
            } else if (authorization.startsWith(HeaderConstant.AUTH_BASIC)){
                // 基本认证，用户名 密码  base64加密
                String basic = authorization.substring(HeaderConstant.AUTH_BASIC.length()).trim();
                // base64解码
                byte[] decode = Base64.decode(basic);
                String loginName = "";
                String pwd = "";
                loginStr = authorizationService.basic(loginName,pwd);
            }
        } else {
            getLoginUserInfo();
        }
    }
    private void getLoginUserInfo(){
        LoginUserInfo loginInfo = new LoginUserInfo();
        /**  将loginToken 放入到本地线程里  **/
        String loginStr = getLoginInfoString();
        String token = getTokenKey();
        LoginUserInfoUtil.clearContext();
        if (StringUtils.isBlank(loginStr) && Objects.nonNull(token) && !HeaderConstant.DEFAULT_TOKEN.equals(token)) {
            loginStr = tokenService.getLoginInfoStr(token);
            if (log.isTraceEnabled()){
                log.trace("请求的,uri:{},loginInfoStr:{}", httpRequest.getRequestURI(),loginStr);
            }
        }
        if (StringUtils.isEmpty(loginStr)){
            //无需登陆请求
            loginInfo.setToken(token);
        } else {
            loginInfo = JsonUtils.fromJson(loginStr, LoginUserInfo.class);
        }
        MDC.put(HeaderConstant.TRACE_ID,getTraceId());
        LoginUserInfoUtil.setLoginUserInfo(loginInfo);
        if (log.isTraceEnabled()){
            log.trace(" 用户登陆信息为:{}", loginStr);
        }
    }
    private String getTraceId(){
        String traceId = httpRequest.getHeader(HeaderConstant.TRACE_ID);

        if (StringUtils.isNotEmpty(traceId)){
            return traceId;
        }
        return StringUtils.generateTraceId();
    }

    private String getTokenKey() {
        return httpRequest.getHeader(HeaderConstant.TOKEN);
    }

    /**
     * 特殊认证处理
     * @return
     */
    private String getAuthorization() {
        return httpRequest.getHeader(HeaderConstant.AUTHORIZATION);
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
    /**
     * 对一个Method对象进行注解检查
     */
    private  boolean checkMethodAnnotation(Method method) {
        AuthProperties.Sys sys = authProperties.getSys();
        String path = httpRequest.getRequestURI().toString();
        if (StringUtils.isNotEmpty(path) && path.startsWith(SymbolConstant.divide)){
            path = path.substring(1);
        }
        NoLogin noLogin = method.getAnnotation(NoLogin.class);
        if (Objects.nonNull(noLogin)){
            return true;
        }
        // 校验 @RequiredLogin 注解
        RequiredLogin requiredLogin = method.getAnnotation(RequiredLogin.class);
        if (Objects.nonNull(requiredLogin) ) {
            if(AuthUtil.checkLogin()){
                return true;
            } else {
                throw new NoLoginException();
            }
        }
        RequiredRoles requiresRoles = method.getAnnotation(RequiredRoles.class);
        if (Objects.nonNull(requiresRoles)) {
            if (AuthUtil.checkRole(requiresRoles)){
                return true;
            }else {
                throw new NoAuthForDataOptException();
            }
        }
        LoginUserInfo userInfo = LoginUserInfoUtil.getLoginUserInfo();
        userInfo.getPerms().addAll(sys.getDefaultPerms());
        //兜底校验
        if (!AuthUtil.isSuperAdmin() && !AuthUtil.hasPerms(userInfo.getPerms(), path)) {
            log.info("用户：{} 权限验证失败,用户已有权限:{},需要权限:{}", userInfo.getLoginName(), userInfo.getPerms(), path);
            throw new NoAuthForDataOptException();
        }
        return true;
    }
}
