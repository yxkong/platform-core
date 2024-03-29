package com.github.platform.core.loadbalancer.gateway;

import com.github.platform.core.loadbalancer.domain.entity.GrayRuleEntity;

/**
 * 灰度查询网关
 * @author: yxkong
 * @date: 2023/4/14 5:13 PM
 * @version: 1.0
 */
public interface IGrayRuleQueryGateway {

    /**
     * 查询灰度规则
     * @return
     */
    GrayRuleEntity findOne();
}
