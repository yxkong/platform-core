package com.github.platform.core.sys.adapter.api.convert;

import com.github.platform.core.sys.adapter.api.command.dept.SysDeptCmd;
import com.github.platform.core.sys.adapter.api.command.dept.SysDeptQuery;
import com.github.platform.core.sys.domain.context.SysDeptContext;
import com.github.platform.core.sys.domain.context.SysDeptQueryContext;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
/**
* 系统部门Controller到Application层的适配
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-15 10:55:05.241
* @version 1.0
*/
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SysDeptAdapterConvert {
    /**
    * 查询实体转查询上下文
    * @param query 查询实体
    * @return 查询上下文
    */
    SysDeptQueryContext toQuery(SysDeptQuery query);
    /**
    * 操作实体转操作上下文
    * @param cmd 操作实体
    * @return 操作上下文
    */
    SysDeptContext toContext(SysDeptCmd cmd);
}