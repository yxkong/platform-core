package com.github.platform.core.code.infra.convert;

import com.github.pagehelper.PageInfo;
import com.github.platform.core.code.domain.common.entity.CodeColumnConfigBase;
import com.github.platform.core.code.domain.common.entity.CodeGenConfigBase;
import com.github.platform.core.code.domain.context.ColumnContext;
import com.github.platform.core.code.domain.dto.ColumnDto;
import com.github.platform.core.code.domain.dto.TableDto;
import com.github.platform.core.persistence.entity.code.ColumnsBase;
import com.github.platform.core.persistence.entity.code.TablesBase;
import com.github.platform.core.standard.entity.dto.PageBean;
import org.mapstruct.*;

import java.util.List;

/**
 * 字段转化基础设施层
 * @Author: yxkong
 * @Date: 2023/4/25 2:09 PM
 * @version: 1.0
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ColumnInfraConvert {
    @Named("yesNoToInteger")
    default Integer yesNoToInteger(String value) {
        return "YES".equalsIgnoreCase(value) ? 1 : 0;
    }

    List<ColumnDto> sysToEntity(List<ColumnsBase> columns);
    @Mappings({
            @Mapping(target = "remark", source = "columnComment"),
            @Mapping(target = "columnType", source = "dataType"),
            @Mapping(target = "notNull", source = "isNullable",qualifiedByName = "yesNoToInteger"),
    })
    ColumnDto sysToEntity(ColumnsBase columnsBase);

    List<ColumnDto> codeToEntity(List<CodeColumnConfigBase> columns);

    @Mappings({
            @Mapping(target = "formShow", source = "formShow",defaultValue = "1"),
            @Mapping(target = "listShow", source = "listShow",defaultValue = "1"),
            @Mapping(target = "queryShow", source = "queryShow",defaultValue = "0"),
            @Mapping(target = "formType", source = "formType",defaultValue = "input"),
            @Mapping(target = "queryType", source = "queryType",defaultValue = "="),
    })
    ColumnDto codeToEntity(CodeColumnConfigBase columnConfigDO);

    CodeColumnConfigBase toColumnDo(ColumnDto entity);
    @Mappings({
            @Mapping(target = "pageNum", source = "pageNum"),
            @Mapping(target = "pages", source = "pages"),
            @Mapping(target = "pageSize", source = "pageSize"),
            @Mapping(target = "totalSize", source = "total"),
            @Mapping(target = "data", source = "list"),
    })
    PageBean<TableDto> ofPage(PageInfo<TablesBase> pageInfo);

    TableDto toDto(TablesBase table);

    @Mappings({
            @Mapping(target = "dbName", source = "tableSchema"),
    })
    CodeGenConfigBase toGen(TablesBase tablesBase);

    List<CodeColumnConfigBase> toListDo(List<ColumnContext> columns);
    CodeColumnConfigBase toColumnDo(ColumnContext context);

}
