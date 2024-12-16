package com.github.platform.core.message.adapter.api.convert;

import com.github.platform.core.message.adapter.api.command.SysNoticeChannelConfigCmd;
import com.github.platform.core.message.adapter.api.command.SysNoticeChannelConfigQuery;
import com.github.platform.core.message.domain.context.SysNoticeChannelConfigContext;
import com.github.platform.core.message.domain.context.SysNoticeChannelConfigQueryContext;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
/**
 * 通知通道配置Controller到Application层的适配
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author yxkong
 * @date 2024-12-04 13:32:28.892
 * @version 1.0
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SysNoticeChannelConfigAdapterConvert {
    /**
    * 查询实体转查询上下文
    * @param query 查询实体
    * @return 查询上下文
    */
    SysNoticeChannelConfigQueryContext toQuery(SysNoticeChannelConfigQuery query);
    /**
    * 操作实体转操作上下文
    * @param cmd 操作实体
    * @return 操作上下文
    */
    SysNoticeChannelConfigContext toContext(SysNoticeChannelConfigCmd cmd);
}