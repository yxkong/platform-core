package com.github.platform.core.common.utils;

import org.yaml.snakeyaml.Yaml;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;

/**
 * ymal文件解析
 *
 * @author: yxkong
 * @date: 2021/12/17 2:58 PM
 * @version: 1.0
 */
public class YamlUtil {

    private static Yaml yaml = new Yaml();

    /**
     * 解析yaml string 到对象
     * RouteDto routeDto = YamlUtil.load(configInfo, RouteDto.class);
     * @param text
     * @param type
     * @param <T>
     * @return
     */
    public static  <T> T load(String text, Class<T> type) {
        return yaml.loadAs(text,type);
    }
    /**
     * 递归将 YML 节点值存入 MAP
     */
    public static Map<String,Object> load(String text){
        Map<String,Object> ans = new LinkedHashMap<>();
        Map<String,Object> yml = yaml.load(text);
        if(!CollectionUtil.isEmpty(yml)){
            getVal(new Stack<>(),yml,ans);
        }
        return ans;
    }

    /**
     * 利用栈来将yaml文件的解析成kv
     *  如果是map类型,key的结尾为map，否则解析为k v
     * @param stack
     * @param yml
     * @param ans
     */
    public static void getVal(Stack<String> stack,Map<String,Object> yml,Map<String,Object> ans){
        for (String key:yml.keySet()){
            Object tmp = yml.get(key);
            stack.add(key);
            if (tmp instanceof Map && !key.endsWith("map")){
                getVal(stack,(Map<String,Object>)tmp,ans);
            }else {
                ans.put(stack.stream().collect(Collectors.joining(".")),tmp);
                stack.pop();
            }
        }
        if (!stack.isEmpty()){
            stack.pop();
        }
    }
}
