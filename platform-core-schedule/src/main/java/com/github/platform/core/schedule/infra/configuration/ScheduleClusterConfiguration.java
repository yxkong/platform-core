//package com.github.platform.core.schedule.configuration;
//
//import com.github.platform.core.common.constant.PropertyConstant;
//import com.github.platform.core.common.constant.SpringBeanOrderConstant;
//import com.github.platform.core.schedule.infra.configuration.ScheduleJobConfiguration;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.boot.autoconfigure.quartz.QuartzProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.annotation.Order;
//import org.springframework.scheduling.quartz.SchedulerFactoryBean;
//
//import javax.annotation.Resource;
//import javax.sql.DataSource;
//import java.util.Properties;
//
///**
// * 集群版配置，需要使用 quartz.properties
// * <p>1,找到 org/quartz/impl/jdbcjobstore/tables_mysql_innodb.sql  使用什么数据库</p>
// *
// * @author: yxkong
// * @date: 2023/3/6 5:35 PM
// * @version: 1.0
// */
//@Configuration
//@ConditionalOnBean({ScheduleJobConfiguration.class})
//@ConditionalOnProperty(name = PropertyConstant.CON_SCHEDULE_ENABLED_DB, havingValue = "jdbc")
//@Order(SpringBeanOrderConstant.JOB_DB)
//public class ScheduleClusterConfiguration {
//
//    @Resource(name = "sysDataSource")
//    private DataSource dataSource;
//
//    @Bean
//    public SchedulerFactoryBean schedulerFactoryBean(QuartzProperties properties) {
//        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
//        //设置覆盖已存在的任务
//        schedulerFactoryBean.setOverwriteExistingJobs(true);
//        //设置数据源，使用与项目统一数据源
//        schedulerFactoryBean.setDataSource(dataSource);
//        Properties prop = new Properties();
//        prop.putAll(properties.getProperties());
//        schedulerFactoryBean.setQuartzProperties(prop);
//        return schedulerFactoryBean;
//    }
//
//}
