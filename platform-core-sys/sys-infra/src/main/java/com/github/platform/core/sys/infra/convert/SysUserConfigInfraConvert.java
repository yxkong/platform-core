package com.github.platform.core.sys.infra.convert;

import com.github.platform.core.sys.domain.common.entity.SysUserConfigBase;
import com.github.platform.core.sys.domain.context.SysUserConfigContext;
import com.github.platform.core.sys.domain.dto.SysUserConfigDto;
import org.mapstruct.*;
import java.util.List;
/**
 * 用户配置基础设施层转换器
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author yxkong
 * @date 2024-03-27 16:10:38.663
 * @version 1.0
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SysUserConfigInfraConvert {
    /**
    * 数据库实体列表转dto列表
    * @param list 数据库实体列表
    * @return dto列表
    */
    List<SysUserConfigDto> toDtos(List<SysUserConfigBase> list);
    /**
    * 数据库实体转dto
    * @param entity 数据库实体
    * @return dto
    */
    @Mappings({
        @Mapping(target = "strId", expression = "java(com.github.platform.core.common.utils.SignUtil.getStrId(entity.getId()))"),
    })
    SysUserConfigDto toDto(SysUserConfigBase entity);
    /**
    * 实体上下文转数据库实体
    * @param context 实体上下文
    * @return 数据库实体
    */
    SysUserConfigBase toSysUserConfigBase(SysUserConfigContext context);
}