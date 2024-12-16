package com.github.platform.core.sys.adapter.api.convert;

import com.github.platform.core.sys.adapter.api.command.loginlog.SysLoginLogCmd;
import com.github.platform.core.sys.adapter.api.command.loginlog.SysLoginLogQuery;
import com.github.platform.core.sys.domain.context.SysLoginLogContext;
import com.github.platform.core.sys.domain.context.SysLoginLogQueryContext;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mappings;

/**
* 登录日志Controller到Application层的适配
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-15 10:55:05.685
* @version 1.0
*/
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SysLoginLogAdapterConvert {
    /**
    * 查询实体转查询上下文
    * @param query 查询实体
    * @return 查询上下文
    */
    @Mappings({
            @Mapping(target = "createBy",source = "loginName"),
    })
    SysLoginLogQueryContext toQuery(SysLoginLogQuery query);
    /**
    * 操作实体转操作上下文
    * @param cmd 操作实体
    * @return 操作上下文
    */
    SysLoginLogContext toContext(SysLoginLogCmd cmd);
}