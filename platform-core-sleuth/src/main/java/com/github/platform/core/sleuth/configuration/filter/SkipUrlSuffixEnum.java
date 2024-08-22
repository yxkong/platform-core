package com.github.platform.core.sleuth.configuration.filter;

import java.util.Arrays;

/**
 * 不采集过滤枚举
 * @author: yxkong
 * @date: 2023/8/18 3:20 下午
 * @version: 1.0
 */
public enum SkipUrlSuffixEnum {
    HTM(".htm"), HTML(".html"), CSS(".css"), JS(".js"), PNG(".png"), JPG(".jpg"), GIF(".gif"),
    SWAGGERRESOURCES("/swagger-resources"), APIDOCS("/v2/api-docs");

    // 成员变量
    private String suffix;

    // 构造方法
    private SkipUrlSuffixEnum(String suffix) {
        this.suffix = suffix;
    }

    public String getSuffix() {
        return suffix;
    }

    public static boolean contains(String uri){
        return Arrays.stream(SkipUrlSuffixEnum.values()).anyMatch(s->s.getSuffix().equalsIgnoreCase(uri));
    }
}

