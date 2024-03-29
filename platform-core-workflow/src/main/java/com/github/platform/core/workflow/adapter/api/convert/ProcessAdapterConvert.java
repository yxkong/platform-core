package com.github.platform.core.workflow.adapter.api.convert;

import com.github.platform.core.workflow.adapter.api.command.ProcessDetailQuery;
import com.github.platform.core.workflow.adapter.api.command.ProcessQuery;
import com.github.platform.core.workflow.domain.context.ProcessDetailQueryContext;
import com.github.platform.core.workflow.domain.context.ProcessQueryContext;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/**
* ProcessController到Application层的适配
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-09-22 16:35:27.395
* @version 1.0
*/
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProcessAdapterConvert {

    /**
     * 流程处理
     * @param query
     * @return
     */
    ProcessQueryContext toQueryContext(ProcessQuery query);

    /**
     * 流程明细查询
     * @param query
     * @return
     */
    ProcessDetailQueryContext toQueryDetailContext(ProcessDetailQuery query);
}