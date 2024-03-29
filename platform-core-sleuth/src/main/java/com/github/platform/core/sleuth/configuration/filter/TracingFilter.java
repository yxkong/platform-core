package com.github.platform.core.sleuth.configuration.filter;

import brave.Span;
import brave.Tracing;
import com.alibaba.fastjson2.JSONObject;
import com.github.platform.core.common.constant.SpringBeanOrderConstant;
import com.github.platform.core.standard.entity.dto.ResultBean;
import com.github.platform.core.web.filter.PlatformOncePerRequestFilter;
import com.github.platform.core.web.util.RequestHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

/**
 * 采集输入输出信息到brave.Span
 * @author: yxkong
 * @date: 2021/7/27 3:16 下午
 * @version: 1.0
 */
@Slf4j
public class TracingFilter extends PlatformOncePerRequestFilter {

    @Override
    public int getOrder() {
        return SpringBeanOrderConstant.FILTER_SLEUTH;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Pair<Boolean, ContentCachingRequestWrapper> p1 = getRequestWrapper(request);
        ContentCachingRequestWrapper requestWrapper = p1.getRight();
        Pair<Boolean, ContentCachingResponseWrapper> p2 = getResponseWrapper(response);
        ContentCachingResponseWrapper responseWrapper = p2.getRight();
        try {
            String uri = request.getRequestURI();
            //属于跳过资源，直接放行
            if (SkipUrlSuffixEnum.contains(uri)){
                filterChain.doFilter(requestWrapper, responseWrapper);
                return;
            }
            before(requestWrapper);
            filterChain.doFilter(requestWrapper, responseWrapper);
            after(requestWrapper, responseWrapper);
        } finally {
            if (p2.getLeft()){
                responseWrapper.copyBodyToResponse();
            }
        }


    }

    /**
     * 执行前设置
     * @param requestWrapper
     */
    private void before(ContentCachingRequestWrapper requestWrapper) {
        try {
            if (Objects.isNull(getSpan())){
                if (log.isDebugEnabled()){
                    log.debug("未查询到span，不采集zipkin日志");
                }
                return;
            }
            Map<String, String> header = RequestHolder.getHeaders(requestWrapper);
            header.forEach((k,v) ->{
                if (!SkipHeaderEnum.contains(k)) {
                    putSpan( "request.headers." + k,v);
                }
            });
            String url = requestWrapper.getQueryString();
            if (url != null && url.length() > 1) {
                putSpan( "request.queryString", url);
            }
        } catch (Exception e) {
            //异常后不影响
        }

    }
    private Span getSpan(){
        Span span = null;
        try {
            span = Tracing.currentTracer().currentSpan();
        } catch (Exception e){
        }
        return span;
    }

    /**
     * 执行完设置
     * @param request
     * @param response
     */
    private void after(ContentCachingRequestWrapper request, ContentCachingResponseWrapper response) throws IOException{
        try {
            if (Objects.isNull(getSpan())){
                return;
            }
            String requestPayload = null;
            String responsePayload = null;
            try {
                requestPayload = RequestHolder.getRequestBody(request);
                responsePayload = RequestHolder.getResponseBody(response);
            } catch (Exception e) {
                logger.warn("getPayLoad异常.", e);
            } finally {
                response.copyBodyToResponse();
            }
            putSpan("request.body", requestPayload);

            String contentType = request.getContentType();
            if (Objects.nonNull(contentType)) {
                putSpan("response.contentType", contentType);
            }
            String characterEncoding = request.getCharacterEncoding();
            if (Objects.nonNull(characterEncoding)) {
                putSpan("response.characterEncoding", characterEncoding);
            }

            JSONObject jsonObject = JSONObject.parseObject(responsePayload);
            if (jsonObject.containsKey(ResultBean.STATUS)){
                putSpan("response.status", jsonObject.getString(ResultBean.STATUS));
            }
            if (jsonObject.containsKey(ResultBean.MESSAGE)){
                putSpan("response.message", jsonObject.getString(ResultBean.MESSAGE));
            }
            if (jsonObject.containsKey(ResultBean.TIMESTAMP)){
                putSpan("response.timestamp", jsonObject.getString(ResultBean.TIMESTAMP));
            }
            if (jsonObject.containsKey(ResultBean.DATA)){
                putSpan("response.data", jsonObject.getString(ResultBean.DATA));
            }
        } catch (Exception e) {
        }
    }
    private void putSpan( String key, String value) {
        Span span = getSpan();
        if ( Objects.nonNull(span) && Objects.nonNull(value)) {
            span.tag(key, value);
        }
    }
}