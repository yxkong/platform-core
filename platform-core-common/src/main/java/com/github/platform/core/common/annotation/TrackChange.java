package com.github.platform.core.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 追踪变化
 * @author: yxkong
 * @date: 2024/6/7 13:57
 * @version: 1.0
 */
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface TrackChange {
    /**备注*/
    String remark();
    /**默认比较追踪，为false 有值直接使用*/
    boolean compare() default true;
    /**
     * 合并某个字段
     * 例如：A 和 B 要组合展示
     * A 的 @TrackChange 中的 merge 填写 B
     * B 的 @TrackChange 中的 merge 填写 固定值ignore 表示忽略
     */
    String merge() default "";
    /**时间格式*/
    String dateFormat() default "";
    /**排序,默认100*/
    int sort() default 100;
}
