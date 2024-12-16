package com.github.platform.core.monitor.infra.convert;

import com.github.pagehelper.PageInfo;
import com.github.platform.core.log.domain.entity.OptLogEntity;
import com.github.platform.core.monitor.domain.common.entity.SysOptLogBase;
import com.github.platform.core.monitor.domain.context.SysOptLogContext;
import com.github.platform.core.monitor.domain.context.SysOptLogQueryContext;
import com.github.platform.core.monitor.domain.dto.SysOptLogDto;
import com.github.platform.core.standard.entity.dto.PageBean;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mappings;

import java.util.List;
/**
* 操作日志基础设施层转换器
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-15 10:55:05.960
* @version 1.0
*/
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SysOptLogInfraConvert {
    /**
    * 数据库实体列表转dto列表
    * @param list 数据库实体列表
    * @return dto列表
    */
    List<SysOptLogDto> toDtos(List<SysOptLogBase> list);
    /**
    * 数据库实体转dto
    * @param entity 数据库实体
    * @return dto
    */
    SysOptLogDto toDto(SysOptLogBase entity);
    /**
    * 数据库分页转业务分页
    * @param pageInfo 数据库分页
    * @return 业务分页
    */
    @Mappings({
        @Mapping(target = "totalSize", source = "total"),
        @Mapping(target = "data", source = "list"),
    })
    PageBean<SysOptLogDto> ofPageBean(PageInfo<SysOptLogBase> pageInfo);

    /**
    * 查询上下文转数据库实体
    * @param context 数据库分页
    * @return 数据库实体
    */
    @Mappings({
            @Mapping(target = "createBy", source = "loginName"),
    })
    SysOptLogBase toSysOptLogBase(SysOptLogQueryContext context);
    SysOptLogBase toSysOptLogBase(OptLogEntity entity);
    /**
    * 实体上下文转数据库实体
    * @param context 实体上下文
    * @return 数据库实体
    */
    SysOptLogBase toSysOptLogBase(SysOptLogContext context);
}