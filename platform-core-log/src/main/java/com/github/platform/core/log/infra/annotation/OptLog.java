package com.github.platform.core.log.infra.annotation;

import com.github.platform.core.log.domain.constants.LogOptTypeEnum;
import com.github.platform.core.standard.constant.OptLogConstant;

import java.lang.annotation.*;


/**
 * 操作日志注解,
 * @author: yxkong
 * @date: 2023/4/23 5:02 PM
 * @version: 1.0
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OptLog {
    /**
     * 模块
     */
    String module() default "";
    /**
     * 标题
     */
    String title() default "";
    /**
     * 日志输出排除指定的请求参数
     */
    String[] excludeParamNames() default {};
    /**
     * 日志输出需要掩码的字段，默认掩码字段见 {@link OptLogConstant.defaultMaskFields }
     * @return
     */
    String[] maskParamNames() default {};

    /**
     * 默认query (查询)
     * add
     * modify
     * delete
     * mix
     */
    LogOptTypeEnum optType() default LogOptTypeEnum.query;

    /**
     * 持久化，默认true,不需要持久化修改为false
     * @return
     */
    boolean persistent() default true;
}
