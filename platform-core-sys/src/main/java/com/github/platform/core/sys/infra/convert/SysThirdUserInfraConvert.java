package com.github.platform.core.sys.infra.convert;

import com.github.pagehelper.PageInfo;
import com.github.platform.core.standard.entity.dto.PageBean;
import com.github.platform.core.sys.domain.common.entity.SysThirdUserBase;
import com.github.platform.core.sys.domain.context.SysThirdUserContext;
import com.github.platform.core.sys.domain.context.SysThirdUserQueryContext;
import com.github.platform.core.sys.domain.dto.SysThirdUserDto;
import com.github.platform.core.sys.domain.model.user.ThirdUserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mappings;

import java.util.List;
/**
* 三方用户基础设施层转换器
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-15 10:55:06.505
* @version 1.0
*/
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SysThirdUserInfraConvert {
    /**
    * 数据库实体列表转dto列表
    * @param list 数据库实体列表
    * @return dto列表
    */
    List<SysThirdUserDto> toDtos(List<SysThirdUserBase> list);
    /**
    * 数据库实体转dto
    * @param entity 数据库实体
    * @return dto
    */
    @Mappings({
            @Mapping(target = "strUserId", expression = "java(com.github.platform.core.common.utils.SignUtil.getStrId(entity.getUserId()))"),
            @Mapping(target = "strId", expression = "java(com.github.platform.core.common.utils.SignUtil.getStrId(entity.getId()))"),
            @Mapping(target = "userId", expression = "java(null)")
    })
    SysThirdUserDto toDto(SysThirdUserBase entity);
    /**
    * 数据库分页转业务分页
    * @param pageInfo 数据库分页
    * @return 业务分页
    */
    @Mappings({
            @Mapping(target = "totalSize", source = "total"),
            @Mapping(target = "data", source = "list"),
    })
    PageBean<SysThirdUserDto> ofPageBean(PageInfo<SysThirdUserBase> pageInfo);

    /**
    * 查询上下文转数据库实体
    * @param context 数据库分页
    * @return 数据库实体
    */
    SysThirdUserBase toSysThirdUserBase(SysThirdUserQueryContext context);
    /**
    * 实体上下文转数据库实体
    * @param context 实体上下文
    * @return 数据库实体
    */
    SysThirdUserBase toSysThirdUserBase(SysThirdUserContext context);
    @Mappings({
            @Mapping(target = "mobile", source = "thirdUser.mobile"),
            @Mapping(target = "createBy", source = "thirdUser.loginName"),
    })
    SysThirdUserBase toSysThirdUserBase(ThirdUserEntity thirdUser,Long userId);
}