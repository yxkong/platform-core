package com.github.platform.core.loadbalancer.infra.configuration;

import com.github.platform.core.common.constant.SpringBeanNameConstant;
import com.github.platform.core.common.constant.SpringBeanOrderConstant;
import com.github.platform.core.loadbalancer.domain.gateway.IGrayRuleGateway;
import com.github.platform.core.loadbalancer.gateway.IGrayRuleQueryGateway;
import com.github.platform.core.loadbalancer.infra.service.impl.DbGrayRuleServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * 灰度规则管理
 * @author: yxkong
 * @date: 2023/4/14 6:26 PM
 * @version: 1.0
 */
@Configuration
public class GrayRuleConfiguration {

    /**
     * 默认灰度规则实现
     * @return
     */
    @Bean(name = SpringBeanNameConstant.GRAY_RULE_GATEWAY)
    @Order(SpringBeanOrderConstant.GRAY_RULE_DB)
    @ConditionalOnMissingBean(name = SpringBeanNameConstant.GRAY_RULE_GATEWAY)
    public IGrayRuleQueryGateway redisGrayRule(IGrayRuleGateway grayRuleGateway){
        return new DbGrayRuleServiceImpl(grayRuleGateway);
    }
}
