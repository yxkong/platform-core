package com.github.platform.core.common.configuration;

import com.github.platform.core.common.constant.PropertyConstant;
import com.github.platform.core.common.utils.StringUtils;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

/**
 * 自定义线程池
 * @author: yxkong
 * @date: 2023/5/4 3:13 PM
 * @version: 1.0
 */
@Configuration
@Slf4j
@ConfigurationProperties(prefix =  PropertyConstant.DATA_ASYNC)
public class ThreadPoolConfiguration {
    private int coreSize;
    private int maxPoolSize;
    private long keepAliveTime;
    private int queueSize;
    private String name;
    private int defaultSize = Math.max(1,Runtime.getRuntime().availableProcessors()/2);

    @Bean(name = "asyncEventExecutor")
    public Executor asyncEventExecutor() {
        log.info("start asyncEventExecutor --->");
        int maxPoolSize = Math.max(1,Runtime.getRuntime().availableProcessors()/2);
        return new ThreadPoolExecutor(this.getCoreSize(), this.getMaxPoolSize(), this.getKeepAliveTime(), TimeUnit.SECONDS
                , new LinkedBlockingQueue<>(this.getQueueSize()), getThreadFactory(this.getName()), new ThreadPoolExecutor.CallerRunsPolicy());
    }
    private ThreadFactory getThreadFactory(String name){
        return  new ThreadFactoryBuilder().setNameFormat(name).build();
    }

    public int getCoreSize() {
        if (this.coreSize == 0){
            return defaultSize;
        }
        return coreSize;
    }

    public void setCoreSize(int coreSize) {
        this.coreSize = coreSize;
    }

    public int getMaxPoolSize() {
        if (this.coreSize == 0){
            return defaultSize;
        }
        return maxPoolSize;
    }

    public void setMaxPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    public long getKeepAliveTime() {
        if (this.keepAliveTime == 0){
            return  60;
        }
        return keepAliveTime;
    }

    public void setKeepAliveTime(long keepAliveTime) {
        this.keepAliveTime = keepAliveTime;
    }

    public int getQueueSize() {
        if (this.coreSize == 0){
            return 200;
        }
        return queueSize;
    }

    public void setQueueSize(int queueSize) {
        this.queueSize = queueSize;
    }

    public String getName() {
        if (StringUtils.isEmpty(this.name)){
            return "async-event-executor-%d";
        }
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
