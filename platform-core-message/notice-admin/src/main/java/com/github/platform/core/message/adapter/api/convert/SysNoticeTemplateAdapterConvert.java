package com.github.platform.core.message.adapter.api.convert;

import com.github.platform.core.message.adapter.api.command.SysNoticeTemplateCmd;
import com.github.platform.core.message.adapter.api.command.SysNoticeTemplateQuery;
import com.github.platform.core.message.domain.context.SysNoticeTemplateContext;
import com.github.platform.core.message.domain.context.SysNoticeTemplateQueryContext;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
/**
 * 消息通知模板Controller到Application层的适配
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author yxkong
 * @date 2024-12-04 13:32:24.593
 * @version 1.0
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SysNoticeTemplateAdapterConvert {
    /**
    * 查询实体转查询上下文
    * @param query 查询实体
    * @return 查询上下文
    */
    SysNoticeTemplateQueryContext toQuery(SysNoticeTemplateQuery query);
    /**
    * 操作实体转操作上下文
    * @param cmd 操作实体
    * @return 操作上下文
    */
    SysNoticeTemplateContext toContext(SysNoticeTemplateCmd cmd);
}