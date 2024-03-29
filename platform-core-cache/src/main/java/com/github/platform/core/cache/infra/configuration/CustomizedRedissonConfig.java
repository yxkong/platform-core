//package com.github.platform.core.cache.infra.configuration;
//
//import org.redisson.config.Config;
//import org.redisson.spring.starter.RedissonAutoConfiguration;
//import org.redisson.spring.starter.RedissonAutoConfigurationCustomizer;
//
///**
// * 回调方法，定义Config，声明成bean后会注入自定义配置
// * {@link RedissonAutoConfiguration}
// *
// * @date 2020-08-11 20:52
// */
//// @Component
//public class CustomizedRedissonConfig implements RedissonAutoConfigurationCustomizer {
//
//    @Override
//    public void customize(Config configuration) {
//        configuration.setLockWatchdogTimeout(3000L);
//    }
//}
