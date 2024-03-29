package com.github.platform.core.sys.adapter.api.convert;

import com.github.platform.core.sys.adapter.api.command.thirduser.SysThirdUserCmd;
import com.github.platform.core.sys.adapter.api.command.thirduser.SysThirdUserQuery;
import com.github.platform.core.sys.adapter.api.command.user.ThirdApproveCmd;
import com.github.platform.core.sys.domain.context.SysThirdUserContext;
import com.github.platform.core.sys.domain.context.SysThirdUserQueryContext;
import com.github.platform.core.sys.domain.context.ThirdApproveContext;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mappings;

/**
* 三方用户Controller到Application层的适配
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-15 10:55:06.505
* @version 1.0
*/
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SysThirdUserAdapterConvert {
    /**
    * 查询实体转查询上下文
    * @param query 查询实体
    * @return 查询上下文
    */
    SysThirdUserQueryContext toQuery(SysThirdUserQuery query);
    /**
    * 操作实体转操作上下文
    * @param cmd 操作实体
    * @return 操作上下文
    */
    @Mappings({
            @Mapping(target = "userId", expression = "java(com.github.platform.core.common.utils.SignUtil.getLongId(cmd.getUserIdStr()))"),
    })
    SysThirdUserContext toContext(SysThirdUserCmd cmd);
    @Mappings({
            @Mapping(target = "userId", expression = "java(com.github.platform.core.common.utils.SignUtil.getLongId(cmd.getUserId()))"),
    })
    ThirdApproveContext toApprove(ThirdApproveCmd cmd);
}