package com.github.platform.core.workflow.adapter.api.convert;

import com.github.platform.core.workflow.adapter.api.command.ProcessApprovalRecordCmd;
import com.github.platform.core.workflow.adapter.api.command.ProcessApprovalRecordQuery;
import com.github.platform.core.workflow.domain.context.ProcessApprovalRecordContext;
import com.github.platform.core.workflow.domain.context.ProcessApprovalRecordQueryContext;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
/**
* 流程审批记录Controller到Application层的适配
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-11-09 11:30:36.194
* @version 1.0
*/
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProcessApprovalRecordAdapterConvert {
    /**
    * 查询实体转查询上下文
    * @param query 查询实体
    * @return 查询上下文
    */
    ProcessApprovalRecordQueryContext toQuery(ProcessApprovalRecordQuery query);
    /**
    * 操作实体转操作上下文
    * @param cmd 操作实体
    * @return 操作上下文
    */
    ProcessApprovalRecordContext toContext(ProcessApprovalRecordCmd cmd);
}