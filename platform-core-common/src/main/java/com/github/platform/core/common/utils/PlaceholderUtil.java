package com.github.platform.core.common.utils;

import org.apache.commons.text.StringSubstitutor;

import java.util.Map;

/**
 * 占位符替换
 * 比如： "Hello, ${user}"  替换user
 * @author: yxkong
 * @date: 2022/7/4 11:24 AM
 * @version: 1.0
 */
public class PlaceholderUtil {

    /**
     * 占位符替换
     * @param source 源内容
     * @param params 参数实例
     * @return
     */
    public static String replace(String source, Map<String, Object> params){
        return replace(source,params,true);
    }

    /**
     * 占位符替换
     * @param source 源内容 比如： "Hello, ${user}"  替换user
     * @param params 参数实例
     * @param enableSubstitutionInVariables
     * @return
     */
    public static String replace(String source, Map<String, Object> params, boolean enableSubstitutionInVariables){
        return replace(source,params,StringSubstitutor.DEFAULT_VAR_START,StringSubstitutor.DEFAULT_VAR_END,enableSubstitutionInVariables);
    }

    /**
     * 占位符替换
     * @param source 源内容
     * @param params 参数数据
     * @param prefix 占位符前缀 例如:${
     * @param suffix 占位符后缀 例如:}
     * @param enableSubstitutionInVariables  是否在变量名称中进行替换
     *   转义符默认为'$'。如果这个字符放在一个变量引用之前，这个引用将被忽略，不会被替换 如$${a}将直接输出${a}
     * @return
     */
    public static String replace(String source, Map<String, Object> params, String prefix, String suffix, boolean enableSubstitutionInVariables){
        StringSubstitutor stringSubstitutor = new  StringSubstitutor(params,prefix, suffix);
        stringSubstitutor.setEnableSubstitutionInVariables(enableSubstitutionInVariables);
        return stringSubstitutor.replace(source);
    }
}
