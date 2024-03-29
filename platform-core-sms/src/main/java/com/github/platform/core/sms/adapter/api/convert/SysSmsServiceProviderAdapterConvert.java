package com.github.platform.core.sms.adapter.api.convert;

import com.github.platform.core.sms.adapter.api.command.SysSmsServiceProviderCmd;
import com.github.platform.core.sms.adapter.api.command.SysSmsServiceProviderQuery;
import com.github.platform.core.sms.domain.context.SysSmsServiceProviderContext;
import com.github.platform.core.sms.domain.context.SysSmsServiceProviderQueryContext;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
/**
* 服务商Controller到Application层的适配
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-14 17:39:29.748
* @version 1.0
*/
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SysSmsServiceProviderAdapterConvert {
    /**
    * 查询实体转查询上下文
    * @param query 查询实体
    * @return 查询上下文
    */
    SysSmsServiceProviderQueryContext toQuery(SysSmsServiceProviderQuery query);
    /**
    * 操作实体转操作上下文
    * @param cmd 操作实体
    * @return 操作上下文
    */
    SysSmsServiceProviderContext toContext(SysSmsServiceProviderCmd cmd);
}