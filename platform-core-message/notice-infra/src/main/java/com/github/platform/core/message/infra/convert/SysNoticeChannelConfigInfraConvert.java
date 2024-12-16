package com.github.platform.core.message.infra.convert;

import com.github.pagehelper.PageInfo;
import com.github.platform.core.message.domain.common.entity.SysNoticeChannelConfigBase;
import com.github.platform.core.message.domain.context.SysNoticeChannelConfigContext;
import com.github.platform.core.message.domain.context.SysNoticeChannelConfigQueryContext;
import com.github.platform.core.message.domain.dto.SysNoticeChannelConfigDto;
import com.github.platform.core.standard.entity.dto.PageBean;
import org.mapstruct.*;
import java.util.List;
/**
 * 通知通道配置基础设施层转换器
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author yxkong
 * @date 2024-12-04 13:32:28.892
 * @version 1.0
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SysNoticeChannelConfigInfraConvert {
    /**
    * 数据库实体列表转dto列表
    * @param list 数据库实体列表
    * @return dto列表
    */
    List<SysNoticeChannelConfigDto> toDtos(List<SysNoticeChannelConfigBase> list);
    /**
    * 数据库实体转dto
    * @param entity 数据库实体
    * @return dto
    */
    @Mappings({
        @Mapping(target = "strId", expression = "java(com.github.platform.core.common.utils.SignUtil.getStrId(entity.getId()))"),
    })
    SysNoticeChannelConfigDto toDto(SysNoticeChannelConfigBase entity);
    /**
    * 数据库分页转业务分页
    * @param pageInfo 数据库分页
    * @return 业务分页
    */
    @Mappings({
        @Mapping(target = "totalSize", source = "total"),
        @Mapping(target = "data", source = "list"),
    })
    PageBean<SysNoticeChannelConfigDto> ofPageBean(PageInfo<SysNoticeChannelConfigBase> pageInfo);

    /**
    * 查询上下文转数据库实体
    * @param context 数据库分页
    * @return 数据库实体
    */
    SysNoticeChannelConfigBase toSysNoticeChannelConfigBase(SysNoticeChannelConfigQueryContext context);
    /**
    * 实体上下文转数据库实体
    * @param context 实体上下文
    * @return 数据库实体
    */
    SysNoticeChannelConfigBase toSysNoticeChannelConfigBase(SysNoticeChannelConfigContext context);
    /**
    * dto转数据库实体
    * @param dto 传输实体
    * @return 数据库实体
    */
    SysNoticeChannelConfigBase toSysNoticeChannelConfigBase(SysNoticeChannelConfigDto dto);
}