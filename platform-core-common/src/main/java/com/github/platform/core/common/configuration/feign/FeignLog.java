package com.github.platform.core.common.configuration.feign;

import java.lang.annotation.*;

/**
 * feign日志是否需要记录的注解，如果不加，代表需要记录，如果加了，根据属性进行判断
 *
 * @author yxkong
 * @date 2019/7/16-18:27
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface FeignLog {

    /**
     * 是否需要记录日志，如果为false，请求和响应内容都不再记录
     */
    boolean value() default true;

    /**
     * 日志关键词，便于搜索
     */
    String keyWord() default "";

    /**
     * 请求需要记录的内容
     */
    FeignLogType request() default @FeignLogType();

    /**
     * 响应需要记录的内容
     */
    FeignLogType response() default @FeignLogType(headers = false);

    @interface FeignLogType {
        /**
         * 是否记录日志
         */
        boolean value() default true;

        /**
         * 是否记录headers
         */
        boolean headers() default true;

        /**
         * 是否记录body
         */
        boolean body() default true;

        /**
         * 排除的headers
         */
        String[] excludeHeaders() default {"content-type", "cache-control", "content-length", "date", "expires",
                "pragma", "x-content-type-options", "x-content-type-options", "x-frame-options", "x-xss-protection", "mode"};
    }
}
