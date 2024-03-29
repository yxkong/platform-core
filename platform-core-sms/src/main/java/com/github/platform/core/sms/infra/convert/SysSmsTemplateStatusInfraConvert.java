package com.github.platform.core.sms.infra.convert;

import com.github.pagehelper.PageInfo;
import com.github.platform.core.sms.domain.common.entity.SysSmsTemplateStatusBase;
import com.github.platform.core.sms.domain.context.SysSmsTemplateStatusContext;
import com.github.platform.core.sms.domain.context.SysSmsTemplateStatusQueryContext;
import com.github.platform.core.sms.domain.dto.SysSmsTemplateStatusDto;
import com.github.platform.core.standard.entity.dto.PageBean;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mappings;

import java.util.List;
/**
* 模板厂商基础设施层转换器
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-14 17:39:30.326
* @version 1.0
*/
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SysSmsTemplateStatusInfraConvert {
    /**
    * 数据库实体列表转dto列表
    * @param list 数据库实体列表
    * @return dto列表
    */
    List<SysSmsTemplateStatusDto> toDtos(List<SysSmsTemplateStatusBase> list);
    /**
    * 数据库实体转dto
    * @param entity 数据库实体
    * @return dto
    */
    @Mappings({
            @Mapping(target = "strId", expression = "java(com.github.platform.core.common.utils.SignUtil.getStrId(entity.getId()))"),
            @Mapping(target = "id", expression = "java(null)"),
    })
    SysSmsTemplateStatusDto toDto(SysSmsTemplateStatusBase entity);
    /**
    * 数据库分页转业务分页
    * @param pageInfo 数据库分页
    * @return 业务分页
    */
    @Mappings({
        @Mapping(target = "totalSize", source = "total"),
        @Mapping(target = "data", source = "list"),
    })
    PageBean<SysSmsTemplateStatusDto> ofPageBean(PageInfo<SysSmsTemplateStatusBase> pageInfo);

    /**
    * 查询上下文转数据库实体
    * @param context 数据库分页
    * @return 数据库实体
    */
    SysSmsTemplateStatusBase toSmsTemplateStatusBase(SysSmsTemplateStatusQueryContext context);
    /**
    * 实体上下文转数据库实体
    * @param context 实体上下文
    * @return 数据库实体
    */
    SysSmsTemplateStatusBase toSmsTemplateStatusBase(SysSmsTemplateStatusContext context);
}