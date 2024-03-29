package com.github.platform.core.workflow.infra.configuration.environment;

import com.github.platform.core.common.constant.PropertyConstant;
import org.flowable.engine.ProcessEngineConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;

import java.util.HashMap;
import java.util.Map;

public class FlowableDisableEnvironmentPostProcessor implements EnvironmentPostProcessor, Ordered {

    public static final int DEFAULT_ORDER = Ordered.HIGHEST_PRECEDENCE + 20;

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        MutablePropertySources propertySources = environment.getPropertySources();
        Map<String, Object> source = new HashMap<>();

        if (isEnabled(environment)) {
            // 关闭自动创建表及更新表
            source.put("flowable.database-schema-update", ProcessEngineConfiguration.DB_SCHEMA_UPDATE_FALSE);
        } else {
            source.put("flowable.process.enabled", false);
            source.put("flowable.app.enabled", false);
            source.put("flowable.eventregistry.enabled", false);
        }
        source.put("flowable.cmmn.enabled", false);
        source.put("flowable.content.enabled", false);
        source.put("flowable.dmn.enabled", false);
        source.put("flowable.form.enabled", false);
        source.put("flowable.db-identity-used.enabled", false);
        source.put("flowable.idm.enabled", false);
        source.put("flowable.idm.ldap.enabled", false);
        propertySources.addFirst(new MapPropertySource("flowable-disable", source));
    }

    @Override
    public int getOrder() {
        return DEFAULT_ORDER;
    }

    protected Boolean isEnabled(ConfigurableEnvironment environment) {
        return environment.getProperty(PropertyConstant.CON_WORKFLOW_FLOWABLE_ENABLED, Boolean.class, false);
    }
}
