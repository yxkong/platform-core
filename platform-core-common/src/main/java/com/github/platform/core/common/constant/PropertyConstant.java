package com.github.platform.core.common.constant;

/**
 * 属性配置常量
 * @author: yxkong
 * @date: 2023/4/17 10:07 AM
 * @version: 1.0
 */
public interface PropertyConstant {
    String PREFIX = "platform";
    /**通知类型*/
    String CON_NOTICE_TYPE = "platform.notice.type";
    /**钉钉通知配置*/
    String DATA_NOTICE_DING_TALK = "platform.notice.ding-talk";
    /**雪花算法*/
    String DATA_SNOWFLAKE = "platform.snowflake";
    String CON_SNOWFLAKE_ENABLED = "platform.snowflake.enabled";
    String DATA_ASYNC = "platform.async";
    String DATA_DING_TALK = "platform.ding-talk";
    /**延迟队列配置*/
    String DATA_DELAY = "platform.delay";

    /**文件配置*/
    String DATA_FILE = "platform.file";
    /**文件路由配置，用于多种方式上传的文件*/
    String CON_FILE_ROUTERS = "platform.file.routers";
    /**
     * 从nacos拉取配置到env
     */
    String CON_ClOUD_NACOS_ENABLED = "platform.cloud.nacos.enabled";
    /***
     * flowable配置
     */
    String CON_WORKFLOW_FLOWABLE_ENABLED = "platform.workflow.flowable.enabled";
    String DATA_SCHEDULE = "platform.schedule";
    /**定时任务开关*/
    String CON_SCHEDULE_ENABLED = "platform.schedule.enabled";
    /**DB的方式开启定时*/
    String CON_SCHEDULE_TYPE = "platform.schedule.type";
    /**系统配置*/
    String DATA_SYSTEM = "platform.system";
    /**缓存前缀*/
    String DATA_CACHE_PREFIX = "platform.cache.prefix";
    /**swagger配置*/
    String DATA_SWAGGER = "platform.swagger";
    /**权限相关*/
    String DATA_AUTH = "platform.auth";
    String DATA_WORKFLOW = "platform.workflow";
    /**网关路由nacos的配置*/
    String DATA_GATEWAY_ROUTE_NACOS = "platform.gateway.route.nacos";
    /**灰度开关*/
    String CON_GATEWAY_GRAY_ENABLED = "platform.gray.enabled";
    /**网关路由类型*/
    String CON_GATEWAY_ROUTE_TYPE = "platform.gateway.route.type";
    /**http请求日志打印*/
    String CON_HTTP_TRACE_ENABLED = "platform.http.trace.enabled";
    /**sleuth tracing */
    String CON_SLEUTH_TRACING_ENABLED = "platform.sleuth.tracing.enabled";
    /**验证码配置*/
    String DATA_KAPTCHA = "platform.kaptcha";

    /**操作日志配置*/
    String DATA_OPT_LOG = "platform.opt.log";
    /**ldap配置*/
    String DATA_LDAP = "platform.ldap";
    /**项目配置*/
    String DATA_PM = "platform.pm";
    String FEIGN = "feign";
    /**redis订阅发布开关*/
    String CON_REDIS_PUBLISH_ENABLED = "platform.redis.publish.enabled";
}
