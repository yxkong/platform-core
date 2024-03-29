package com.github.platform.core.workflow.infra.convert;

import com.github.pagehelper.PageInfo;
import com.github.platform.core.workflow.domain.common.entity.ProcessConditionBase;
import com.github.platform.core.workflow.domain.context.ProcessConditionContext;
import com.github.platform.core.workflow.domain.context.ProcessConditionQueryContext;
import com.github.platform.core.workflow.domain.dto.ProcessConditionDto;
import com.github.platform.core.standard.entity.dto.PageBean;
import org.mapstruct.*;
import java.util.List;
/**
* 流程条件基础设施层转换器
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-11-30 18:17:25.794
* @version 1.0
*/
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProcessConditionInfraConvert {
    /**
    * 数据库实体列表转dto列表
    * @param list 数据库实体列表
    * @return dto列表
    */
    List<ProcessConditionDto> toDtos(List<ProcessConditionBase> list);
    /**
    * 数据库实体转dto
    * @param entity 数据库实体
    * @return dto
    */
    @Mappings({
            @Mapping(target = "strId", expression = "java(com.github.platform.core.common.utils.SignUtil.getStrId(entity.getId()))"),
            @Mapping(target = "id", expression = "java(null)"),
    })
    ProcessConditionDto toDto(ProcessConditionBase entity);
    /**
    * 数据库分页转业务分页
    * @param pageInfo 数据库分页
    * @return 业务分页
    */
    @Mappings({
        @Mapping(target = "totalSize", source = "total"),
        @Mapping(target = "data", source = "list"),
    })
    PageBean<ProcessConditionDto> ofPageBean(PageInfo<ProcessConditionBase> pageInfo);

    /**
    * 查询上下文转数据库实体
    * @param context 数据库分页
    * @return 数据库实体
    */
    ProcessConditionBase toProcessConditionBase(ProcessConditionQueryContext context);
    /**
    * 实体上下文转数据库实体
    * @param context 实体上下文
    * @return 数据库实体
    */
    ProcessConditionBase toProcessConditionBase(ProcessConditionContext context);
    /**
    * dto转数据库实体
    * @param dto 传输实体
    * @return 数据库实体
    */
    ProcessConditionBase toProcessConditionBase(ProcessConditionDto dto);
}