package com.github.platform.core.monitor.infra.aspect;

import com.github.platform.core.auth.util.LoginUserInfoUtil;
import com.github.platform.core.common.utils.JsonUtils;
import com.github.platform.core.log.domain.entity.OptLogEntity;
import com.github.platform.core.log.infra.annotation.OptLog;
import com.github.platform.core.log.infra.configuration.properties.OptLogProperties;
import com.github.platform.core.log.infra.event.OptLogEvent;
import com.github.platform.core.standard.constant.OptLogConstant;
import com.github.platform.core.standard.util.LocalDateTimeUtil;
import com.github.platform.core.web.util.RequestHolder;
import com.github.platform.core.web.util.WebUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.annotation.Resource;
import java.util.*;

/**
 * 操作日志切面
 *
 * @author: yxkong
 * @date: 2023/5/2 12:21 下午
 * @version: 1.0
 */
@Aspect
@Component
@Slf4j
public class OptLogAspect {
    @Resource
    private OptLogProperties logProperties;
    @Resource
    private ApplicationContext applicationContext;

    @Pointcut("@annotation(com.github.platform.core.log.infra.annotation.OptLog)")
    public void pointCut() {
    }
    @Around("pointCut()")
    public Object around(ProceedingJoinPoint jp) throws Throwable {
        MethodSignature signature = (MethodSignature) jp.getSignature();
        OptLog optLog = AnnotationUtils.findAnnotation(signature.getMethod(), OptLog.class);
        if (optLog == null) {
            return jp.proceed();
        }
        Throwable ex = null;
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        Object rst = null;
        try {
            // 执行原有逻辑
            rst = jp.proceed();
            stopWatch.stop();
            return rst;
        } catch (Throwable e) {
            throw e;
        } finally {
            try {
                OptLogEvent event = getOptLogEvent(optLog, stopWatch,rst,ex);
                applicationContext.publishEvent(event);
            } catch (Exception e) {
            }
        }
    }

    private OptLogEvent getOptLogEvent(OptLog optLog,StopWatch stopWatch,Object rst,Throwable ex) {
        ContentCachingRequestWrapper request = RequestHolder.getContentCachingRequestWrapper();
        Map<String, String> headers = RequestHolder.getIncludeHeaders(request, OptLogConstant.includeHeader);
        headers.put("requestIp",WebUtil.getIpAddress());
        Set<String> exclude = new HashSet<>(Arrays.asList(optLog.excludeParamNames()));
        Set<String> mask = new HashSet<>(Arrays.asList(optLog.maskParamNames()));
        OptLogEntity entity = OptLogEntity.builder()
                .module(optLog.module())
                .title(optLog.title())
                .url(request.getRequestURI())
                .method(request.getMethod())
                .requestIp(WebUtil.getIpHost(request))
                .loginName(LoginUserInfoUtil.getLoginName())
                .tenantId(LoginUserInfoUtil.getTenantId())
                .headersMap(headers)
                .requestBody(RequestHolder.getRequestBody(request,exclude))
                .responseBody(RequestHolder.resultDeal(exclude,mask, JsonUtils.toJson(rst)))
                .executeTime(stopWatch.getTotalTimeMillis())
                .createTime(LocalDateTimeUtil.dateTime())
                .persistent(optLog.persistent())
                .optType(optLog.optType())
                .build();

        if (Objects.nonNull(ex)){
            entity.setException(ex.getMessage());
        }
        return new OptLogEvent(entity);
    }


}
