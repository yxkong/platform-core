package com.github.platform.core.loadbalancer.application.executor.impl;

import com.github.platform.core.common.service.BaseExecutor;
import com.github.platform.core.loadbalancer.application.executor.IGrayRuleExecutor;
import com.github.platform.core.loadbalancer.domain.context.GrayRuleContext;
import com.github.platform.core.loadbalancer.domain.context.GrayRuleQueryContext;
import com.github.platform.core.loadbalancer.domain.dto.GrayRuleDto;
import com.github.platform.core.loadbalancer.domain.gateway.IGrayRuleGateway;
import com.github.platform.core.standard.entity.dto.PageBean;
import com.github.platform.core.standard.entity.dto.ResultBean;
import com.github.platform.core.standard.util.ResultBeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 灰度规则执行器
 * @author: yxkong
 * @date: 2023/4/18 3:08 PM
 * @version: 1.0
 */
@Service
@Slf4j
public class GrayRuleExecutorImpl extends BaseExecutor implements IGrayRuleExecutor {

    @Resource
    private IGrayRuleGateway gateway;
    @Override
    public PageBean<GrayRuleDto> query(GrayRuleQueryContext context) {
        return gateway.query(context);
    }

    @Override
    public Long insert(GrayRuleContext context) {
        context.setId(null);
        Pair<Boolean,String> pair = gateway.insert(context);
        return context.getId();
    }
    @Override
    public void update(GrayRuleContext context) {
        Pair<Boolean,String> pair = gateway.update(context);
    }

    @Override
    public void delete(Long id) {
        Pair<Boolean,String> pair = gateway.delete(id);
    }
}
