package com.github.platform.core.auth.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 数据权限过滤注解
 *
 * @author wangxiaozhou
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataScope {

    /**
     * 表的别名
     * @return
     */
    String tableAlias() default "table";
}
