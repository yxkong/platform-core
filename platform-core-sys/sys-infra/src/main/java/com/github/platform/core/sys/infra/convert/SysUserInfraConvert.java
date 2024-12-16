package com.github.platform.core.sys.infra.convert;

import com.github.pagehelper.PageInfo;
import com.github.platform.core.auth.entity.LoginUserInfo;
import com.github.platform.core.standard.entity.dto.PageBean;
import com.github.platform.core.sys.domain.common.entity.SysUserBase;
import com.github.platform.core.sys.domain.common.entity.SysUserLogBase;
import com.github.platform.core.sys.domain.constant.UserLogBizTypeEnum;
import com.github.platform.core.sys.domain.context.*;
import com.github.platform.core.sys.domain.dto.SysUserDto;
import com.github.platform.core.sys.domain.model.sms.VerifyEntity;
import com.github.platform.core.sys.domain.model.user.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mappings;

import java.util.List;
/**
* 用户信息基础设施层转换器
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-15 10:55:06.651
* @version 1.0
*/
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SysUserInfraConvert {
    default Integer toUserLogBizTypeEnumInt(UserLogBizTypeEnum bizType) {
        return bizType.getType();
    }
    default UserLogBizTypeEnum toUserLogBizTypeEnum(Integer bizType) {
        return UserLogBizTypeEnum.of(bizType);
    }
    /**
    * 数据库实体列表转dto列表
    * @param list 数据库实体列表
    * @return dto列表
    */
    List<SysUserDto> toDtos(List<SysUserBase> list);
    /**
    * 数据库实体转dto
    * @param entity 数据库实体
    * @return dto
    */
    @Mappings({
            @Mapping(target = "strId", expression = "java(com.github.platform.core.common.utils.SignUtil.getStrId(entity.getId()))"),
            @Mapping(target = "id", expression = "java(null)")
    })
    SysUserDto toDto(SysUserBase entity);
    /**
    * 数据库分页转业务分页
    * @param pageInfo 数据库分页
    * @return 业务分页
    */
    @Mappings({
        @Mapping(target = "totalSize", source = "total"),
        @Mapping(target = "data", source = "list"),
    })
    PageBean<SysUserDto> ofPageBean(PageInfo<SysUserBase> pageInfo);

    /**
    * 查询上下文转数据库实体
    * @param context 数据库分页
    * @return 数据库实体
    */
    SysUserBase toSysUserBase(SysUserQueryContext context);
    /**
    * 实体上下文转数据库实体
    * @param context 实体上下文
    * @return 数据库实体
    */
    SysUserBase toSysUserBase(SysUserContext context);


    VerifyEntity verify(LoginContext context);
    @Mappings({
            @Mapping(target = "registerTime", source = "user.createTime"),
            @Mapping(target = "md5Pwd", source = "user.pwd"),
    })
    UserEntity target(SysUserBase user, String deptName);
    @Mappings({
    })
    LoginUserInfo target(UserEntity user);

    @Mappings({
            @Mapping(target = "pwd", source = "md5Pwd"),
    })
    SysUserBase of(RegisterContext context, String salt, String md5Pwd);

    SysUserBase of(SysUserContext context);



    @Mappings({
            @Mapping(target = "pwd", source = "md5Pwd"),
    })
    SysUserBase of(ResetPwdContext context, String salt, String md5Pwd);

    @Mappings({
            @Mapping(target = "pwd", source = "md5Pwd"),
    })
    SysUserBase of(ModifyPwdContext context, String salt, String md5Pwd);


    @Mappings({
            @Mapping(target = "createTime", expression = "java(com.github.platform.core.standard.util.LocalDateTimeUtil.dateTime())")
    })
    SysUserLogBase of(RegisterContext context, String creator, UserLogBizTypeEnum bizType);
}