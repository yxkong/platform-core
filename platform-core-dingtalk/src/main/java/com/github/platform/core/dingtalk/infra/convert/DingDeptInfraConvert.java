package com.github.platform.core.dingtalk.infra.convert;

import com.github.platform.core.dingtalk.domain.common.entity.DingDeptBase;
import com.github.platform.core.dingtalk.domain.context.DingDeptContext;
import com.github.platform.core.dingtalk.domain.dto.DingDeptDto;
import org.mapstruct.*;
import java.util.List;
/**
 * 钉钉部门基础设施层转换器
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author yxkong
 * @date 2024-01-18 15:49:32.694
 * @version 1.0
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DingDeptInfraConvert {
    /**
    * 数据库实体列表转dto列表
    * @param list 数据库实体列表
    * @return dto列表
    */
    List<DingDeptDto> toDtos(List<DingDeptBase> list);
    /**
    * 数据库实体转dto
    * @param entity 数据库实体
    * @return dto
    */
    DingDeptDto toDto(DingDeptBase entity);
    /**
    * 实体上下文转数据库实体
    * @param context 实体上下文
    * @return 数据库实体
    */
    DingDeptBase toDingDeptBase(DingDeptContext context);
}