package com.github.platform.core.code.adpter.api.convert;

import com.github.platform.core.code.adpter.api.command.*;
import com.github.platform.core.code.domain.context.ColumnContext;
import com.github.platform.core.code.domain.context.ColumnQueryContext;
import com.github.platform.core.code.domain.context.GenContext;
import com.github.platform.core.code.domain.context.TableQueryContext;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/**
 *
 * @Author: yxkong
 * @Date: 2023/4/25 3:14 PM
 * @version: 1.0
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface GeneratorConvert {
    TableQueryContext toQuery(TableQuery tableQuery);
    ColumnQueryContext toQueryColumn(ColumnQuery tableQuery);

    GenContext toContext(GenCmd cmd);

    ColumnContext toContext(ColumnCmd cmd);


}
