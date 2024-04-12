package com.github.platform.core.kafka.configuration;

import com.github.platform.core.common.configuration.property.PlatformProperties;
import com.github.platform.core.common.constant.SpringBeanNameConstant;
import com.github.platform.core.common.constant.SpringBeanOrderConstant;
import com.github.platform.core.common.service.IDomainEventService;
import com.github.platform.core.kafka.service.impl.KafkaDomainEventServiceImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.kafka.core.KafkaTemplate;

/**
 * kafka自定义领域事件配置
 * @author: yxkong
 * @date: 2023/4/17 5:12 PM
 * @version: 1.0
 */
@Configuration
@ConditionalOnProperty(prefix = "spring.kafka.biz", name = "enabled", havingValue = "true")
public class KafkaDomainEventConfiguration {

    @Bean(SpringBeanNameConstant.DOMAIN_EVENT_SERVICE)
    @Order(SpringBeanOrderConstant.DOMAIN_SERVICE_KAFKA)
    @ConditionalOnMissingBean(IDomainEventService.class)
    public IDomainEventService kafkaDomainEventService(@Qualifier("bizKafkaTemplate") KafkaTemplate<Object, Object> kafkaTemplate, PlatformProperties properties, ApplicationContext applicationContext){
        return new KafkaDomainEventServiceImpl(kafkaTemplate,properties.getSystem().getServiceName(),applicationContext);
    }
}
