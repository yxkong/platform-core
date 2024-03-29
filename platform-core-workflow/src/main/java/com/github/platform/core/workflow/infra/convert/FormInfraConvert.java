package com.github.platform.core.workflow.infra.convert;

import com.github.pagehelper.PageInfo;
import com.github.platform.core.workflow.domain.common.entity.FormBase;
import com.github.platform.core.workflow.domain.context.FormContext;
import com.github.platform.core.workflow.domain.context.FormQueryContext;
import com.github.platform.core.workflow.domain.dto.FormDetailDto;
import com.github.platform.core.standard.entity.dto.PageBean;
import com.github.platform.core.workflow.domain.dto.FormDto;
import org.mapstruct.*;
import java.util.List;
/**
* 表单配置基础设施层转换器
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-11-17 18:23:18.130
* @version 1.0
*/
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface FormInfraConvert {
    /**
    * 数据库实体列表转dto列表
    * @param list 数据库实体列表
    * @return dto列表
    */
    List<FormDto> toDtos(List<FormBase> list);
    /**
    * 数据库实体转dto
    * @param entity 数据库实体
    * @return dto
    */
    @Mappings({
            @Mapping(target = "strId", expression = "java(com.github.platform.core.common.utils.SignUtil.getStrId(entity.getId()))"),
            @Mapping(target = "id", expression = "java(null)"),
    })
    FormDto toDto(FormBase entity);
    /**
    * 数据库分页转业务分页
    * @param pageInfo 数据库分页
    * @return 业务分页
    */
    @Mappings({
        @Mapping(target = "totalSize", source = "total"),
        @Mapping(target = "data", source = "list"),
    })
    PageBean<FormDto> ofPageBean(PageInfo<FormBase> pageInfo);

    /**
    * 查询上下文转数据库实体
    * @param context 数据库分页
    * @return 数据库实体
    */
    FormBase toFormBase(FormQueryContext context);
    /**
    * 实体上下文转数据库实体
    * @param context 实体上下文
    * @return 数据库实体
    */
    FormBase toFormBase(FormContext context);
    /**
    * dto转数据库实体
    * @param dto 传输实体
    * @return 数据库实体
    */
    FormBase toFormBase(FormDto dto);
}