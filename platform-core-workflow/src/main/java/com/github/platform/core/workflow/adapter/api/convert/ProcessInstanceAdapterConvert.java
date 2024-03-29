package com.github.platform.core.workflow.adapter.api.convert;

import com.github.platform.core.workflow.adapter.api.command.ProcessInstanceCmd;
import com.github.platform.core.workflow.adapter.api.command.ProcessInstanceQuery;
import com.github.platform.core.workflow.adapter.api.command.ProcessQuery;
import com.github.platform.core.workflow.adapter.api.command.ProcessRunCmd;
import com.github.platform.core.workflow.domain.context.ProcessInstanceContext;
import com.github.platform.core.workflow.domain.context.ProcessInstanceQueryContext;
import com.github.platform.core.workflow.domain.context.ProcessQueryContext;
import com.github.platform.core.workflow.domain.context.ProcessRunContext;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mappings;

/**
* 流程实例Controller到Application层的适配
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-09-22 16:35:27.395
* @version 1.0
*/
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProcessInstanceAdapterConvert {
    /**
    * 查询实体转查询上下文
    * @param query 查询实体
    * @return 查询上下文
    */
    ProcessInstanceQueryContext toQuery(ProcessInstanceQuery query);
    /**
    * 操作实体转操作上下文
    * @param cmd 操作实体
    * @return 操作上下文
    */
    @Mappings({
            @Mapping(target = "id", expression = "java(com.github.platform.core.common.utils.SignUtil.getLongId(cmd.getStrId()))"),
    })
    ProcessInstanceContext toContext(ProcessInstanceCmd cmd);

    ProcessRunContext toProcessRunContext(ProcessRunCmd cmd);

    ProcessQueryContext toProcessQueryContext(ProcessQuery query);
}