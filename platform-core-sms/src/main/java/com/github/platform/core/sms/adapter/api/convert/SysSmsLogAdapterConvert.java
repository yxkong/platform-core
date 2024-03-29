package com.github.platform.core.sms.adapter.api.convert;

import com.github.platform.core.sms.adapter.api.command.SysSysSmsLogQuery;
import com.github.platform.core.sms.domain.context.SmsLogQueryContext;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
/**
* 短信日志Controller到Application层的适配
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-14 17:39:30.502
* @version 1.0
*/
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SysSmsLogAdapterConvert {
    /**
    * 查询实体转查询上下文
    * @param query 查询实体
    * @return 查询上下文
    */
    SmsLogQueryContext toQuery(SysSysSmsLogQuery query);
}