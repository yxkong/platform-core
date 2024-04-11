package com.github.platform.core.auth.aspect;

import com.github.platform.core.auth.annotation.NoLogin;
import com.github.platform.core.auth.annotation.RequiredLogin;
import com.github.platform.core.auth.util.LoginInfoUtil;
import com.github.platform.core.common.constant.SpringBeanOrderConstant;
import com.github.platform.core.common.utils.StringUtils;
import com.github.platform.core.standard.exception.NoAuthForDataOptException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

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
        //校验权限
        checkMethodAnnotation(signature.getMethod());
        // 执行原有逻辑
        return joinPoint.proceed();
    }
    /**
     * 对一个Method对象进行注解检查
     */
    public boolean checkMethodAnnotation(Method method) {
        NoLogin noLogin = method.getAnnotation(NoLogin.class);
        if (Objects.nonNull(noLogin)){
            return true;
        }
        // 校验 @RequiredLogin 注解
//        RequiredLogin requiredLogin = method.getAnnotation(RequiredLogin.class);
//        if (Objects.nonNull(requiredLogin) && LoginInfoUtil.isLogin()) {
//            return true;
//        }
        if (LoginInfoUtil.isLogin()){
            return true;
        }
        throw new NoAuthForDataOptException("用户没有权限");
    }
}
