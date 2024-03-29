package com.github.platform.core.cloud.service.impl;

import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import com.github.platform.core.cloud.service.PullConfigToEnvService;
import com.github.platform.core.common.utils.YamlUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.MapPropertySource;
import com.github.platform.core.common.utils.CollectionUtil;

import java.util.Map;
import java.util.concurrent.Executor;

/**
 * 拉取nacos配置到环境变量
 * @author: yxkong
 * @date: 2023/3/2 5:12 PM
 * @version: 1.0
 */
@Slf4j
public class NacosConfigToEnvService implements PullConfigToEnvService {
    private NacosConfigManager nacosConfigManager;
    private ConfigurableApplicationContext context;

    public NacosConfigToEnvService(NacosConfigManager nacosConfigManager, ConfigurableApplicationContext context) {
        this.nacosConfigManager = nacosConfigManager;
        this.context = context;
    }

    @Override
    public void initConfig(String dataId, String group, Long timeout) {
        try {
            String configInfo = nacosConfigManager.getConfigService().getConfig(dataId, group, timeout);
            if(log.isDebugEnabled()){
                log.debug("dataId:{},group:{}初始化配置：{}",dataId,group,configInfo);
            }
            Map<String, Object> load = YamlUtil.load(configInfo);
            if (!CollectionUtil.isEmpty(load)){
                MapPropertySource source = new MapPropertySource(dataId,load);
                context.getEnvironment().getPropertySources().addLast(source);
            }
            if (log.isDebugEnabled()){
                log.debug("dataId:{},group:{}初始化配置完成",dataId,group);
            }

        } catch (NacosException e) {
            log.error("initConfig is error",e);
        }
    }

    @Override
    public void addListener(String dataId, String group) {
        try {
            nacosConfigManager.getConfigService().addListener(dataId, group, new Listener() {
                @Override
                public Executor getExecutor() {
                    return null;
                }

                @Override
                public void receiveConfigInfo(String configInfo) {
                    if(log.isDebugEnabled()){
                        log.debug("dataId:{},group:{}更新配置：",dataId,group,configInfo);
                    }
                    Map<String, Object> load = YamlUtil.load(configInfo);
                    if (!CollectionUtil.isEmpty(load)){
                        MapPropertySource source = new MapPropertySource(dataId,load);
                        context.getEnvironment().getPropertySources().addLast(source);
                    }
                    if (log.isDebugEnabled()){
                        log.debug("dataId:{},group:{}配置更新完成",dataId,group);
                    }
                }
            });
        } catch (NacosException e) {
            log.error("addListener is error",e);
        }
    }
}
