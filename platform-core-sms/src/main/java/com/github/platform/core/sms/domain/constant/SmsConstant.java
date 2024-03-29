package com.github.platform.core.sms.domain.constant;

/**
 * 短信常量
 * @author: yxkong
 * @date: 2024/3/27 12:20
 * @version: 1.0
 */
public interface SmsConstant {
    /**
     * 路由类型 随机
     */
    String route_type_random = "random";
    /**
     * 路由类型，轮训
     */
    String route_type_roundRobin = "roundRobin";
    /**
     * 路由类型，指定
     */
    String route_type_specify = "specify";
}
