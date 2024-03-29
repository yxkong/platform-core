package com.github.platform.core.common.configuration.property;

import com.github.platform.core.common.constant.PropertyConstant;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author yxkong
 */
@Component
@ConfigurationProperties(prefix =  PropertyConstant.DATA_SNOWFLAKE)
public class IdWorkerProperties {
    private List<String> cluster;
    private int skip;

    public List<String> getCluster() {
        return cluster;
    }

    public void setCluster(List<String> cluster) {
        this.cluster = cluster;
    }

    public int getSkip() {
        if (skip == 0){
            return 1;
        }
        return skip;
    }

    public void setSkip(int skip) {
        this.skip = skip;
    }
}
