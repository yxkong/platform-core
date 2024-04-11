package com.github.platform.core.workflow.infra.convert;

import com.github.pagehelper.PageInfo;
import com.github.platform.core.workflow.domain.common.entity.ProcessDefinitionBase;
import com.github.platform.core.workflow.domain.context.ProcessDefinitionContext;
import com.github.platform.core.workflow.domain.context.ProcessDefinitionQueryContext;
import com.github.platform.core.workflow.domain.dto.ProcessDefinitionDto;
import com.github.platform.core.standard.entity.dto.PageBean;
import org.mapstruct.*;
import java.util.List;
/**
* 流程管理基础设施层转换器
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-09-22 16:35:24.551
* @version 1.0
*/
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProcessManageInfraConvert {
    /**
    * 数据库实体列表转dto列表
    * @param list 数据库实体列表
    * @return dto列表
    */
    List<ProcessDefinitionDto> toDtos(List<ProcessDefinitionBase> list);
    /**
    * 数据库实体转dto
    * @param entity 数据库实体
    * @return dto
    */
    @Mappings({
            @Mapping(target = "strId", expression = "java(com.github.platform.core.common.utils.SignUtil.getStrId(entity.getId()))"),
            
    })
    ProcessDefinitionDto toDto(ProcessDefinitionBase entity);
    /**
    * 数据库分页转业务分页
    * @param pageInfo 数据库分页
    * @return 业务分页
    */
    @Mappings({
        @Mapping(target = "totalSize", source = "total"),
        @Mapping(target = "data", source = "list"),
    })
    PageBean<ProcessDefinitionDto> ofPageBean(PageInfo<ProcessDefinitionBase> pageInfo);

    /**
    * 查询上下文转数据库实体
    * @param context 数据库分页
    * @return 数据库实体
    */
    ProcessDefinitionBase toProcessDefinitionBase(ProcessDefinitionQueryContext context);
    /**
    * 实体上下文转数据库实体
    * @param context 实体上下文
    * @return 数据库实体
    */
    ProcessDefinitionBase toProcessDefinitionBase(ProcessDefinitionContext context);
    /**
    * dto转数据库实体
    * @param dto 传输实体
    * @return 数据库实体
    */
    ProcessDefinitionBase toProcessDefinitionBase(ProcessDefinitionDto dto);
    ProcessDefinitionContext toContext(ProcessDefinitionDto dto);
}