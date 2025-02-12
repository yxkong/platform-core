package com.github.platform.core.common.annotation;

import com.github.platform.core.common.configuration.condition.InListConditional;
import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

/**
 * 自定义条件主键，判断对应的havingValue 是否在列表中
 * @author: yxkong
 * @date: 2023/2/18 11:13 AM
 * @version: 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
@Conditional({InListConditional.class})
public @interface ConditionalOnPropertyInList {
    /**
     * 前缀
     * @return
     */
    String prefix() default "";

    /**
     * 直接定位到属性的名称
     * @return
     */
    String[] name() default {};

    /**
     * 是否有对应的值
     * @return
     */
    String havingValue() default "";

    /**
     * 确实匹配的时候默认值
     * @return
     */
    boolean matchIfMissing() default false;

}
