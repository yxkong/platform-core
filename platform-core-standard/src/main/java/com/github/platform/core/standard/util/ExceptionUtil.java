package com.github.platform.core.standard.util;

/**
 * 异常工具类
 * @author: yxkong
 * @date: 2023/9/7 11:01 AM
 * @version: 1.0
 */
public class ExceptionUtil {
    public static String getMessage(Exception e) {
        return null == e ? "null" : String.format("%s: %s",e.getClass().getSimpleName(), e.getMessage());
    }
    public static String getMessage(Throwable e) {
        return null == e ? "null" : String.format("%s: %s",e.getClass().getSimpleName(), e.getMessage());
    }
}
