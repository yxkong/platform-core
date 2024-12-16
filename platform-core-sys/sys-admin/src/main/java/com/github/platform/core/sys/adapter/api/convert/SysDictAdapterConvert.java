package com.github.platform.core.sys.adapter.api.convert;

import com.github.platform.core.cache.domain.entity.DictEntity;
import com.github.platform.core.sys.adapter.api.command.dict.SysDictCmd;
import com.github.platform.core.sys.adapter.api.command.dict.SysDictQuery;
import com.github.platform.core.sys.domain.context.SysDictContext;
import com.github.platform.core.sys.domain.context.SysDictQueryContext;
import com.github.platform.core.sys.domain.dto.SysDictDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

/**
* 字典数据Controller到Application层的适配
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-15 10:55:05.400
* @version 1.0
*/
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SysDictAdapterConvert {
    /**
    * 查询实体转查询上下文
    * @param query 查询实体
    * @return 查询上下文
    */
    SysDictQueryContext toQuery(SysDictQuery query);
    /**
    * 操作实体转操作上下文
    * @param cmd 操作实体
    * @return 操作上下文
    */
    SysDictContext toContext(SysDictCmd cmd);

    SysDictContext toDict(SysDictCmd dictCmd);

    SysDictQueryContext toDictQuery(SysDictQuery dictQuery);
    List<DictEntity> toList(List<SysDictDto> list);
    DictEntity toEntity(SysDictDto dto);
}