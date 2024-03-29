package com.github.platform.core.web.plugin.spring.mvc;

import org.springframework.web.servlet.mvc.condition.RequestCondition;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ApiVersion拦截的条件
 * springmvc的条件注解
 *
 * @author: yxkong
 * @date: 2022/6/16 6:01 PM
 * @version: 1.0
 */
public class ApiVersionCondition implements RequestCondition<ApiVersionCondition> {
    /**接口版本前缀，如：/api/v[1~n]/xxx  */
    private final static Pattern VERSION_PREFIX = Pattern.compile("/v(\\d+)/");
    private int apiVersion;

    public ApiVersionCondition(int apiVersion) {
        this.apiVersion = apiVersion;
    }

    /**
     * 最近优先原则，
     *   优先方法定义的 @ApiVersion
     *   再找类定义的 @ApiVersion
     * @param other
     * @return
     */
    @Override
    public ApiVersionCondition combine(ApiVersionCondition other) {
        return new ApiVersionCondition(other.getApiVersion());
    }

    @Override
    public ApiVersionCondition getMatchingCondition(HttpServletRequest request) {
        //pi
        Matcher m = VERSION_PREFIX.matcher(request.getRequestURI());
        //获取所有符合匹配条件的ApiVersionCondition
        if (m.find()) {
            int version = Integer.parseInt(m.group(1));
            if (version >= getApiVersion()) {
                return this;
            }
        }
        return null;
    }

    /**
     * 自定义比较器，优先匹配版本号比较大的接口
     * @param other
     * @param request
     * @return
     */
    @Override
    public int compareTo(ApiVersionCondition other, HttpServletRequest request) {
        return other.getApiVersion() - getApiVersion();
    }

    public int getApiVersion() {
        return apiVersion;
    }
}
