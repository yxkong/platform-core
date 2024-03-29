package com.github.platform.core.web.plugin.spring.mvc;

import com.github.platform.core.standard.annotation.ApiVersion;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.Assert;
import org.springframework.web.servlet.mvc.condition.RequestCondition;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * 重写requestMapping,自定义版本匹配器
 * @author: yxkong
 * @date: 2022/6/16 6:16 PM
 * @version: 1.0
 */
public class ApiVersionRequestMappingHandlerMapping extends RequestMappingHandlerMapping {

    /**
     * 扫描类上的@ApiVersion
     * @param handlerType
     * @return
     */
    @Override
    protected RequestCondition<?> getCustomTypeCondition(Class<?> handlerType) {
        ApiVersion apiVersion = AnnotationUtils.findAnnotation(handlerType, ApiVersion.class);
        return buildRequestCondition(apiVersion);
    }

    /**
     * 扫描方法上的@ApiVersion
     * @param method
     * @return
     */
    @Override
    protected RequestCondition<?> getCustomMethodCondition(Method method) {
        ApiVersion apiVersion = AnnotationUtils.findAnnotation(method, ApiVersion.class);
        return buildRequestCondition(apiVersion);
    }

    /**
     * 构建ApiVersionCondition
     * @param apiVersion
     * @return
     */
    private RequestCondition<ApiVersionCondition> buildRequestCondition(ApiVersion apiVersion) {
        if (Objects.isNull(apiVersion)) {
            return null;
        }
        //获取注解中配置的值，默认为1
        int value = apiVersion.value();
        Assert.isTrue(value >= 1, "Api 版本号必须大于等于1");
        return new ApiVersionCondition(value);
    }
}
