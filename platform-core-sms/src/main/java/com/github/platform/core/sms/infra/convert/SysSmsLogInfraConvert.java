package com.github.platform.core.sms.infra.convert;

import com.github.pagehelper.PageInfo;
import com.github.platform.core.sms.domain.common.entity.SysSmsLogBase;
import com.github.platform.core.sms.domain.context.SysSmsLogContext;
import com.github.platform.core.sms.domain.context.SmsLogQueryContext;
import com.github.platform.core.sms.domain.dto.SysSmsLogDto;
import com.github.platform.core.standard.entity.dto.PageBean;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mappings;

import java.util.List;

/**
* 短信日志基础设施层转换器
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @time 2023-07-04 10:23:45.615
* @version 1.0
*/
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SysSmsLogInfraConvert {
    /**
     * 数据库实体列表转dto列表
     * @param list 数据库实体列表
     * @return dto列表
     */
    List<SysSmsLogDto> toDtos(List<SysSmsLogBase> list);
    /**
     * 数据库实体转dto
     * @param entity 数据库实体
     * @return dto
     */
    @Mappings({
            @Mapping(target = "strId", expression = "java(com.github.platform.core.common.utils.SignUtil.getStrId(entity.getId()))"),
            @Mapping(target = "id", expression = "java(null)"),
    })
    SysSmsLogDto toDto(SysSmsLogBase entity);
    /**
     * 数据库分页转业务分页
     * @param pageInfo 数据库分页
     * @return 业务分页
     */
    @Mappings({
            @Mapping(target = "totalSize", source = "total"),
            @Mapping(target = "data", source = "list"),
    })
    PageBean<SysSmsLogDto> ofPageBean(PageInfo<SysSmsLogBase> pageInfo);

    /**
     * 查询上下文转数据库实体
     * @param context 数据库分页
     * @return 数据库实体
     */
    SysSmsLogBase toSmsLogBase(SmsLogQueryContext context);
    /**
     * 实体上下文转数据库实体
     * @param context 实体上下文
     * @return 数据库实体
     */
    SysSmsLogBase toSmsLogBase(SysSmsLogContext context);
}