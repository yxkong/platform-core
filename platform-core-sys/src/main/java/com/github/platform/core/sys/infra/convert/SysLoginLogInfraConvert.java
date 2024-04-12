package com.github.platform.core.sys.infra.convert;

import com.github.pagehelper.PageInfo;
import com.github.platform.core.auth.entity.LoginUserInfo;
import com.github.platform.core.standard.entity.dto.PageBean;
import com.github.platform.core.sys.domain.common.entity.SysLoginLogBase;
import com.github.platform.core.sys.domain.context.SysLoginLogContext;
import com.github.platform.core.sys.domain.context.SysLoginLogQueryContext;
import com.github.platform.core.sys.domain.dto.SysLoginLogDto;
import com.github.platform.core.sys.domain.model.event.LoginEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mappings;

import java.util.List;
/**
* 登录日志基础设施层转换器
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-15 10:55:05.685
* @version 1.0
*/
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SysLoginLogInfraConvert {
    /**
    * 数据库实体列表转dto列表
    * @param list 数据库实体列表
    * @return dto列表
    */
    List<SysLoginLogDto> toDtos(List<SysLoginLogBase> list);
    /**
    * 数据库实体转dto
    * @param entity 数据库实体
    * @return dto
    */
    @Mappings({
            @Mapping(target = "strId", expression = "java(com.github.platform.core.common.utils.SignUtil.getStrId(entity.getId()))"),
            
    })
    SysLoginLogDto toDto(SysLoginLogBase entity);
    /**
    * 数据库分页转业务分页
    * @param pageInfo 数据库分页
    * @return 业务分页
    */
    @Mappings({
        @Mapping(target = "totalSize", source = "total"),
        @Mapping(target = "data", source = "list"),
    })
    PageBean<SysLoginLogDto> ofPageBean(PageInfo<SysLoginLogBase> pageInfo);

    /**
    * 查询上下文转数据库实体
    * @param context 数据库分页
    * @return 数据库实体
    */
    SysLoginLogBase toSysLoginLogBase(SysLoginLogQueryContext context);
    /**
    * 实体上下文转数据库实体
    * @param context 实体上下文
    * @return 数据库实体
    */
    SysLoginLogBase toSysLoginLogBase(SysLoginLogContext context);
    SysLoginLogBase toSysLoginLogBase(LoginUserInfo context);
    @Mappings({
            @Mapping(target = "createTime", source = "loginTime"),
            @Mapping(target = "createBy", source = "loginName"),
    })
    SysLoginLogBase toSysLoginLogBase(LoginEntity login);
}