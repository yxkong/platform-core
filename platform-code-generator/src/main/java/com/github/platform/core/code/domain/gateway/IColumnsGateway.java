package com.github.platform.core.code.domain.gateway;

import com.github.platform.core.code.domain.dto.ColumnDto;

import java.util.List;

/**
 * 查询数据库字段接口
 * @Author: yxkong
 * @Date: 2023/4/25 2:03 PM
 * @version: 1.0
 */
public interface IColumnsGateway {
    /**
     * 从infomation库中根据表名模糊查询所有的字段
     * @param tableName
     * @return
     */
    List<ColumnDto> findSysColumnsBy(String tableName);
    /**
     * 根据表名查询所有的字段
     *   会给一些字段设置默认值
     * @param tableName
     * @return
     */
    List<ColumnDto> findCodeColumnsBy(String tableName);

    void deleteByTableName(String tableName);
}
