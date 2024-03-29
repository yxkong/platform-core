package com.github.platform.core.workflow.adapter.api.convert;

import com.github.platform.core.workflow.adapter.api.command.FlwRoleQuery;
import com.github.platform.core.workflow.adapter.api.command.FlwUserQuery;
import com.github.platform.core.workflow.domain.context.FlwRoleQueryContext;
import com.github.platform.core.workflow.domain.context.FlwUserQueryContext;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/**
 * 流程审批人转换器
 * @author: yxkong
 * @date: 2023/10/30 13:36
 * @version: 1.0
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProcessAssigneeConvert {

    FlwRoleQueryContext toQueryRole(FlwRoleQuery roleQuery);

    FlwUserQueryContext toQueryUsers(FlwUserQuery userQuery);
}
