package com.github.platform.core.loadbalancer.application.executor;

import com.github.platform.core.loadbalancer.domain.context.GrayRuleContext;
import com.github.platform.core.loadbalancer.domain.context.GrayRuleQueryContext;
import com.github.platform.core.loadbalancer.domain.dto.GrayRuleDto;
import com.github.platform.core.standard.entity.dto.PageBean;
import com.github.platform.core.standard.entity.dto.ResultBean;

/**
 * 灰度规则执行器
 * @author: yxkong
 * @date: 2023/12/27 13:34
 * @version: 1.0
 */
public interface IGrayRuleExecutor {
    PageBean<GrayRuleDto> query(GrayRuleQueryContext context);

    Long insert(GrayRuleContext context);

    void update(GrayRuleContext context);

    void delete(Long id);
}
