package com.github.platform.core.workflow.adapter.api.convert;

import com.github.platform.core.workflow.adapter.api.command.TaskSubmitCmd;
import com.github.platform.core.workflow.domain.constant.ProcessOptTypeEnum;
import com.github.platform.core.workflow.domain.context.ApprovalContext;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mappings;

/**
 * 流程审批adapter层转换器
 * @author: yxkong
 * @date: 2023/11/10 14:04
 * @version: 1.0
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProcessApprovalAdapterConvert {
    default ProcessOptTypeEnum toOptTypeEnum(String optType) {
        return ProcessOptTypeEnum.of(optType);
    }
    @Mappings({
            @Mapping(target = "optTypeEnum", source = "cmd.optType"),
    })
    ApprovalContext toContext(TaskSubmitCmd cmd);
}
