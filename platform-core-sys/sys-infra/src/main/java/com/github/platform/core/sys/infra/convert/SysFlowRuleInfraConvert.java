package com.github.platform.core.sys.infra.convert;

import com.github.pagehelper.PageInfo;
import com.github.platform.core.sys.domain.common.entity.SysFlowRuleBase;
import com.github.platform.core.sys.domain.context.SysFlowRuleContext;
import com.github.platform.core.sys.domain.context.SysFlowRuleQueryContext;
import com.github.platform.core.sys.domain.dto.SysFlowRuleDto;
import com.github.platform.core.standard.entity.dto.PageBean;
import org.mapstruct.*;
import java.util.List;
/**
 * 状态机配置规则基础设施层转换器
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author yxkong
 * @date 2024-08-29 10:25:01.691
 * @version 1.0
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SysFlowRuleInfraConvert {
    /**
    * 数据库实体列表转dto列表
    * @param list 数据库实体列表
    * @return dto列表
    */
    List<SysFlowRuleDto> toDtos(List<SysFlowRuleBase> list);
    /**
    * 数据库实体转dto
    * @param entity 数据库实体
    * @return dto
    */
    @Mappings({
        @Mapping(target = "strId", expression = "java(com.github.platform.core.common.utils.SignUtil.getStrId(entity.getId()))"),
    })
    SysFlowRuleDto toDto(SysFlowRuleBase entity);
    /**
    * 数据库分页转业务分页
    * @param pageInfo 数据库分页
    * @return 业务分页
    */
    @Mappings({
        @Mapping(target = "totalSize", source = "total"),
        @Mapping(target = "data", source = "list"),
    })
    PageBean<SysFlowRuleDto> ofPageBean(PageInfo<SysFlowRuleBase> pageInfo);

    /**
    * 查询上下文转数据库实体
    * @param context 数据库分页
    * @return 数据库实体
    */
    SysFlowRuleBase toSysFlowRuleBase(SysFlowRuleQueryContext context);
    /**
    * 实体上下文转数据库实体
    * @param context 实体上下文
    * @return 数据库实体
    */
    SysFlowRuleBase toSysFlowRuleBase(SysFlowRuleContext context);
    /**
    * dto转数据库实体
    * @param dto 传输实体
    * @return 数据库实体
    */
    SysFlowRuleBase toSysFlowRuleBase(SysFlowRuleDto dto);
}