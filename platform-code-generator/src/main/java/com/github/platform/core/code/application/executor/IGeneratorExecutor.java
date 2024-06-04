package com.github.platform.core.code.application.executor;

import com.github.platform.core.code.domain.context.GenContext;
import com.github.platform.core.code.domain.context.TableQueryContext;
import com.github.platform.core.code.domain.dto.GenConfigDto;
import com.github.platform.core.code.domain.dto.GenDto;
import com.github.platform.core.code.domain.dto.TableDto;
import com.github.platform.core.standard.entity.dto.PageBean;
import com.github.platform.core.standard.entity.dto.ResultBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 代码生成器执行器
 * @author: yxkong
 * @date: 2023/12/27 13:42
 * @version: 1.0
 */
public interface IGeneratorExecutor {
    /**
     * 同步表
     * @param dbName
     * @param tableName
     */
    void sync(String dbName,String tableName);

    /**
     * 从information_schema查询当前连接库对应的表
     *
     * @param context
     * @return
     */
    PageBean<TableDto> findTables(TableQueryContext context);

    /**
     * 从genConfig中查询表
     *
     * @param context
     * @return
     */
    PageBean<GenConfigDto> query(TableQueryContext context);

    List<Map<String, Object>> preview(String dbName,String tableName, Integer codeType) throws Exception;

    void generatorCode(String dbName,String tableName, Integer codeType) throws Exception;

    GenDto findById(Long id);

    @Transactional(rollbackFor = Exception.class)
    void update(GenContext context);

    byte[] downloadCode(String dbName,String tableName, Integer codeType) throws Exception;

    void delete(Long id);
}
