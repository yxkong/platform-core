package com.github.platform.core.sys.adapter.api.convert;

import com.github.platform.core.sys.adapter.api.command.SysUserLogCmd;
import com.github.platform.core.sys.adapter.api.command.SysUserLogQuery;
import com.github.platform.core.sys.domain.context.SysUserLogContext;
import com.github.platform.core.sys.domain.context.SysUserLogQueryContext;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
/**
* 用户日志表Controller到Application层的适配
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-15 10:55:06.892
* @version 1.0
*/
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SysUserLogAdapterConvert {
    /**
    * 查询实体转查询上下文
    * @param query 查询实体
    * @return 查询上下文
    */
    SysUserLogQueryContext toQuery(SysUserLogQuery query);
    /**
    * 操作实体转操作上下文
    * @param cmd 操作实体
    * @return 操作上下文
    */
    SysUserLogContext toContext(SysUserLogCmd cmd);
}