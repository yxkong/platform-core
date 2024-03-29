package com.github.platform.core.loadbalancer.adapter.api.convert;

import com.github.platform.core.loadbalancer.adapter.api.command.GrayRuleCmd;
import com.github.platform.core.loadbalancer.adapter.api.command.GrayRuleQuery;
import com.github.platform.core.loadbalancer.domain.context.GrayRuleContext;
import com.github.platform.core.loadbalancer.domain.context.GrayRuleQueryContext;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/**
* 灰度规则表Controller到Application层的适配
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-14 15:54:07.988
* @version 1.0
*/
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface GrayRuleAdapterConvert {
    /**
    * 查询实体转查询上下文
    * @param query 查询实体
    * @return 查询上下文
    */
    GrayRuleQueryContext toQuery(GrayRuleQuery query);
    /**
    * 操作实体转操作上下文
    * @param cmd 操作实体
    * @return 操作上下文
    */
    GrayRuleContext toContext(GrayRuleCmd cmd);
}