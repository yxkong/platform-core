package com.github.platform.core.workflow.infra.configuration;

import com.github.platform.core.common.constant.PropertyConstant;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

/**
 * flowable 配置
 * @author: yxkong
 * @date: 2023/9/21 9:47 AM
 * @version: 1.0
 */
@Data
@Configuration
@ConfigurationProperties(PropertyConstant.DATA_WORKFLOW)
public class WorkflowProperties {

    /**api项目*/
    private ExecutorProperties executor = new ExecutorProperties();
    /**
     * 项目默认用户
     * platform.workflow.fallbackUsers
     *   - yxkong
     *   - admin
     */
    private List<String> fallbackUsers ;

    @Data
    @SuperBuilder
    @NoArgsConstructor
    public static class ExecutorProperties{
        @Builder.Default
        private Integer coreSize = Runtime.getRuntime().availableProcessors();
        @Builder.Default
        private Integer maxSize = Runtime.getRuntime().availableProcessors()*2;
        @Builder.Default
        private Integer queueSize = 100;
        @Builder.Default
        private Integer keepAliveTime = 100;
        private String threadNamePrefix = "platform-workflow-";
        private Integer awaitTerminationMillis = 30;
        private Boolean waitForTasksToCompleteOnShutdown = true;
        private Boolean allowCoreThreadTimeOut = true;
    }
}
