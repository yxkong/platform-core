package com.github.platform.core.sys.infra.convert;

import com.github.pagehelper.PageInfo;
import com.github.platform.core.auth.entity.TokenCacheEntity;
import com.github.platform.core.standard.entity.dto.PageBean;
import com.github.platform.core.sys.domain.common.entity.SysTokenCacheBase;
import com.github.platform.core.sys.domain.context.SysTokenCacheContext;
import com.github.platform.core.sys.domain.context.SysTokenCacheQueryContext;
import com.github.platform.core.sys.domain.dto.SysTokenCacheDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mappings;

import java.util.List;
/**
 * token缓存基础设施层转换器
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author yxkong
 * @date 2024-04-28 16:25:03.690
 * @version 1.0
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SysTokenCacheInfraConvert {
    /**
    * 数据库实体列表转dto列表
    * @param list 数据库实体列表
    * @return dto列表
    */
    List<SysTokenCacheDto> toDtos(List<SysTokenCacheBase> list);
    /**
    * 数据库实体转dto
    * @param entity 数据库实体
    * @return dto
    */
    @Mappings({
            @Mapping(target = "strId", expression = "java(com.github.platform.core.common.utils.SignUtil.getStrId(entity.getId()))"),
    })
    SysTokenCacheDto toDto(SysTokenCacheBase entity);

    @Mappings({
            @Mapping(target = "strId", expression = "java(com.github.platform.core.common.utils.SignUtil.getStrId(entity.getId()))"),
    })
    TokenCacheEntity toEntity(SysTokenCacheDto entity);
    List<TokenCacheEntity> toEntityList(List<SysTokenCacheDto> list);
    /**
    * 数据库分页转业务分页
    * @param pageInfo 数据库分页
    * @return 业务分页
    */
    @Mappings({
        @Mapping(target = "totalSize", source = "total"),
        @Mapping(target = "data", source = "list"),
    })
    PageBean<SysTokenCacheDto> ofPageBean(PageInfo<SysTokenCacheBase> pageInfo);

    /**
    * 查询上下文转数据库实体
    * @param context 数据库分页
    * @return 数据库实体
    */
    SysTokenCacheBase toSysTokenCacheBase(SysTokenCacheQueryContext context);
    /**
    * 实体上下文转数据库实体
    * @param context 实体上下文
    * @return 数据库实体
    */
    SysTokenCacheBase toSysTokenCacheBase(SysTokenCacheContext context);
    /**
    * dto转数据库实体
    * @param dto 传输实体
    * @return 数据库实体
    */
    SysTokenCacheBase toSysTokenCacheBase(SysTokenCacheDto dto);
}