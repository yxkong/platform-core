package com.github.platform.core.web.util;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.github.platform.core.common.utils.CollectionUtil;
import com.github.platform.core.standard.constant.OptLogConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * request具类
 * @author: yxkong
 * @date: 2022/4/26 7:35 PM
 * @version: 1.0
 */
@Slf4j
public class RequestHolder {
    public static final Integer payloadMaxLength = 2048;
    public static final String defaultPayload = null;
    /**默认掩码字段 TODO  后续改为动态配置*/
    private static final String defaultMask =  "******";
    /**
     * 获取 HttpServletRequest
     *
     * @return HttpServletRequest
     */
    public static HttpServletRequest getRequest() {
        return  ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }
    public static ContentCachingRequestWrapper getContentCachingRequestWrapper() {
        HttpServletRequest request = getRequest();
        return getContentCachingRequestWrapper(request).getRight();
    }

    /**
     * HttpServletResponse
     *
     * @return HttpServletResponse
     */
    public static HttpServletResponse getResponse() {
        return ((ServletWebRequest) RequestContextHolder.getRequestAttributes()).getResponse();
    }
    /**
     * 获取所有的header
     * @return
     */
    public static Map<String,String> getHeaders(){
        return getHeaders(getRequest());
    }


    /**
     * 获取ContentCachingRequestWrapper
     * @param request
     * @return
     */
    public static Pair<Boolean,ContentCachingRequestWrapper> getContentCachingRequestWrapper(HttpServletRequest request) {
        if ( request instanceof ContentCachingRequestWrapper) {
            return Pair.of(false,(ContentCachingRequestWrapper) request);
        } else {
           return Pair.of(true, new ContentCachingRequestWrapper(request));
        }
    }

    /**
     * 获取header
     * @param request
     * @return
     */
    public static Map<String,String> getHeaders(HttpServletRequest request){
        Map<String, String> headersMap = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = headerNames.nextElement();
            headersMap.put(key, request.getHeader(key));
        }
        return headersMap;
    }

    /**
     * 排除某些字段获取map
     * @param request
     * @param exclude 排除字段
     * @return
     */
    public static Map<String, String> getExcludeHeaders(HttpServletRequest request, Set<String> exclude) {
        if (CollectionUtil.isEmpty(exclude)) {
            return getHeaders(request);
        }
        Map<String, String> headersMap = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = headerNames.nextElement();
            if (!exclude.contains(key)){
                headersMap.put(key, request.getHeader(key));
            }
        }
        return headersMap;
    }
    /**
     * 获取包含某些的header
     * @param request
     * @param include 包含字段
     * @return
     */
    public static Map<String, String> getIncludeHeaders(HttpServletRequest request, Set<String> include) {
        if (CollectionUtil.isEmpty(include)) {
            return new HashMap<>();
        }
        Map<String, String> headersMap = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = headerNames.nextElement();
            if (include.contains(key)){
                headersMap.put(key, request.getHeader(key));
            }
        }
        return headersMap;
    }
    public static void updateResponse(ContentCachingResponseWrapper response) throws IOException {
//        用于获取ContentCachingResponseWrapper的实例。这个方法会检查响应是否已经被包装，如果没有包装则会创建一个新的包装器。如果已经包装了，它会返回已存在的包装器实例
//        ContentCachingResponseWrapper responseWrapper = WebUtils.getNativeResponse(response, ContentCachingResponseWrapper.class);
//        将缓存的响应内容复制回原始的HttpServletResponse中，从而实际上将内容发送给客户端
        if (Objects.nonNull(response)){
            Objects.requireNonNull(response).copyBodyToResponse();
        }
    }

    /**
     * 获取请求内容,只有在使用ContentCachingRequestWrapper增强的时候才能获取
     * @param request
     * @return
     */
    public static String getRequestBody(HttpServletRequest request) {
        ContentCachingRequestWrapper wrapper = WebUtils.getNativeRequest(request, ContentCachingRequestWrapper.class);
        return getRequestBody(wrapper);
    }
    /**
     * 获取requestBody内容
     * @param request
     * @return
     */
    public static String getRequestBody(ContentCachingRequestWrapper request) {
        if (Objects.isNull(request)){
            return null;
        }
        return getPayLoad(request.getContentAsByteArray(),request.getCharacterEncoding());
    }
    public static String getRequestBody(HttpServletRequest request,Set<String> exclude) {
        String payLoad = getRequestBody(request);
        return resultDeal(exclude,null, payLoad);
    }
    /**
     * 排除请求体中的数据
     * @param request
     * @param exclude
     * @return
     */
    public static String getRequestBody(ContentCachingRequestWrapper request,Set<String> exclude) {
        String payLoad = getRequestBody(request);
        return resultDeal(exclude,null, payLoad);
    }

    private static String getPayLoad(byte[] buf,String characterEncoding) {
        if (null != buf && buf.length > 0) {
            try {
                return new String(buf, characterEncoding);
            } catch (UnsupportedEncodingException ex) {
                log.warn("getPayLoad 异常.", ex);
            }
        }
        return defaultPayload;
    }

    /**
     * 获取响应体
     * 注意： 只有在使用ContentCachingResponseWrapper增强以后才能获取到
     * @param response
     * @return
     */
    public static String getResponseBody(HttpServletResponse response) {
        ContentCachingResponseWrapper wrapper = WebUtils.getNativeResponse(response, ContentCachingResponseWrapper.class);
        return getResponseBody(wrapper);
    }
    public static Pair<Boolean,ContentCachingResponseWrapper> getContentCachingResponseWrapper(HttpServletResponse response) {
        if ( response instanceof ContentCachingResponseWrapper) {
            return Pair.of(false,(ContentCachingResponseWrapper) response);
        } else {
            return Pair.of(true,new ContentCachingResponseWrapper(response));
        }
    }

    /**
     * 获取responseBody内容
     * @param response
     * @return
     */
    public static String getResponseBody(ContentCachingResponseWrapper response) {
        return getPayLoad(response.getContentAsByteArray(),response.getCharacterEncoding());
    }
    public static String getResponseBodyExclude(ContentCachingResponseWrapper response,Set<String> exclude) {
        String payLoad = getPayLoad(response.getContentAsByteArray(), response.getCharacterEncoding());
        return resultDeal(exclude,null, payLoad);
    }

    /**
     * 结果处理
     * 1，排除某些字段
     * 2，指定字段字段加掩码
     * @param exclude
     * @param json
     * @return
     */
    public static String resultDeal(Set<String> exclude,Set<String> mask, String json){
        if (Objects.isNull(json)){
            return null;
        }
        JSONObject parse = JSONObject.parseObject(json);
        excludeFields(parse,exclude);
        mask(parse,mask);
        return parse.toString();
    }

    /**
     * 排除字段
     * @param parse
     * @param exclude
     * @return
     */
    public static void excludeFields(JSONObject parse,Set<String> exclude) {
        try {
            if (CollectionUtil.isEmpty(exclude)){
                return;
            }
            if (log.isDebugEnabled()){
                log.debug("数据：{} 排除字段：{} ", parse,JSON.toJSONString(exclude));
            }

            exclude.forEach(s->{
                if(parse.containsKey(s)){
                    parse.remove(s);
                }
            });
        } catch (Exception e) {
            log.error("数据：{} 排除：{} 异常", parse,JSON.toJSONString(exclude));
        }
    }

    /**
     * 掩码处理
     * @param parse
     */
    public static void mask(JSONObject parse,Set<String> custom) {
        try {
            Set<String> masks = new HashSet<>( OptLogConstant.defaultMaskFields);
            if (CollectionUtil.isNotEmpty(custom)){
                masks.addAll(custom);
            }
            masks.forEach(s->{
                if(parse.containsKey(s)){
                    parse.put(s,defaultMask);
                }
            });
        } catch (Exception e) {
            log.error("数据：{} 掩码异常", parse);
        }
    }

    /**
     * HttpSession
     *
     * @return HttpSession
     */
    public static HttpSession getSession() {
        return getRequest().getSession();
    }

    /**
     * 获取header 值
     *
     * @param name
     * @return
     */
    public static String getHeaderVal(String name) {
        return getRequest().getHeader(name);
    }

    /**
     * Parameter 值
     *
     * @param name
     * @return
     */
    public static String getPara(String name) {
        return getRequest().getParameter(name);
    }
}
