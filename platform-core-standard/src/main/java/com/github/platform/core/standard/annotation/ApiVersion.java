package com.github.platform.core.standard.annotation;

import java.lang.annotation.*;

/**
 * 接口版本控制
 * 常用注解说明：
 * 1. RetentionPolicy(保留策略)是一个enum类型，有三个值
 *      SOURCE        --  这个Annotation类型的信息只会保留在程序源码里，源码如果经过了编译后，Annotation的数据就会消失，并不会保留在编译好的.class文件里
 *      CLASS         --  这个Annotation类型的信息保留在程序源码中，同时也会保留在编译好的.class文件里面,在执行的时候，并不会把这一些信息加载到虚拟 机(JVM)中去.注意一下，当你没有设定一个Annotation类型的Retention值时，系统默认值是CLASS。
 *      RUNTIME       --  在源码、编译好的.class文件中保留信息，在执行的时候会把这一些信息加载到JVM中去的。
 *
 * 2.ElementType @Target中的ElementType用来指定Annotation类型可以用在哪些元素上
 *      TYPE(类型)    -- 在Class，Interface，Enum和Annotation类型上
 *      FIELD        -- 属性上
 *      METHOD       -- 方法上
 *      PARAMETER    -- 参数上
 *      CONSTRUCTOR  -- 构造函数上
 *      LOCAL_VARIABLE -- 局部变量
 *      ANNOTATION_TYPE   -- Annotation类型上
 *      PACKAGE           -- 包上
 *
 * 3.Documented    -- 让这个Annotation类型的信息能够显示在API说明文档上；没有添加的话，使用javadoc生成的API文件找不到这个类型生成的信息

 * @author: yxkong
 * @date: 2022/6/16 5:57 PM
 * @version: 1.0
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiVersion {
    int value() default 1;
}
