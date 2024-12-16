package com.github.platform.core.common.plugin.mybatis.dialect;

/**
 * @author: yxkong
 * @date: 2021/6/3 5:15 下午
 * @version: 1.0
 */
public abstract class BaseDialect {

    public static enum Type {
        /**
         * 数据库方言枚举
         */
        MYSQL,
        ORACLE
    }

    /**
     * 分页封装
     * @param sql sql语句
     * @param skipResults 
     * @param maxResults 最大返回函数
     * @return
     */
    public abstract String getLimitString(String sql, int skipResults, int maxResults);

}