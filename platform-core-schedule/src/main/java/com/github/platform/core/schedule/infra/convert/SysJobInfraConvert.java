package com.github.platform.core.schedule.infra.convert;

import com.github.pagehelper.PageInfo;
import com.github.platform.core.schedule.domain.common.entity.SysJobBase;
import com.github.platform.core.schedule.domain.context.SysJobContext;
import com.github.platform.core.schedule.domain.context.SysJobQueryContext;
import com.github.platform.core.schedule.domain.dto.SysJobDto;
import com.github.platform.core.standard.entity.dto.PageBean;
import org.mapstruct.*;
import java.util.List;
/**
* 任务管理基础设施层转换器
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-09-05 11:37:59.627
* @version 1.0
*/
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SysJobInfraConvert {
    /**
    * 数据库实体列表转dto列表
    * @param list 数据库实体列表
    * @return dto列表
    */
    List<SysJobDto> toDtos(List<SysJobBase> list);
    /**
    * 数据库实体转dto
    * @param entity 数据库实体
    * @return dto
    */
    @Mappings({
            @Mapping(target = "strId", expression = "java(com.github.platform.core.common.utils.SignUtil.getStrId(entity.getId()))"),
            @Mapping(target = "id", expression = "java(null)"),
    })
    SysJobDto toDto(SysJobBase entity);
    /**
    * 数据库分页转业务分页
    * @param pageInfo 数据库分页
    * @return 业务分页
    */
    @Mappings({
        @Mapping(target = "totalSize", source = "total"),
        @Mapping(target = "data", source = "list"),
    })
    PageBean<SysJobDto> ofPageBean(PageInfo<SysJobBase> pageInfo);

    /**
    * 查询上下文转数据库实体
    * @param context 数据库分页
    * @return 数据库实体
    */
    SysJobBase toSysJobBase(SysJobQueryContext context);

    /**
     * 传输实体转数据库实体
     * @param dto 传输实体
     * @return 数据库实体
     */
    SysJobBase toSysJobBase(SysJobDto dto);
    /**
    * 实体上下文转数据库实体
    * @param context 实体上下文
    * @return 数据库实体
    */
    SysJobBase toSysJobBase(SysJobContext context);
}