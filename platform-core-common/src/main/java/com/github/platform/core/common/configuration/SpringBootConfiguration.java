package com.github.platform.core.common.configuration;

import com.github.platform.core.common.configuration.property.IdWorkerProperties;
import com.github.platform.core.common.configuration.property.PlatformProperties;
import com.github.platform.core.common.constant.PropertyConstant;
import com.github.platform.core.common.constant.SpringBeanNameConstant;
import com.github.platform.core.common.constant.SpringBeanOrderConstant;
import com.github.platform.core.common.service.DefaultDomainEventServiceImpl;
import com.github.platform.core.common.service.IDomainEventService;
import com.github.platform.core.common.service.IPublishService;
import com.github.platform.core.common.service.impl.EventPublishServiceImpl;
import com.github.platform.core.common.utils.IdWorker;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * springboot相关的扩展点
 *
 * @author: yxkong
 * @date: 2022/11/30 10:42 下午
 * @version: 1.0
 */
@Configuration
public class SpringBootConfiguration {
    @Resource
    private PlatformProperties properties;
//    @PostConstruct
//    void setDefaultTimezone() {
//        //东八区
//        TimeZone.setDefault(TimeZone.getTimeZone(properties.getDefaultZone()));
//    }
    /**
     * 的分布式id生成算法
     *
     * @param idWorkerProperties
     * @return
     * @throws Exception
     */
    @Bean
    @ConditionalOnProperty(name = PropertyConstant.CON_SNOWFLAKE_ENABLED, havingValue = "true")
    public IdWorker idWorker(IdWorkerProperties idWorkerProperties) throws Exception {
        List<String> cluster = idWorkerProperties.getCluster();
        int skip = idWorkerProperties.getSkip();
        if (cluster == null) {
            cluster = new ArrayList<>();
        }
        return new IdWorker(cluster, skip);
    }
    @Bean(SpringBeanNameConstant.DOMAIN_EVENT_SERVICE)
    @Order(SpringBeanOrderConstant.DOMAIN_SERVICE_DEFAULT)
    @ConditionalOnMissingBean(IDomainEventService.class)
    public IDomainEventService domainEventService(ApplicationContext applicationContext){
        return new DefaultDomainEventServiceImpl(applicationContext);
    }

    /**
     * 系统事件，发布服务，默认实现
     * @param applicationContext
     * @return
     */
    @Bean(SpringBeanNameConstant.PUBLISH_SERVICE)
    @Order(SpringBeanOrderConstant.PUBLISH_SERVICE_DEFAULT)
    @ConditionalOnMissingBean(IPublishService.class)
    public IPublishService publishService(ApplicationContext applicationContext){
        return new EventPublishServiceImpl(applicationContext);
    }

//    @Bean
//    public FastJsonConfig fastJsonConfig() {
//        //1.自定义配置...
//        FastJsonConfig config = new FastJsonConfig();
//        config.setDateFormat("yyyy-MM-dd HH:mm:ss");
//        //2.1配置序列化的行为
//        //JSONWriter.Feature.PrettyFormat:格式化输出
//        config.setWriterFeatures(JSONWriter.Feature.PrettyFormat);
//        //2.2配置反序列化的行为
//        config.setReaderFeatures(JSONReader.Feature.FieldBased, JSONReader.Feature.SupportArrayToBean);
//        return config;
//    }
}
