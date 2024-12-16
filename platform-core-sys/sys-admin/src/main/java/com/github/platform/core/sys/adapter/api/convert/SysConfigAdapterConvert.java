package com.github.platform.core.sys.adapter.api.convert;

import com.github.platform.core.cache.domain.entity.ConfigEntity;
import com.github.platform.core.sys.adapter.api.command.config.SysConfigCmd;
import com.github.platform.core.sys.adapter.api.command.config.SysConfigQuery;
import com.github.platform.core.sys.domain.context.SysConfigContext;
import com.github.platform.core.sys.domain.context.SysConfigQueryContext;
import com.github.platform.core.sys.domain.dto.SysConfigDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
/**
* 参数配置Controller到Application层的适配
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-15 10:55:04.987
* @version 1.0
*/
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SysConfigAdapterConvert {
    /**
    * 查询实体转查询上下文
    * @param query 查询实体
    * @return 查询上下文
    */
    SysConfigQueryContext toQuery(SysConfigQuery query);
    /**
    * 操作实体转操作上下文
    * @param cmd 操作实体
    * @return 操作上下文
    */
    SysConfigContext toContext(SysConfigCmd cmd);

    /**
     * 转实体
     * @param dto
     * @return
     */
    ConfigEntity toEntity(SysConfigDto dto);
}