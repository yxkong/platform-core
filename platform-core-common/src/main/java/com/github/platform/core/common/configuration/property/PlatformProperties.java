package com.github.platform.core.common.configuration.property;

import com.github.platform.core.common.constant.PropertyConstant;
import com.github.platform.core.common.utils.CollectionUtil;
import com.github.platform.core.common.utils.StringUtils;
import com.google.common.collect.Sets;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 系统配置
 * @author: yxkong
 * @date: 2023/4/17 1:48 PM
 * @version: 1.0
 */
@ConfigurationProperties(prefix =  PropertyConstant.PREFIX)
@Configuration
@Data
public class PlatformProperties {
    private String defaultZone ;
    /**系统配置*/
    private SystemInfo system = new SystemInfo();
    /**包含header*/
    private List<String> includeHeaders;
    /**是否演示模式*/
    private boolean demoMode;

    public Set<String> getIncludeHeaders() {
        if (CollectionUtil.isNotEmpty(includeHeaders)){
            return new HashSet<>(includeHeaders);
        }
        return  Sets.newHashSet("token","loginName","tenantId");
    }
    public String getDefaultZone(){
        if (StringUtils.isEmpty(this.defaultZone)){
            return "Asia/Shanghai";
        }
        return this.defaultZone;
    }
}
