package com.github.platform.core.code.infra.gateway.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.platform.core.code.domain.common.entity.CodeColumnConfigBase;
import com.github.platform.core.code.domain.common.entity.CodeGenConfigBase;
import com.github.platform.core.code.domain.context.ColumnContext;
import com.github.platform.core.code.domain.context.GenConfigContext;
import com.github.platform.core.code.domain.context.TableQueryContext;
import com.github.platform.core.code.domain.dto.ColumnDto;
import com.github.platform.core.code.domain.dto.GenConfigDto;
import com.github.platform.core.code.domain.dto.TableDto;
import com.github.platform.core.code.domain.gateway.IGeneratorGateway;
import com.github.platform.core.code.infra.convert.ColumnInfraConvert;
import com.github.platform.core.code.infra.convert.GenInfraConvert;
import com.github.platform.core.common.gateway.BaseGatewayImpl;
import com.github.platform.core.common.utils.CollectionUtil;
import com.github.platform.core.common.utils.StringUtils;
import com.github.platform.core.persistence.entity.code.TablesBase;
import com.github.platform.core.persistence.mapper.code.CodeColumnConfigMapper;
import com.github.platform.core.persistence.mapper.code.CodeGenConfigMapper;
import com.github.platform.core.persistence.mapper.code.TablesMapper;
import com.github.platform.core.standard.entity.dto.PageBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 代码生成网关实现
 * @Author: yxkong
 * @Date: 2023/4/25 2:03 PM
 * @version: 1.0
 */
@Service
@Slf4j
public class GeneratorGatewayImpl extends BaseGatewayImpl implements IGeneratorGateway {
    @Resource
    private CodeColumnConfigMapper codeColumnConfigMapper;
    @Resource
    private CodeGenConfigMapper genConfigMapper;
    @Resource
    private TablesMapper tablesMapper;
    @Resource
    private ColumnInfraConvert convert;
    @Resource
    private GenInfraConvert genInfraConvert;

    @Override
    public List<TablesBase> getAllTables(String dbName,String tableName) {
        TablesBase record = TablesBase.builder().tableSchema(dbName).tableName(tableName).build();
        return tablesMapper.findListBy(record);
    }


    @Override
    public void sync(List<ColumnDto> codeColumns, List<ColumnDto> sysColumns) {
        // 数据库字段改变或新增了
        sysColumns.forEach(entity -> {
            //在codeColumns检索对应的字段，检索到，表示已经存在，可能存在更新
            Optional<ColumnDto> optional = codeColumns.stream().filter(c -> c.getColumnName().equalsIgnoreCase(entity.getColumnName())).findFirst();
            CodeColumnConfigBase columnConfigDO = convert.toColumnDo(entity);
            //已经存在了，更新
            if (optional.isPresent()) {
                ColumnDto columnDto = optional.get();
                if(!columnDto.getRemark().equals(entity.getRemark())){
                    columnConfigDO.setId(columnDto.getId());
                    codeColumnConfigMapper.updateById(columnConfigDO);
                }
            }else {
                codeColumnConfigMapper.insert(columnConfigDO);
            }
        });

        codeColumns.forEach(entity -> {
            //在information_schema检索对应的字段，检索不到，表示删除了
            List<ColumnDto> collect = sysColumns.stream().filter(c -> c.getColumnName().equalsIgnoreCase(entity.getColumnName())).collect(Collectors.toList());
            //不存在，就删除
            if (CollectionUtil.isEmpty(collect)) {
                codeColumnConfigMapper.deleteById(entity.getId());
            }
        });

    }

    @Override
    public PageBean<TableDto> findSysTables(TableQueryContext context) {
        TablesBase tablesBase = null;
        if (StringUtils.isNotEmpty(context.getTableName())){
            tablesBase = TablesBase.builder().tableName(context.getTableName()).build();
        }
        PageHelper.startPage(context.getPageNum(),context.getPageSize());
        List<TablesBase> list =  tablesMapper.findListBy(tablesBase);
        return convert.ofPage(new PageInfo<>(list));
    }

    @Override
    public PageBean<GenConfigDto> findGenTables(TableQueryContext context) {
        CodeGenConfigBase genConfigDO = null;
        if (Objects.nonNull(context.getTableName())){
            genConfigDO = CodeGenConfigBase.builder().tableName(context.getTableName()).build();
        }
        PageHelper.startPage(context.getPageNum(),context.getPageSize());
        List<CodeGenConfigBase> list = genConfigMapper.findListBy(genConfigDO);
        return genInfraConvert.ofPageGen(new PageInfo<>(list));
    }

    @Override
    public GenConfigDto findGen(String tableName) {
        CodeGenConfigBase genConfigDO = CodeGenConfigBase.builder().tableName(tableName).build();
        List<CodeGenConfigBase> listBy = genConfigMapper.findListBy(genConfigDO);
        if (CollectionUtil.isNotEmpty(listBy)){
            return genInfraConvert.toDto(listBy.get(0)) ;
        }
        return null;
    }
    @Override
    public GenConfigDto findById(Long id) {
        CodeGenConfigBase genConfigDO = genConfigMapper.findById(id);
        return genInfraConvert.toDto(genConfigDO);
    }

    @Override
    public void updateGen(GenConfigContext context) {
        CodeGenConfigBase genConfigDO = genInfraConvert.toConfigDo(context);
        if (Objects.isNull(genConfigDO) || Objects.isNull(genConfigDO.getId())){
            exception("0","更新失败");
        }
        genConfigMapper.updateById(genConfigDO);
    }

    @Override
    public void updateColumns(List<ColumnContext> columns) {
        List<CodeColumnConfigBase> list = convert.toListDo(columns);
        list.forEach(columnConfigDO -> {
            codeColumnConfigMapper.updateById(columnConfigDO);
        });
    }

    @Override
    public void syncGenConfig(TablesBase s) {
        CodeGenConfigBase record = CodeGenConfigBase.builder().tableName(s.getTableName()).build();
        List<CodeGenConfigBase> list = genConfigMapper.findListBy(record);
        if (CollectionUtil.isEmpty(list)){
            CodeGenConfigBase configDO = convert.toGen(s);
            genConfigMapper.insert(configDO);
        }
    }

    @Override
    public void deleteByTableName(String tableName) {
        genConfigMapper.deleteByTableName(tableName);
    }
}
