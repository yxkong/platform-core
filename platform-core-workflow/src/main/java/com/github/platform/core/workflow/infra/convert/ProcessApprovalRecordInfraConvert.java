package com.github.platform.core.workflow.infra.convert;

import com.github.pagehelper.PageInfo;
import com.github.platform.core.workflow.domain.common.entity.ProcessApprovalRecordBase;
import com.github.platform.core.workflow.domain.context.ProcessApprovalRecordContext;
import com.github.platform.core.workflow.domain.context.ProcessApprovalRecordQueryContext;
import com.github.platform.core.workflow.domain.dto.ProcessApprovalRecordDto;
import com.github.platform.core.standard.entity.dto.PageBean;
import org.mapstruct.*;
import java.util.List;
/**
* 流程审批记录基础设施层转换器
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-11-09 11:30:36.194
* @version 1.0
*/
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProcessApprovalRecordInfraConvert {
    /**
    * 数据库实体列表转dto列表
    * @param list 数据库实体列表
    * @return dto列表
    */
    List<ProcessApprovalRecordDto> toDtos(List<ProcessApprovalRecordBase> list);
    /**
    * 数据库实体转dto
    * @param entity 数据库实体
    * @return dto
    */
    @Mappings({
            @Mapping(target = "duration", expression = "java(com.github.platform.core.standard.util.LocalDateTimeUtil.formatDurationAsString(entity.getStartTime(),entity.getCreateTime()))"),
            @Mapping(target = "id", expression = "java(null)"),
            @Mapping(target = "nodeType", constant = "userTask")
    })
    ProcessApprovalRecordDto toDto(ProcessApprovalRecordBase entity);
    /**
    * 数据库分页转业务分页
    * @param pageInfo 数据库分页
    * @return 业务分页
    */
    @Mappings({
        @Mapping(target = "totalSize", source = "total"),
        @Mapping(target = "data", source = "list"),
    })
    PageBean<ProcessApprovalRecordDto> ofPageBean(PageInfo<ProcessApprovalRecordBase> pageInfo);

    /**
    * 查询上下文转数据库实体
    * @param context 数据库分页
    * @return 数据库实体
    */
    ProcessApprovalRecordBase toProcessApprovalRecordBase(ProcessApprovalRecordQueryContext context);
    /**
    * 实体上下文转数据库实体
    * @param context 实体上下文
    * @return 数据库实体
    */
    ProcessApprovalRecordBase toProcessApprovalRecordBase(ProcessApprovalRecordContext context);
    /**
    * dto转数据库实体
    * @param dto 传输实体
    * @return 数据库实体
    */
    ProcessApprovalRecordBase toProcessApprovalRecordBase(ProcessApprovalRecordDto dto);

}