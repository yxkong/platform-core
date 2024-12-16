package com.github.platform.core.sys.infra.convert;

import com.github.pagehelper.PageInfo;
import com.github.platform.core.cache.domain.entity.DictEntity;
import com.github.platform.core.standard.entity.dto.PageBean;
import com.github.platform.core.sys.domain.common.entity.SysDictBase;
import com.github.platform.core.sys.domain.context.SysDictContext;
import com.github.platform.core.sys.domain.context.SysDictQueryContext;
import com.github.platform.core.sys.domain.dto.SysDictDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mappings;

import java.util.List;
/**
* 字典数据基础设施层转换器
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-15 10:55:05.400
* @version 1.0
*/
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SysDictInfraConvert {
    /**
     * SysDictBase 到缓存实体
     * @param list
     * @return
     */
    List<DictEntity> toDictEntity(List<SysDictBase> list);
    DictEntity toDictEntity(SysDictDto dto);
    List<DictEntity> target(List<SysDictDto> list);
    /**
    * 数据库实体列表转dto列表
    * @param list 数据库实体列表
    * @return dto列表
    */
    List<SysDictDto> toDtos(List<SysDictBase> list);
    List<SysDictDto> toDtos1(List<SysDictDto> list);
    /**
    * 数据库实体转dto
    * @param entity 数据库实体
    * @return dto
    */
    @Mappings({
            @Mapping(target = "strId", expression = "java(com.github.platform.core.common.utils.SignUtil.getStrId(entity.getId()))"),
            
    })
    SysDictDto toDto(SysDictBase entity);
    @Mappings({
            @Mapping(target = "strId", expression = "java(com.github.platform.core.common.utils.SignUtil.getStrId(entity.getId()))"),
    })
    SysDictDto toDto1(SysDictDto entity);
    /**
    * 数据库分页转业务分页
    * @param pageInfo 数据库分页
    * @return 业务分页
    */
    @Mappings({
        @Mapping(target = "totalSize", source = "total"),
        @Mapping(target = "data", source = "list"),
    })
    PageBean<SysDictDto> ofPageBean(PageInfo<SysDictBase> pageInfo);

    @Mappings({
            @Mapping(target = "totalSize", source = "total"),
            @Mapping(target = "data", source = "list"),
    })
    PageBean<SysDictDto> ofPageBeanDto(PageInfo<SysDictDto> pageInfo);

    /**
    * 查询上下文转数据库实体
    * @param context 数据库分页
    * @return 数据库实体
    */
    SysDictBase toSysDictBase(SysDictQueryContext context);
    /**
    * 实体上下文转数据库实体
    * @param context 实体上下文
    * @return 数据库实体
    */
    SysDictBase toSysDictBase(SysDictContext context);

    DictEntity of(SysDictBase dictDO, String charType, String name);
}