package com.github.platform.core.sys.adapter.api.convert;

import com.github.platform.core.sys.adapter.api.command.SysFlowRuleCmd;
import com.github.platform.core.sys.adapter.api.command.SysFlowRuleQuery;
import com.github.platform.core.sys.domain.context.SysFlowRuleContext;
import com.github.platform.core.sys.domain.context.SysFlowRuleQueryContext;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
/**
 * 状态机配置规则Controller到Application层的适配
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author yxkong
 * @date 2024-08-29 10:25:01.691
 * @version 1.0
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SysFlowRuleAdapterConvert {
    /**
    * 查询实体转查询上下文
    * @param query 查询实体
    * @return 查询上下文
    */
    SysFlowRuleQueryContext toQuery(SysFlowRuleQuery query);
    /**
    * 操作实体转操作上下文
    * @param cmd 操作实体
    * @return 操作上下文
    */
    SysFlowRuleContext toContext(SysFlowRuleCmd cmd);
}