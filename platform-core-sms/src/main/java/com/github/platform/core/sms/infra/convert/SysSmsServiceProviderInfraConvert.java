package com.github.platform.core.sms.infra.convert;

import com.github.pagehelper.PageInfo;
import com.github.platform.core.sms.domain.common.entity.SysSmsServiceProviderBase;
import com.github.platform.core.sms.domain.context.SysSmsServiceProviderContext;
import com.github.platform.core.sms.domain.context.SysSmsServiceProviderQueryContext;
import com.github.platform.core.sms.domain.dto.SysSmsServiceProviderDto;
import com.github.platform.core.standard.entity.dto.PageBean;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mappings;

import java.util.List;
/**
* 服务商基础设施层转换器
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-14 17:39:29.748
* @version 1.0
*/
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SysSmsServiceProviderInfraConvert {
    /**
    * 数据库实体列表转dto列表
    * @param list 数据库实体列表
    * @return dto列表
    */
    List<SysSmsServiceProviderDto> toDtos(List<SysSmsServiceProviderBase> list);
    /**
    * 数据库实体转dto
    * @param entity 数据库实体
    * @return dto
    */
    @Mappings({
            @Mapping(target = "strId", expression = "java(com.github.platform.core.common.utils.SignUtil.getStrId(entity.getId()))"),
    })
    SysSmsServiceProviderDto toDto(SysSmsServiceProviderBase entity);
    /**
    * 数据库分页转业务分页
    * @param pageInfo 数据库分页
    * @return 业务分页
    */
    @Mappings({
        @Mapping(target = "totalSize", source = "total"),
        @Mapping(target = "data", source = "list"),
    })
    PageBean<SysSmsServiceProviderDto> ofPageBean(PageInfo<SysSmsServiceProviderBase> pageInfo);

    /**
    * 查询上下文转数据库实体
    * @param context 数据库分页
    * @return 数据库实体
    */
    SysSmsServiceProviderBase toSmsServiceProviderBase(SysSmsServiceProviderQueryContext context);
    /**
    * 实体上下文转数据库实体
    * @param context 实体上下文
    * @return 数据库实体
    */
    SysSmsServiceProviderBase toSmsServiceProviderBase(SysSmsServiceProviderContext context);
}