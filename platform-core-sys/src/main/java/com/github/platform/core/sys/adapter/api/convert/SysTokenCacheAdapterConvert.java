package com.github.platform.core.sys.adapter.api.convert;

import com.github.platform.core.sys.adapter.api.command.SysTokenCacheCmd;
import com.github.platform.core.sys.adapter.api.command.SysTokenCacheQuery;
import com.github.platform.core.sys.domain.context.SysTokenCacheContext;
import com.github.platform.core.sys.domain.context.SysTokenCacheQueryContext;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
/**
 * token缓存Controller到Application层的适配
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author yxkong
 * @date 2024-04-28 16:25:03.690
 * @version 1.0
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SysTokenCacheAdapterConvert {
    /**
    * 查询实体转查询上下文
    * @param query 查询实体
    * @return 查询上下文
    */
    SysTokenCacheQueryContext toQuery(SysTokenCacheQuery query);
    /**
    * 操作实体转操作上下文
    * @param cmd 操作实体
    * @return 操作上下文
    */
    SysTokenCacheContext toContext(SysTokenCacheCmd cmd);
}