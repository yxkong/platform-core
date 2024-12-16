package com.github.platform.core.monitor.adapter.api.convert;

import com.github.platform.core.monitor.adapter.api.command.SysOptLogCmd;
import com.github.platform.core.monitor.adapter.api.command.SysOptLogQuery;
import com.github.platform.core.monitor.domain.context.SysOptLogContext;
import com.github.platform.core.monitor.domain.context.SysOptLogQueryContext;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
/**
* 操作日志Controller到Application层的适配
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-15 10:55:05.960
* @version 1.0
*/
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SysOptLogAdapterConvert {
    /**
    * 查询实体转查询上下文
    * @param query 查询实体
    * @return 查询上下文
    */
    SysOptLogQueryContext toQuery(SysOptLogQuery query);
    /**
    * 操作实体转操作上下文
    * @param cmd 操作实体
    * @return 操作上下文
    */
    SysOptLogContext toContext(SysOptLogCmd cmd);
}