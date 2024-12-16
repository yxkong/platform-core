package com.github.platform.core.gateway.infra.configuration.mybatis;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.pool.DruidDataSource;
import com.github.platform.core.common.constant.SpringBeanOrderConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * druid数据源配置
 *
 * @author: yxkong
 * @date: 2021/6/2 4:46 下午
 * @version: 1.0
 */
@Configuration
@Order(SpringBeanOrderConstant.DB_INIT)
public class DruidConfiguration {

    private static final Logger log = LoggerFactory.getLogger(DruidConfiguration.class);

    private List<Filter> getFilters() {
        List<Filter> filters = new ArrayList<>();
        return filters;
    }

    /**
     * arbitration库（可能多个）
     * ConfigurationProperties会去自动读取YAML中datasource.loan开头的配置
     *
     * @return DataSource
     */
    @Bean(name = "sysDataSource")
    @ConfigurationProperties(prefix = "datasource.sys")
    @Primary
    public DataSource sysDataSource() {
        log.info("-------------------- sysDataSource init ---------------------");
        DruidDataSource dataSource = DataSourceBuilder.create().type(DruidDataSource.class).build();
        dataSource.setProxyFilters(getFilters());
        return dataSource;
    }
}
