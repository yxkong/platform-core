package com.github.platform.core.cache.infra.annotation;

import java.lang.annotation.*;
import com.github.platform.core.cache.infra.constant.RepeatSubmitEnum;
/**
 * 自定义注解防止表单重复提交
 *
 * @author wangxiaozhou
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RepeatSubmit {
    /**
     * 间隔时间(秒)，小于此时间视为重复提交
     */
    int interval() default 5;

    RepeatSubmitEnum type() default RepeatSubmitEnum.USER;

    boolean release() default true;
}