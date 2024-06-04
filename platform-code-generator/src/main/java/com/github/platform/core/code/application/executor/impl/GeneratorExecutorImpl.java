package com.github.platform.core.code.application.executor.impl;

import com.github.platform.core.code.application.executor.IGeneratorExecutor;
import com.github.platform.core.code.domain.context.ColumnContext;
import com.github.platform.core.code.domain.context.GenConfigContext;
import com.github.platform.core.code.domain.context.GenContext;
import com.github.platform.core.code.domain.context.TableQueryContext;
import com.github.platform.core.code.domain.dto.ColumnDto;
import com.github.platform.core.code.domain.dto.GenConfigDto;
import com.github.platform.core.code.domain.dto.GenDto;
import com.github.platform.core.code.domain.dto.TableDto;
import com.github.platform.core.code.domain.gateway.IColumnsGateway;
import com.github.platform.core.code.domain.gateway.IGeneratorGateway;
import com.github.platform.core.code.infra.utils.GenUtil;
import com.github.platform.core.common.service.BaseExecutor;
import com.github.platform.core.common.utils.StringUtils;
import com.github.platform.core.persistence.entity.code.TablesBase;
import com.github.platform.core.standard.entity.dto.PageBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.zip.ZipOutputStream;

/**
 *
 * @Author: yxkong
 * @Date: 2023/4/25 1:56 PM
 * @version: 1.0
 */
@Service
@Slf4j
public class GeneratorExecutorImpl extends BaseExecutor implements IGeneratorExecutor {
    @Resource
    private IColumnsGateway columnsGateway;
    @Resource
    private IGeneratorGateway generatorGateway;
    @Override
    public void sync(String dbName,String tableName){
        List<TablesBase>  tablesBases =generatorGateway.getAllTables(dbName,tableName);
        for (TablesBase s : tablesBases) {
            generatorGateway.syncGenConfig(s);
            List<ColumnDto> sysColumns = columnsGateway.findSysColumnsBy(dbName,s.getTableName());
            //表名唯一性问题
            List<ColumnDto> codeColumns = columnsGateway.findCodeColumnsBy(s.getTableName());
            generatorGateway.sync(codeColumns,sysColumns);
        }
    }

    @Override
    public PageBean<TableDto> findTables(TableQueryContext context) {
        return generatorGateway.findSysTables(context);
    }

    @Override
    public PageBean<GenConfigDto> query(TableQueryContext context) {
        return generatorGateway.findGenTables(context);
    }

    @Override
    public List<Map<String, Object>> preview(String dbName,String tableName, Integer codeType) throws Exception {
        GenConfigDto genConfig = generatorGateway.findGen(dbName,tableName);
        if (!validate(genConfig)){
            return null;
        }
        List<ColumnDto> columns = columnsGateway.findCodeColumnsBy(tableName);
        return GenUtil.preview(columns, genConfig,codeType);
    }
    private boolean validate(GenConfigDto config ){
        if (StringUtils.isEmpty(config.getApiAlias())){
            return false;
        }
        return true;
    }
    @Override
    public void generatorCode(String dbName,String tableName, Integer codeType) throws Exception {
        List<ColumnDto> columns = columnsGateway.findCodeColumnsBy(tableName);
        GenConfigDto genConfig = generatorGateway.findGen(dbName,tableName);
        GenUtil.generatorCode(columns, genConfig,codeType);
    }


    @Override
    public GenDto findById(Long id) {
        GenConfigDto configDto = generatorGateway.findById(id);
        if (Objects.isNull(configDto)){
            return null;
        }
        if (Objects.isNull(configDto.getCover())){
            configDto.setCover(0);
        }
        List<ColumnDto> columns = columnsGateway.findCodeColumnsBy(configDto.getTableName());
        return GenDto.builder().id(id).tableName(configDto.getTableName()).genConfig(configDto).columns(columns).build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(GenContext context) {
        GenConfigContext genConfig = context.getGenConfig();
        generatorGateway.updateGen(genConfig);
        List<ColumnContext> columns = context.getColumns();
        generatorGateway.updateColumns(columns);
    }

    @Override
    public byte[] downloadCode(String dbName,String tableName, Integer codeType) throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);
        generatorCode(dbName,tableName,codeType, zip);
        if (null != zip){
            zip.close();
        }
        return outputStream.toByteArray();
    }
    private void generatorCode(String dbName,String tableName,Integer codeType,ZipOutputStream zip) throws Exception {
        List<ColumnDto> columns = columnsGateway.findCodeColumnsBy(tableName);
        GenConfigDto genConfig = generatorGateway.findGen(dbName,tableName);
        GenUtil.download(columns, genConfig,codeType,zip);
    }

    @Override
    public void delete(Long id) {
        GenConfigDto configDto = generatorGateway.findById(id);
        columnsGateway.deleteByTableName(configDto.getTableName());
        generatorGateway.deleteByTableName(configDto.getTableName());
    }
}
