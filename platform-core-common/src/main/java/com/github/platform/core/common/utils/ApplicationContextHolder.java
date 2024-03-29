package com.github.platform.core.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 注意此类使用必须先注入applicationContext
 * @author: yxkong
 * @date: 2021/5/17 3:40 下午
 * @version: 1.0
 */
@Component
public class ApplicationContextHolder implements ApplicationContextAware {
    public static final String LOCAL_PROFILE = "local";
    public static final String DEV_PROFILE = "dev";
    public static final String TEST_PROFILE = "test";
    public static final String PROD_PROFILE = "prod";
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextHolder.applicationContext = applicationContext;
    }

    /**
     * 获取 ApplicationContext
     * @return
     */
    public static ApplicationContext getApplicationContext(){
        return ApplicationContextHolder.applicationContext;
    }
    /**
     * 根据beanId获取容器中的bean
     *
     * @param name
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        return (T) applicationContext.getBean(name);
    }

    /**
     * 获取对象
     *
     * @param name
     * @throws BeansException
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name, Class<T> clz) {
        return applicationContext.getBean(name, clz);
    }

    /**
     * 根据类型获取所有的bean
     *
     * @param type
     * @param <T>
     * @return
     */
    public static <T> Map<String, T> getBeansOfType(Class<T> type) {
        return applicationContext.getBeansOfType(type);
    }

    /**
     * 获取指定类型的Bean
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> clazz) {

        if (clazz == null) {
            return null;
        }
        return (T) applicationContext.getBean(clazz);
    }
    public static String getProfile(){
        return applicationContext.getEnvironment().getActiveProfiles()[0];
    }

    /**
     * 是否生产环境
     * @return
     */
    public static Boolean isProd(){
        return PROD_PROFILE.equalsIgnoreCase(getProfile());
    }

    /**
     * 是否local环境
     * @return
     */
    public static Boolean isLocal(){
        return LOCAL_PROFILE.equalsIgnoreCase(getProfile());
    }
    /**
     * 是否dev环境
     * @return
     */
    public static Boolean isDev(){
        return DEV_PROFILE.equalsIgnoreCase(getProfile());
    }
    /**
     * 是否test环境
     * @return
     */
    public static Boolean isTest(){
        return TEST_PROFILE.equalsIgnoreCase(getProfile());
    }
}