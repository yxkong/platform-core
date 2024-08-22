package com.github.platform.core.standard.constant;

import lombok.Getter;

/**
 * 发送类型
 * @author: yxkong
 * @date: 2023/9/8 12:06 PM
 * @version: 1.0
 */
@Getter
public enum SendTypeEnum {
    LOCAL(1,"eventPublishService",null,"本地事件，单机使用，默认实现"),
    REDIS(2,"redis","platform_common_event","redis订阅发布，共用一个redis，根据实际情况是否做幂等")
//    KAFKA(3,"kafka","platform.common.event","kafka订阅发布，共用一个kafka,需"),
//    REMOTE_REDIS(4,"redis","platform_common_event","推到指定的redis中，根据实际情况是否做幂等"),
//    REMOTE_KAFKA(5,"kafka","platform.common.event","推送到指定的kafka中，根据实际情况是否需要分组")
    ;

    SendTypeEnum(Integer type,String implBean,String channel, String desc) {
        this.type = type;
        this.implBean = implBean;
        this.channel = channel;
        this.desc = desc;
    }
    /**类型*/
    private final Integer type;
    /**实现bean*/
    private final String implBean;
    /**通道或主题*/
    private final String channel;
    /**描述*/
    private final String desc;
}
