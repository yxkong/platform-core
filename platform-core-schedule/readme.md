
platform.schedule.enabled

```yaml
platform:
  schedule:
    enabled: true
```



```sql
CREATE TABLE `t_sys_job` (
     `id` bigint(20) NOT NULL AUTO_INCREMENT,
     `name` varchar(100) NOT NULL COMMENT '任务名称',
     `bean_name` varchar(100) NOT NULL COMMENT '处理器bean名称',
     `handler_param` varchar(200) DEFAULT NULL COMMENT '处理器参数',
     `access_token` varchar(32) DEFAULT NULL COMMENT '回调访问token',
     `call_back_url` varchar(200) DEFAULT NULL COMMENT '回调地址，只支持post',
     `job_type` tinyint(1) NOT NULL DEFAULT '1' COMMENT '任务类型1本地，0远程',
     `cron_expression` varchar(50) DEFAULT NULL COMMENT '定时任务表达式',
     `start_date` datetime DEFAULT NULL COMMENT '开始时间,null 用于判断',
     `end_date` datetime DEFAULT NULL COMMENT '结束时间，null 用于判断',
     `retry_count` int(8) DEFAULT '0' COMMENT '重试次数，0为不重试',
     `retry_interval` int(8) DEFAULT '0' COMMENT '重试间隔，单位为秒，0为直接重试',
     `monitor_timeout` int(8) DEFAULT '0' COMMENT '监控超时时间,最大监控时间，单位秒',
     `sub_job_ids` varchar(255) DEFAULT NULL COMMENT '子任务id，以逗号分隔',
     `job_status` int(8) DEFAULT NULL COMMENT '任务状态',
     `tenant_id` int(4) DEFAULT NULL COMMENT '租户Id',
     `status` tinyint(4) DEFAULT '1' COMMENT '状态（1正常 0停用）',
     `create_time` timestamp NOT NULL COMMENT '创建时间',
     `create_by` varchar(20) NOT NULL COMMENT '创建人（保存登录名称）',
     `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
     `update_by` varchar(20) DEFAULT NULL COMMENT '更新人（保存登录名称）',
     `remark` varchar(200) DEFAULT NULL COMMENT '备注',
     PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='动态任务表';
CREATE TABLE `t_sys_job_log` (
     `id` bigint(20) NOT NULL AUTO_INCREMENT,
     `job_id` bigint(20) NOT NULL COMMENT '任务id',
     `bean_name` varchar(100) NOT NULL COMMENT '处理器bean名称',
     `handler_param` varchar(100) DEFAULT NULL COMMENT '处理器参数',
     `start_time` datetime DEFAULT NULL COMMENT '开始执行时间',
     `end_date` datetime DEFAULT NULL COMMENT '结束执行时间',
     `execute_id` varchar(32) DEFAULT NULL COMMENT '执行id',
     `execute_num` int(8) DEFAULT NULL COMMENT 'execute_id的第n次执行',
     `execute_time` int(8) DEFAULT '0' COMMENT '执行耗时，毫秒',
     `result` varchar(2000) DEFAULT '' COMMENT '执行结果',
     `tenant_id` int(4) DEFAULT NULL COMMENT '租户Id',
     `status` tinyint(4) DEFAULT '1' COMMENT '状态（1正常 0异常）',
     `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
     `create_by` varchar(20) DEFAULT NULL COMMENT '创建人（保存登录名称）',
     `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
     `update_by` varchar(20) DEFAULT NULL COMMENT '更新人',
     `remark` varchar(200) DEFAULT NULL COMMENT '备注',
     PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=9738 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='任务执行日志表';

```

### 集群版配置
https://zhuanlan.zhihu.com/p/572406842?utm_id=0

quartz-2.3.2.jar!/org/quartz/impl/jdbcjobstore/tables_mysql_innodb.sql

表说明
1.qrtz_blob_triggers : 以Blob 类型存储的触发器，用于Quartz JDBC创建定制的trigger类型
2.qrtz_calendars：存放日历信息， quartz可配置一个日历来指定一个时间范围。
3.qrtz_cron_triggers：存放cron类型的触发器。
4.qrtz_fired_triggers：存放已触发的触发器。
5.qrtz_job_details：存放一个jobDetail信息。
6.qrtz_locks： 存储程序的悲观锁的信息(假如使用了悲观锁)。
7.qrtz_paused_trigger_graps：存放暂停掉的触发器。
8.qrtz_scheduler_state：调度器状态。
9.qrtz_simple_triggers：简单触发器的信息。
10.qrtz_trigger_listeners：触发器监听器。
11.qrtz_triggers：触发器的基本信息。


```yaml
spring:
  datasource:
    driverClassName: com.mysql.jdbc.Driver
    url: jdbc:mysql://localhost:3306/quartz?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=UTC
    username: root
    password: root
  quartz:
    job-store-type: jdbc # Job 存储器类型。默认为 memory 表示内存，可选 jdbc 使用数据库。
    overwrite-existing-jobs: false # 是否覆盖已有 Job 的配置
    auto-startup: true # Quartz 是否自动启动
    startup-delay: 0 # 延迟 N 秒启动
    wait-for-jobs-to-complete-on-shutdown: true # 应用关闭时，是否等待定时任务执行完成。默认为 false ，建议设置为 true
    jdbc:
      initialize-schema: never #不初始化表结构
    properties:  # 添加 Quartz Scheduler 附加属性，更多可以看 http://www.quartz-scheduler.org/documentation/2.4.0-SNAPSHOT/configuration.html 文档 
      org:
        quartz:
          scheduler:
            instanceId: AUTO #默认主机名和时间戳生成实例ID,可以是任何字符串，但对于所有调度程序来说，必须是唯一的 对应qrtz_scheduler_state INSTANCE_NAME字段
            #instanceName: clusteredScheduler #quartzScheduler
          jobStore:
            class: org.springframework.scheduling.quartz.LocalDataSourceJobStore # springboot>2.5.6后使用这个
            driverDelegateClass: org.quartz.impl.jdbcjobstore.StdJDBCDelegate #仅为数据库制作了特定于数据库的代理
            useProperties: false #以指示JDBCJobStore将JobDataMaps中的所有值都作为字符串，因此可以作为名称 - 值对存储而不是在BLOB列中以其序列化形式存储更多复杂的对象。从长远来看，这是更安全的，因为您避免了将非String类序列化为BLOB的类版本问题。
            tablePrefix: qrtz_  #数据库表前缀
            misfireThreshold: 60000 #在被认为“失火”之前，调度程序将“容忍”一个Triggers将其下一个启动时间通过的毫秒数。默认值（如果您在配置中未输入此属性）为60000（60秒）。
            clusterCheckinInterval: 5000 #设置此实例“检入”*与群集的其他实例的频率（以毫秒为单位）。影响检测失败实例的速度。
            isClustered: true #打开群集功能
          threadPool: #连接池
            class: org.quartz.simpl.SimpleThreadPool # 线程池类型
            threadCount: 10 # 线程池大小。默认为 10 。
            threadPriority: 5 # 线程优先级
            threadsInheritContextClassLoaderOfInitializingThread: true
```

quartz.properties
```properties
## 集群版配置
#org.quartz.scheduler.instanceName = schedulerFactoryBean
#org.quartz.scheduler.instanceName = schedulerFactoryBean
#org.quartz.scheduler.instanceId = AUTO
#org.quartz.jobStore.driverDelegateClass = org.quartz.impl.jdbcjobstore.StdJDBCDelegate
#org.quartz.jobStore.tablePrefix = QRTZ_
#org.quartz.jobStore.useProperties = false
#org.quartz.jobStore.isClustered=true
#org.quartz.jobStore.clusterCheckinInterval=30000
#org.quartz.threadPool.class = org.quartz.simpl.SimpleThreadPool
#org.quartz.threadPool.threadCount = 10
#org.quartz.threadPool.threadPriority = 5
```