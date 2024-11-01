package com.github.platform.core.web.filter;

import com.github.platform.core.web.util.RequestHolder;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.core.Ordered;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 使用方式
 * 1，
 * @Component 添加到类上
 * 2，
 *  @Bean
 *     @ConditionalOnProperty(name = PropertyConstant.CON_HTTP_TRACE_ENABLED, havingValue = "true",matchIfMissing = false)
 *     public FilterRegistrationBean<HttpTraceLogFilter> httpTraceLogFilterFilterRegistrationBean() {}
 * 3，
 *    @WebFilter(filterName = " 自定义名称 ", urlPatterns = "/*")
 *    需要在Application上加上
 *    @ServletComponentScan(basePackages = {"com.github.xxxxxx."}) //加上对应的路径扫描
 * @author: yxkong
 * @date: 2023/8/22 3:37 PM
 * @version: 1.0
 */
public abstract class PlatformOncePerRequestFilter extends OncePerRequestFilter implements Ordered {
    protected static final String IGNORE_CONTENT_TYPE = "multipart/form-data";
    protected static final  Set<String> EXCLUDE_URI_SUFFIX_SET =  Sets.newHashSet( "html",
            ".css",
            ".js",
            ".png",
            ".gif",".html",".htm");
    protected static final  Set<String> EXCLUDE_URI_PREFIX_SET =  Sets.newHashSet( "/swagger-resources","/actuator/");
    /** 需要过滤掉的静态文件*/
    protected static final Set<String> EXCLUDE_URI_SET = Sets.newHashSet(
            "/configure",
            "/v3/api-docs",
            "/v2/api-docs",
            "/swagger",
            "/csrf",
            "/webjars");

    /**
     * 是否拦截
     * 非url，multipart/form-data，静态文件，排除的uri，排除的uri后缀，排除的uri前缀
     * @param request
     * @return
     */
    protected boolean isIntercept(HttpServletRequest request) {
        String uri = request.getRequestURI().toLowerCase();
        return !isRequestValid(request)
                || Objects.equals(IGNORE_CONTENT_TYPE, request.getContentType())
                || EXCLUDE_URI_SUFFIX_SET.stream().anyMatch(uri::endsWith)
                || EXCLUDE_URI_PREFIX_SET.stream().anyMatch(uri::startsWith)
                || EXCLUDE_URI_SET.contains(uri);
    }

    private boolean isRequestValid(HttpServletRequest request) {
        try {
            new URI(request.getRequestURL().toString());
            return true;
        } catch (URISyntaxException ex) {
            return false;
        }
    }


    private Pair<ContentCachingRequestWrapper, ContentCachingResponseWrapper> getRequestResponseWrappers(HttpServletRequest request, HttpServletResponse response) {
        return Pair.of(
                getRequestWrapper(request).getRight(),
                getResponseWrapper(response).getRight()
        );
    }


    /**
     * 可多次读取request，需要在链路上传递下去
     * @param request
     * @return
     */
    protected Pair<Boolean,ContentCachingRequestWrapper> getRequestWrapper(HttpServletRequest request){
        return RequestHolder.getContentCachingRequestWrapper(request);

    }

    /**
     * 可多次读取response，需要在链路上传递下去
     * @param response
     * @return
     */
    protected Pair<Boolean,ContentCachingResponseWrapper> getResponseWrapper(HttpServletResponse response){
        return RequestHolder.getContentCachingResponseWrapper(response);
    }

}
