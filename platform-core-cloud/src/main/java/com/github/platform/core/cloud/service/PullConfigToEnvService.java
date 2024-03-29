package com.github.platform.core.cloud.service;

/**
 * 配置中心服务
 * @author: yxkong
 * @date: 2023/3/2 4:30 PM
 * @version: 1.0
 */
public interface PullConfigToEnvService {
    /**
     * 初始化配置
     * @param dataId
     * @param group
     * @param timeout
     */
    void initConfig(String dataId,String group,Long timeout);

    /**
     * 配置添加监听
     * @param dataId
     * @param group
     */
    void addListener(String dataId,String group);

}
