package com.github.platform.core.schedule.infra.configuration;

import com.github.platform.core.common.constant.PropertyConstant;
import com.github.platform.core.common.constant.SpringBeanOrderConstant;
import com.github.platform.core.persistence.mapper.schedule.SysJobMapper;
import com.github.platform.core.schedule.infra.listener.ScheduleStartListener;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Scheduler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.quartz.QuartzProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * 默认使用 RAMJobStore 存储任务
 * @author: yxkong
 * @date: 2023/3/6 3:07 PM
 * @version: 1.0
 */
@Slf4j
@Configuration
@ConditionalOnProperty(name = PropertyConstant.CON_SCHEDULE_ENABLED, havingValue = "true")
@EnableScheduling
@Order(SpringBeanOrderConstant.JOB)
public class ScheduleJobConfiguration {
    @Resource(name = "sysDataSource")
    private DataSource dataSource;

    private Boolean isCluster = false;

    @Bean
    @ConditionalOnProperty(name = PropertyConstant.CON_SCHEDULE_TYPE, havingValue = "jdbc")
    public SchedulerFactoryBean schedulerFactoryBean(QuartzProperties properties) {
        isCluster = true;
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        // 将spring管理job自定义工厂交由调度器维护
//        schedulerFactoryBean.setJobFactory(jobFactory);
        // 设置调度器自动运行
        schedulerFactoryBean.setAutoStartup(true);
        //延迟5秒启动
        schedulerFactoryBean.setStartupDelay(5);
        //设置覆盖已存在的任务
        schedulerFactoryBean.setOverwriteExistingJobs(true);
        //应用关闭时，等待定时任务执行完成
        schedulerFactoryBean.setWaitForJobsToCompleteOnShutdown(true);
        //设置数据源，使用与项目统一数据源
        schedulerFactoryBean.setDataSource(dataSource);
        Properties prop = new Properties();
        prop.putAll(properties.getProperties());
        schedulerFactoryBean.setQuartzProperties(prop);
        return schedulerFactoryBean;
    }
    @Bean
    public ScheduleManager scheduleManager(Scheduler scheduler){
        return new ScheduleManager(scheduler);
    }
    @Bean
    public ScheduleStartListener scheduleStartListener(SysJobMapper sysJobMapper, ScheduleManager scheduleManager){
        return new ScheduleStartListener(sysJobMapper,scheduleManager);
    }

    // 配置了应用关闭等待定时任务执行完成
//    @Bean
//    public ScheduleStopListener scheduleStopListener(SysJobMapper sysJobMapper, ScheduleManager scheduleManager){
//        return new ScheduleStopListener(sysJobMapper,scheduleManager,isCluster);
//    }

//    /**
//     * 继承org.springframework.scheduling.quartz.SpringBeanJobFactory 实现任务实例化方式
//     */
//    public static class AutowiringSpringBeanJobFactory extends SpringBeanJobFactory implements ApplicationContextAware {
//
//        private transient AutowireCapableBeanFactory beanFactory;
//
//        @Override
//        public void setApplicationContext(final ApplicationContext context) {
//            beanFactory = context.getAutowireCapableBeanFactory();
//        }
//
//        /**
//         * 将job实例交给spring ioc托管 我们在job实例实现类内可以直接使用spring注入的调用被spring ioc管理的实例
//         *
//         * @param bundle
//         * @return
//         * @throws Exception
//         */
//        @Override
//        protected Object createJobInstance(final TriggerFiredBundle bundle) throws Exception {
//            final Object job = super.createJobInstance(bundle);
//            /**
//             * 将job实例交付给spring ioc
//             */
//            beanFactory.autowireBean(job);
//            return job;
//        }
//    }
//
//    /**
//     * 配置任务工厂实例
//     *
//     * @param applicationContext spring上下文实例
//     * @return
//     */
//    @Bean
//    public JobFactory jobFactory(ApplicationContext applicationContext) {
//        /**
//         * 采用自定义任务工厂 整合spring实例来完成构建任务 see {@link AutowiringSpringBeanJobFactory}
//         */
//        AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
//        jobFactory.setApplicationContext(applicationContext);
//        return jobFactory;
//    }


}
