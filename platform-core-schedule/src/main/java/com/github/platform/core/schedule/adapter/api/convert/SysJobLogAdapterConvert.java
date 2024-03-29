package com.github.platform.core.schedule.adapter.api.convert;

import com.github.platform.core.schedule.adapter.api.command.SysJobLogQuery;
import com.github.platform.core.schedule.domain.context.SysJobLogQueryContext;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
/**
* 任务执行日志Controller到Application层的适配
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-09-06 18:53:10.711
* @version 1.0
*/
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SysJobLogAdapterConvert {
    /**
    * 查询实体转查询上下文
    * @param query 查询实体
    * @return 查询上下文
    */
    SysJobLogQueryContext toQuery(SysJobLogQuery query);

}