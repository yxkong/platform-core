package com.github.platform.core.loadbalancer.infra.service.impl;

import com.github.platform.core.loadbalancer.domain.entity.GrayRuleEntity;
import com.github.platform.core.loadbalancer.domain.gateway.IGrayRuleGateway;
import com.github.platform.core.loadbalancer.gateway.IGrayRuleQueryGateway;


/**
 *
 * @author: yxkong
 * @date: 2023/4/14 5:49 PM
 * @version: 1.0
 */
public class DbGrayRuleServiceImpl implements IGrayRuleQueryGateway {

    private IGrayRuleGateway grayRuleGateway;
    public DbGrayRuleServiceImpl(IGrayRuleGateway grayRuleGateway) {
        this.grayRuleGateway = grayRuleGateway;
    }

    @Override
    public GrayRuleEntity findOne() {
        return grayRuleGateway.findOne();
    }
}
