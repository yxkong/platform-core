package com.github.platform.core.workflow.adapter.api.convert;

import com.github.platform.core.workflow.adapter.api.command.ProcessDefinitionCmd;
import com.github.platform.core.workflow.adapter.api.command.ProcessDefinitionQuery;
import com.github.platform.core.workflow.domain.context.ProcessDefinitionContext;
import com.github.platform.core.workflow.domain.context.ProcessDefinitionQueryContext;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mappings;

/**
* 流程管理Controller到Application层的适配
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-09-22 16:35:24.551
* @version 1.0
*/
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProcessDefinitionAdapterConvert {
    /**
    * 查询实体转查询上下文
    * @param query 查询实体
    * @return 查询上下文
    */
    ProcessDefinitionQueryContext toQuery(ProcessDefinitionQuery query);
    /**
    * 操作实体转操作上下文
    * @param cmd 操作实体
    * @return 操作上下文
    */
    @Mappings({
            @Mapping(target = "id", expression = "java(com.github.platform.core.common.utils.SignUtil.getLongId(cmd.getStrId()))"),
    })
    ProcessDefinitionContext toContext(ProcessDefinitionCmd cmd);
}