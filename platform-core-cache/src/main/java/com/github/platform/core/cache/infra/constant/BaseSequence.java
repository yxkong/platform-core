package com.github.platform.core.cache.infra.constant;

/**
 * 序列号枚举基类
 * @Author: yxkong
 * @Date: 2024/8/27
 * @version: 1.0
 */
public interface BaseSequence {
    Long DB_INIT = 100000L;
    /**
     * 序列号前缀，区分业务
     */
    String getPrefix();

    /**
     * 序列号格式化
     */
    String getFormat();

    /**
     * 初始序列号
     * @return
     */
    String getCacheInit();
    /**
     * 数据库初始序列号
     * @return
     */
    Long getDbInit();
}
