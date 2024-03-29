package com.github.platform.core.cloud.configuration;

import com.alibaba.cloud.nacos.NacosConfigManager;
import com.github.platform.core.cloud.properties.NacosEnvProperties;
import com.github.platform.core.cloud.service.PullConfigToEnvService;
import com.github.platform.core.cloud.service.impl.NacosConfigToEnvService;
import com.github.platform.core.common.constant.PropertyConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import com.github.platform.core.common.utils.CollectionUtil;

import javax.annotation.PostConstruct;

/**
 * 解析yaml文件到Environment
 * @author: yxkong
 * @date: 2023/3/2 4:03 PM
 * @version: 1.0
 */
@Configuration
@ConditionalOnClass(NacosConfigManager.class)
@ConditionalOnProperty(name = PropertyConstant.CON_ClOUD_NACOS_ENABLED, havingValue = "true")
@Slf4j
public class NacosConfigToEnvConfiguration {
    @Autowired
    private NacosConfigManager nacosConfigManager;
    @Autowired
    private ConfigurableApplicationContext context;
    @Autowired
    private NacosEnvProperties properties;
    @PostConstruct
    public void init(){
        PullConfigToEnvService config = new NacosConfigToEnvService(nacosConfigManager,context);
        if (!CollectionUtil.isEmpty(properties.getMap())){
            properties.getMap().forEach((k,v)->{
                log.info(k+" --- "+v);
                config.initConfig(k,v,2000L);
                config.addListener(k,v);
            });

        }
    }
}
