package com.github.platform.core.schedule.infra.convert;

import com.github.pagehelper.PageInfo;
import com.github.platform.core.schedule.domain.common.entity.SysJobLogBase;
import com.github.platform.core.schedule.domain.context.SysJobLogContext;
import com.github.platform.core.schedule.domain.context.SysJobLogQueryContext;
import com.github.platform.core.schedule.domain.dto.SysJobLogDto;
import com.github.platform.core.standard.entity.dto.PageBean;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mappings;

import java.util.List;
/**
* 任务执行日志基础设施层转换器
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-09-06 18:53:10.711
* @version 1.0
*/
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SysJobLogInfraConvert {
    /**
    * 数据库实体列表转dto列表
    * @param list 数据库实体列表
    * @return dto列表
    */
    List<SysJobLogDto> toDtos(List<SysJobLogBase> list);
    /**
    * 数据库实体转dto
    * @param entity 数据库实体
    * @return dto
    */
    @Mappings({
            @Mapping(target = "strId", expression = "java(com.github.platform.core.common.utils.SignUtil.getStrId(entity.getId()))"),
    })
    SysJobLogDto toDto(SysJobLogBase entity);
    /**
    * 数据库分页转业务分页
    * @param pageInfo 数据库分页
    * @return 业务分页
    */
    @Mappings({
        @Mapping(target = "totalSize", source = "total"),
        @Mapping(target = "data", source = "list"),
    })
    PageBean<SysJobLogDto> ofPageBean(PageInfo<SysJobLogBase> pageInfo);

    /**
    * 查询上下文转数据库实体
    * @param context 数据库分页
    * @return 数据库实体
    */
    SysJobLogBase toSysJobLogBase(SysJobLogQueryContext context);
    /**
    * 实体上下文转数据库实体
    * @param context 实体上下文
    * @return 数据库实体
    */
    SysJobLogBase toSysJobLogBase(SysJobLogContext context);
    /**
    * dto转数据库实体
    * @param dto 传输实体
    * @return 数据库实体
    */
    SysJobLogBase toSysJobLogBase(SysJobLogDto dto);
}