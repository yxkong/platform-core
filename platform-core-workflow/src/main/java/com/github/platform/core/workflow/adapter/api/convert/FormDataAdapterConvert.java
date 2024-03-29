package com.github.platform.core.workflow.adapter.api.convert;

import com.github.platform.core.workflow.adapter.api.command.FormDataCmd;
import com.github.platform.core.workflow.adapter.api.command.FormDataQuery;
import com.github.platform.core.workflow.domain.context.FormDataContext;
import com.github.platform.core.workflow.domain.context.FormDataQueryContext;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
/**
* 表单数据Controller到Application层的适配
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-11-17 18:23:23.341
* @version 1.0
*/
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface FormDataAdapterConvert {
    /**
    * 查询实体转查询上下文
    * @param query 查询实体
    * @return 查询上下文
    */
    FormDataQueryContext toQuery(FormDataQuery query);
    /**
    * 操作实体转操作上下文
    * @param cmd 操作实体
    * @return 操作上下文
    */
    FormDataContext toContext(FormDataCmd cmd);
}