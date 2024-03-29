package com.github.platform.core.gateway.infra.constants;

import java.util.Arrays;

/**
 * @author: yxkong
 * @date: 2023/8/19 10:40
 * @version: 1.0
 */
public enum SkipHeaderEnum {
    parentspanid("x-b3-parentspanid"),
    sampled("x-b3-sampled"),
    spanid("x-b3-spanid"),
    traceid("x-b3-traceid")
    ;
    private String key;
    SkipHeaderEnum(String key){
        this.key = key;
    }

    public String getKey() {
        return key;
    }
    public static boolean contains(String header){
        return Arrays.stream(SkipHeaderEnum.values()).anyMatch(s -> s.getKey().equalsIgnoreCase(header));
    }
}
