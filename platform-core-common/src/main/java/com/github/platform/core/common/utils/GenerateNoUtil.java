package com.github.platform.core.common.utils;

/**
 * @author yxkong
 */
public class GenerateNoUtil {

    /**
     * 生成编号
     * @param initials 首字母缩写
     * @param id   id编号
     * @return
     */
    public static String generateNo(String initials,Long id){
        return initials + String.format("%07d", id);
    }
}
