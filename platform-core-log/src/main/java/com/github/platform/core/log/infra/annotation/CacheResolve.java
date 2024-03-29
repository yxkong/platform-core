package com.github.platform.core.log.infra.annotation;
import java.lang.annotation.*;

/**
 * 缓存日志注解
 * @author: yxkong
 * @date: 2023/5/5 2:16 PM
 * @version: 1.0
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CacheResolve {
    /**
     * 缓存类型 string、hash、list、zset
     * @return
     */
    String type() default "string";
    /**
     * 命令
     * @return
     */
    String command() default "";

    /**
     * redis的key的参数名称
     * @return
     */
    String key() default "";

    /**
     * 过期时间名称
     * @return
     */
    String expire() default "";
}
