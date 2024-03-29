package com.github.platform.core.sms.adapter.api.convert;

import com.github.platform.core.sms.adapter.api.command.SysSmsWhiteListCmd;
import com.github.platform.core.sms.adapter.api.command.SysSmsWhiteListQuery;
import com.github.platform.core.sms.domain.context.SysSmsWhiteListContext;
import com.github.platform.core.sms.domain.context.SysSmsWhiteListQueryContext;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
/**
* 短信白名单Controller到Application层的适配
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-14 17:39:30.643
* @version 1.0
*/
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SysSmsWhiteListAdapterConvert {
    /**
    * 查询实体转查询上下文
    * @param query 查询实体
    * @return 查询上下文
    */
    SysSmsWhiteListQueryContext toQuery(SysSmsWhiteListQuery query);
    /**
    * 操作实体转操作上下文
    * @param cmd 操作实体
    * @return 操作上下文
    */
    SysSmsWhiteListContext toContext(SysSmsWhiteListCmd cmd);
}