package com.github.platform.core.sys.infra.convert;

import com.github.platform.core.sys.domain.common.entity.SysRoleMenuBase;
import com.github.platform.core.sys.domain.dto.SysRoleMenuDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mappings;

import java.util.List;

/**
 * 角色菜单转换器
 * @Author: yxkong
 * @Date: 2024/11/25
 * @version: 1.0
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SysRoleMenuInfraConvert {
        /**
    * 数据库实体列表转dto列表
    * @param list 数据库实体列表
    * @return dto列表
    */
    List<SysRoleMenuDto> toDtos(List<SysRoleMenuBase> list);
    /**
    * 数据库实体转dto
    * @param entity 数据库实体
    * @return dto
    */
    @Mappings({
            @Mapping(target = "strId", expression = "java(com.github.platform.core.common.utils.SignUtil.getStrId(entity.getId()))"),
    })
    SysRoleMenuDto toDto(SysRoleMenuBase entity);
}
