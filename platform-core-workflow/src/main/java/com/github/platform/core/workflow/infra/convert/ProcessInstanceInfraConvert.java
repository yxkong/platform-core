package com.github.platform.core.workflow.infra.convert;

import com.github.pagehelper.PageInfo;
import com.github.platform.core.workflow.domain.common.entity.ProcessInstanceBase;
import com.github.platform.core.workflow.domain.context.ProcessInstanceContext;
import com.github.platform.core.workflow.domain.context.ProcessInstanceQueryContext;
import com.github.platform.core.workflow.domain.context.ProcessRunContext;
import com.github.platform.core.workflow.domain.dto.ProcessInstanceDto;
import com.github.platform.core.standard.entity.dto.PageBean;
import org.mapstruct.*;
import java.util.List;
/**
* 流程实例基础设施层转换器
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-09-22 16:35:27.395
* @version 1.0
*/
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProcessInstanceInfraConvert {
    /**
    * 数据库实体列表转dto列表
    * @param list 数据库实体列表
    * @return dto列表
    */
    List<ProcessInstanceDto> toDtos(List<ProcessInstanceBase> list);
    /**
    * 数据库实体转dto
    * @param entity 数据库实体
    * @return dto
    */
    @Mappings({
            @Mapping(target = "strId", expression = "java(com.github.platform.core.common.utils.SignUtil.getStrId(entity.getId()))"),
            
            @Mapping(target = "duration", expression = "java(com.github.platform.core.standard.util.LocalDateTimeUtil.formatCurrentDurationAsString(entity.getCreateTime()))")
    })
    ProcessInstanceDto toDto(ProcessInstanceBase entity);
    /**
    * 数据库分页转业务分页
    * @param pageInfo 数据库分页
    * @return 业务分页
    */
    @Mappings({
        @Mapping(target = "totalSize", source = "total"),
        @Mapping(target = "data", source = "list"),
    })
    PageBean<ProcessInstanceDto> ofPageBean(PageInfo<ProcessInstanceBase> pageInfo);

    /**
    * 查询上下文转数据库实体
    * @param context 数据库分页
    * @return 数据库实体
    */
    ProcessInstanceBase toFlwProcessInstanceBase(ProcessInstanceQueryContext context);
    /**
    * 实体上下文转数据库实体
    * @param context 实体上下文
    * @return 数据库实体
    */
    ProcessInstanceBase toFlwProcessInstanceBase(ProcessInstanceContext context);
    /**
    * dto转数据库实体
    * @param dto 传输实体
    * @return 数据库实体
    */
    ProcessInstanceBase toFlwProcessInstanceBase(ProcessInstanceDto dto);

    /**
     * 将启动流程上下文转成实例上下文
     * @param context
     * @param processNo
     * @param processVersion
     * @return
     */
    @Mappings({
            @Mapping(target = "createBy", source = "context.initiator"),
    })
    ProcessInstanceContext toContext(ProcessRunContext context, String instanceId, String instanceNo, String processNo, Integer processVersion);
}