package com.github.platform.core.cache.infra.interceptor;

import com.github.platform.core.cache.infra.annotation.RepeatSubmit;
import com.github.platform.core.cache.infra.configuration.properties.CacheProperties;
import com.github.platform.core.cache.infra.constant.RepeatSubmitEnum;
import com.github.platform.core.cache.infra.service.ICacheService;
import com.github.platform.core.common.utils.JsonUtils;
import com.github.platform.core.common.utils.StringUtils;
import com.github.platform.core.standard.constant.HeaderConstant;
import com.github.platform.core.standard.constant.ResultStatusEnum;
import com.github.platform.core.standard.constant.SymbolConstant;
import com.github.platform.core.standard.util.ResultBeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Objects;

/**
 * 防止重复提交拦截器
 *
 * @author wangxiaozhou
 */
@Slf4j
public class RepeatSubmitInterceptor implements HandlerInterceptor {
    private ICacheService cacheService;
    private CacheProperties properties;

    public RepeatSubmitInterceptor(ICacheService cacheService, CacheProperties properties) {
        this.cacheService = cacheService;
        this.properties = properties;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            RepeatSubmit annotation = method.getAnnotation(RepeatSubmit.class);
            return Objects.isNull(annotation) || !isRepeat(request, response, annotation);
        }
        return true;
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            RepeatSubmit annotation = method.getAnnotation(RepeatSubmit.class);
            if (Objects.nonNull(annotation) && annotation.release()) {
                cacheService.del(cacheKey(request, annotation));
            }
        }
    }

    private boolean isRepeat(HttpServletRequest request, HttpServletResponse response, RepeatSubmit annotation) throws IOException {
        String key = cacheKey(request, annotation);
        Long expireTime = cacheService.getExpire(key);
        if (expireTime > 0) {
            log.warn("重复提交:{},剩余时间{}s", key, expireTime);
            response.addHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().println(JsonUtils.toJson(ResultBeanUtil.result(ResultStatusEnum.REPEAT)));
            return Boolean.TRUE;
        }
        cacheService.set(key, "1", annotation.interval());

        return Boolean.FALSE;
    }

    private String cacheKey(HttpServletRequest request, RepeatSubmit annotation) {
        String accessFlag;
        if (RepeatSubmitEnum.IP.equals(annotation.type())) {
            accessFlag = getIpAddress(request);
        } else {
            accessFlag = request.getHeader(HeaderConstant.TOKEN);
        }
        return new StringBuilder(properties.getRepeatSubmit()).append(accessFlag).append(SymbolConstant.colon).append(request.getRequestURI()).toString();
    }

    private  String getIpAddress(HttpServletRequest request) {

        String ip = request.getHeader(HeaderConstant.IP_HEADER_X_REQUESTED_FOR);
        if (StringUtils.isEmpty(ip) || HeaderConstant.IP_UNKNOWN.equalsIgnoreCase(ip)) {
            //这是一个 Squid 开发的字段，只有在通过了HTTP代理或者负载均衡服务器时才会添加该项,多个以逗号分隔
            ip = request.getHeader(HeaderConstant.IP_HEADER_X_FORWARDED_FOR);
        }
        if (StringUtils.isEmpty(ip) || HeaderConstant.IP_UNKNOWN.equalsIgnoreCase(ip)) {
            //apache 服务代理ip
            ip = request.getHeader(HeaderConstant.IP_HEADER_PROXY_CLIENT_IP);
        }
        if (StringUtils.isEmpty(ip) || HeaderConstant.IP_UNKNOWN.equalsIgnoreCase(ip)) {
            //weblogic 服务代理
            ip = request.getHeader(HeaderConstant.IP_HEADER_WL_PROXY_CLIENT_IP);
        }
        if (StringUtils.isEmpty(ip) || HeaderConstant.IP_UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader(HeaderConstant.IP_HEADER_HTTP_CLIENT_IP);
        }
        if (StringUtils.isEmpty(ip) || HeaderConstant.IP_UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader(HeaderConstant.IP_HEADER_HTTP_X_FORWARDED_FOR);
        }
        if (StringUtils.isEmpty(ip) || HeaderConstant.IP_UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if (ip.equals(HeaderConstant.IPV6_DEFAULT_LOCAL) || ip.equals(HeaderConstant.IPV4_DEFAULT_LOCAL)) {
                // 根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    log.error("获取localhost ip is error", e);
                }
                ip = inet.getHostAddress();
            }
        }

        if (ip != null && ip.contains(SymbolConstant.comma)) {
            String[] ips = ip.split(SymbolConstant.comma);
            for (int index = 0; index < ips.length; index++) {
                String strIp = ips[index];
                if (!(HeaderConstant.IP_UNKNOWN.equalsIgnoreCase(strIp))) {
                    ip = strIp;
                    break;
                }
            }
        }
        return ip;
    }

}
