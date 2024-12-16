package com.github.platform.core.message.infra.convert;

import com.github.pagehelper.PageInfo;
import com.github.platform.core.message.domain.common.entity.SysNoticeEventLogBase;
import com.github.platform.core.message.domain.context.SysNoticeEventLogContext;
import com.github.platform.core.message.domain.context.SysNoticeEventLogQueryContext;
import com.github.platform.core.message.domain.dto.SysNoticeEventLogDto;
import com.github.platform.core.standard.entity.dto.PageBean;
import org.mapstruct.*;
import java.util.List;
/**
 * 通知事件日志基础设施层转换器
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author yxkong
 * @date 2024-12-04 13:36:01.514
 * @version 1.0
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SysNoticeEventLogInfraConvert {
    /**
    * 数据库实体列表转dto列表
    * @param list 数据库实体列表
    * @return dto列表
    */
    List<SysNoticeEventLogDto> toDtos(List<SysNoticeEventLogBase> list);
    /**
    * 数据库实体转dto
    * @param entity 数据库实体
    * @return dto
    */
    @Mappings({
        @Mapping(target = "strId", expression = "java(com.github.platform.core.common.utils.SignUtil.getStrId(entity.getId()))"),
    })
    SysNoticeEventLogDto toDto(SysNoticeEventLogBase entity);
    /**
    * 数据库分页转业务分页
    * @param pageInfo 数据库分页
    * @return 业务分页
    */
    @Mappings({
        @Mapping(target = "totalSize", source = "total"),
        @Mapping(target = "data", source = "list"),
    })
    PageBean<SysNoticeEventLogDto> ofPageBean(PageInfo<SysNoticeEventLogBase> pageInfo);

    /**
    * 查询上下文转数据库实体
    * @param context 数据库分页
    * @return 数据库实体
    */
    SysNoticeEventLogBase toSysNoticeEventLogBase(SysNoticeEventLogQueryContext context);
    /**
    * 实体上下文转数据库实体
    * @param context 实体上下文
    * @return 数据库实体
    */
    SysNoticeEventLogBase toSysNoticeEventLogBase(SysNoticeEventLogContext context);
    /**
    * dto转数据库实体
    * @param dto 传输实体
    * @return 数据库实体
    */
    SysNoticeEventLogBase toSysNoticeEventLogBase(SysNoticeEventLogDto dto);
}