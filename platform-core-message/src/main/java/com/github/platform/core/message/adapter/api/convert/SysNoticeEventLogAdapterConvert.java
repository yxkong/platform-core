package com.github.platform.core.message.adapter.api.convert;

import com.github.platform.core.message.adapter.api.command.SysNoticeEventLogCmd;
import com.github.platform.core.message.adapter.api.command.SysNoticeEventLogQuery;
import com.github.platform.core.message.domain.context.SysNoticeEventLogContext;
import com.github.platform.core.message.domain.context.SysNoticeEventLogQueryContext;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
/**
 * 通知事件日志Controller到Application层的适配
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author yxkong
 * @date 2024-12-04 13:36:01.514
 * @version 1.0
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SysNoticeEventLogAdapterConvert {
    /**
    * 查询实体转查询上下文
    * @param query 查询实体
    * @return 查询上下文
    */
    SysNoticeEventLogQueryContext toQuery(SysNoticeEventLogQuery query);
    /**
    * 操作实体转操作上下文
    * @param cmd 操作实体
    * @return 操作上下文
    */
    SysNoticeEventLogContext toContext(SysNoticeEventLogCmd cmd);
}