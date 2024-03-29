package com.github.platform.core.code.domain.gateway;

import com.github.platform.core.code.domain.context.ColumnContext;
import com.github.platform.core.code.domain.context.GenConfigContext;
import com.github.platform.core.code.domain.context.TableQueryContext;
import com.github.platform.core.code.domain.dto.ColumnDto;
import com.github.platform.core.code.domain.dto.GenConfigDto;
import com.github.platform.core.code.domain.dto.TableDto;
import com.github.platform.core.persistence.entity.code.TablesBase;
import com.github.platform.core.standard.entity.dto.PageBean;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

/**
 * 代码生成网关
 * @Author: yxkong
 * @Date: 2023/4/25 1:59 PM
 * @version: 1.0
 */
public interface IGeneratorGateway {

    /**
     * 查询指定条件的所有表
     * @return
     */
    List<TablesBase>  getAllTables(String dbName,String tableName);

    /**
     * 同步表信息到代码生成表
     * @param codeColumns 代码生成表中的字段信息
     * @param sysColumns  表的元数据信息
     */
    void sync(List<ColumnDto> codeColumns, List<ColumnDto> sysColumns);

    /**
     * 查询系统表信息
     * @param context
     * @return
     */
    PageBean<TableDto> findSysTables(TableQueryContext context);

    /**
     * 查询代码生成表
     * @param context
     * @return
     */
    PageBean<GenConfigDto> findGenTables(TableQueryContext context);

    /**
     * 根据表名查询配置信息
     * @param tableName
     * @return
     */
    GenConfigDto findGen(String tableName);

    /**
     * 根据id查询配置信息
     * @param id
     * @return
     */
    GenConfigDto findById(Long id);

    void updateGen(GenConfigContext context);

    void updateColumns(List<ColumnContext> columns);

    /**
     * 同步指定表
     * @param tablesBase
     */
    void syncGenConfig(TablesBase tablesBase);

    /**
     * 根据表名删除记录
     * @param tableName
     */
    void deleteByTableName(String tableName);


}
