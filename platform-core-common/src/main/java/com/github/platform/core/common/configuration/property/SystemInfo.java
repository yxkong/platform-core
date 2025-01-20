package com.github.platform.core.common.configuration.property;

import lombok.Data;

/**
 * 系统信息
 * @author: yxkong
 * @date: 2023/8/3 5:13 PM
 * @version: 1.0
 */
@Data
public class SystemInfo {
    /**域名*/
    private String domain;
    /**服务名称*/
    private String serviceName;
    /**版本*/
    private String version;
    /**版本*/
    private String copyright;
}
