[TOC]
### 参考资料
- https://www.flowable.com/open-source/docs/bpmn/ch02-GettingStarted
- https://cloud.tencent.com/developer/article/2147866
- https://tkjohn.github.io/flowable-userguide/#springBootFlowableStarter

### platform-core-workflow

- 依赖Flowable的版本为6.8.0
- Flowable是BPMN的一个基于java的软件实现，不过Flowable不仅仅包含BPMN，还有BMN决策表和CMMN Case管理引擎。
  - BPMN ： Business Process Model Notaition 业务流程模型符号
  - CMMN ：Case Management Model Notation 案例管理模型符号 
  - DMN： Decision Model Notation 决策模型符号
- 本模块目前只支持BPMN，如果需要支持DMN、CMMN，需要引入相关jar包。

### 业务端使用：
• 创建表（sql目录下 business.sql为业务数据表，flowable-47.sql为flowable原生表）
• 添加本模块maven依赖
• 启用功能（配置文件增加参数：spring.flowable.enabled = true）
• 如果上生产，采用从测试环境复制表结构的方式，需要将表act_ge_property的数据一并复制，里面记录了版本信息


```yaml
# flowable配置
flowable:
  check-process-definitions: true #是否需要自动部署流程定义。
  cmmn:
    async:
      executor:
        async-job-lock-time-in-millis: 300000 #异步作业在被异步执行器取走后的锁定时间（以毫秒计）。在这段时间内，其它异步执行器不会尝试获取及锁定这个任务。'
        default-async-job-acquire-wait-time-in-millis: 10000 #异步作业获取线程在进行下次获取查询前的等待时间（以毫秒计）。只在当次没有取到新的异步作业，或者只取到很少的异步作业时生效。默认值= 10秒。'
        default-queue-size-full-wait-time-in-millis: 0 #异步作业（包括定时器作业与异步执行）获取线程在队列满时，等待执行下次查询的等待时间（以毫秒计）。默认值为0（以向后兼容）'
        default-timer-job-acquire-wait-time-in-millis: 1000 #定时器作业获取线程在进行下次获取查询前的等待时间（以毫秒计）。只在当次没有取到新的定时器作业，或者只取到很少的定时器作业时生效。默认值= 10秒。'
        max-async-jobs-due-per-acquisition: 1 # （译者补）单次查询的异步作业数量。默认值为1，以降低乐观锁异常的可能性。除非你知道自己在做什么，否则请不要修改这个值。'
        retry-wait-time-in-millis: 500 #（译者补不了了）'
        timer-lock-time-in-millis: 300000 #定时器作业在被异步执行器取走后的锁定时间（以毫秒计）。在这段时间内，其它异步执行器不会尝试获取及锁定这个任务。'
    async-executor-activate: true # 是否启用异步执行器。'
    deploy-resources: true #是否部署资源。默认值为''true''。'
    deployment-name: SpringBootAutoDeployment #CMMN资源部署的名字。'
    enable-safe-xml: true # 在解析CMMN XML文件时进行额外检查。参见 https://www.flowable.org/docs/userguide/index.html#advanced.safe.bpmn.xml # 不幸的是，部分平台（JDK 6，JBoss）上无法使用这个功能，因此如果你所用的平台在XML解析时不支持StaxSource，需要禁用这个功能。'
    enabled: false # 是否启用CMMN引擎。'
    resource-location: classpath*:/cases/ # CMMN资源的路径。'
    resource-suffixes: # **.cmmn,**.cmmn11,**.cmmn.xml,**.cmmn11.xml # 需要扫描的资源后缀名。'
    servlet:
      load-on-startup: -1 # 启动时加载CMMN servlet。'
      name: Flowable CMMN Rest API # CMMN servlet的名字。'
      path: /cmmn-api # CMMN servlet的context path。'
  content:
    enabled: false # 是否启动Content引擎。'
    servlet:
      load-on-startup: -1 # 启动时加载Content servlet。'
      name: Flowable Content Rest API # Content servlet的名字。'
      path: /content-api # Content servlet的context path。'
    storage:
      create-root: true # 如果根路径不存在，是否需要创建？'
      root-folder: # 存储content文件（如上传的任务附件，或表单文件）的根路径。'
  custom-mybatis-mappers: # 需要添加至引擎的自定义Mybatis映射的FQN。'
  custom-mybatis-x-m-l-mappers: # 需要添加至引擎的自定义Mybatis XML映射的路径。'
  database-schema: # 如果数据库返回的元数据不正确，可以在这里设置schema用于检测/生成表。'
  database-schema-update: true # 数据库schema更新策略。'
  db-history-used: true # 是否要使用db历史。'
  deployment-name: SpringBootAutoDeployment # 自动部署的名称。'
  dmn:
    deploy-resources: true # 是否部署资源。默认为''true''。'
    deployment-name: SpringBootAutoDeployment # DMN资源部署的名字。'
    enable-safe-xml: true # 在解析DMN XML文件时进行额外检查。参见 https://www.flowable.org/docs/userguide/index.html#advanced.safe.bpmn.xml。不幸的是，部分平台（JDK 6，JBoss）上无法使用这个功能，因此如果你所用的平台在XML解析时不支持StaxSource，需要禁用这个功能。'
    enabled: false # 是否启用DMN引擎。'
    history-enabled: false # 是否启用DMN引擎的历史。'
    resource-location: classpath*:/dmn/ # DMN资源的路径。'
    resource-suffixes: # **.dmn,**.dmn.xml,**.dmn11,**.dmn11.xml # 需要扫描的资源后缀名。'
    servlet:
      load-on-startup: -1 # 启动时加载DMN servlet。'
      name: Flowable DMN Rest API # DMN servlet的名字。'
      path: /dmn-api # DMN servlet的context path。'
    strict-mode: true # 如果希望避免抉择表命中策略检查导致失败，可以将本参数设置为false。如果检查发现了错误，会直接返回错误前一刻的中间结果。'
  form:
    deploy-resources: true # 是否部署资源。默认为''true''。'
    deployment-name: SpringBootAutoDeployment # Form资源部署的名字。'
    enabled: false # 是否启用Form引擎。'
    resource-location: classpath*:/forms/ # Form资源的路径。'
    resource-suffixes: # **.form # 需要扫描的资源后缀名。'
    servlet:
      load-on-startup: -1 # 启动时加载Form servlet。'
      name: Flowable Form Rest API # Form servlet的名字。'
      path: /form-api # Form servlet的context path。'
  history-level: audit # 要使用的历史级别。'
  idm:
    enabled: false # 是否启用IDM引擎。'
    ldap:
      attribute:
        email: # 用户email的属性名。'
        first-name: # 用户名字的属性名。'
        group-id: # 用户组ID的属性名。'
        group-name: # 用户组名的属性名。'
        group-type: # 用户组类型的属性名。'
        last-name: # 用户姓的属性名。'
        user-id: # 用户ID的属性名。'
      base-dn: # 查找用户与组的DN（标志名称 distinguished name）。'
      cache:
        group-size: -1 # 设置{@link org.flowable.ldap.LDAPGroupCache}的大小。这是LRU缓存，用于缓存用户及组，以避免每次都查询LDAP系统。'
      custom-connection-parameters: '# 用于设置所有没有专用setter的LDAP连接参数。查看 http://docs.oracle.com/javase/tutorial/jndi/ldap/jndi.html
                介绍的自定义参数。参数包括配置链接池，安全设置，等等。'
      enabled: false # 是否启用LDAP IDM 服务。'
      group-base-dn: # 组查找的DN。'
      initial-context-factory: com.sun.jndi.ldap.LdapCtxFactory # 初始化上下文工厂的类名。'
      password: # 连接LDAP系统的密码。'
      port: -1 # LDAP系统的端口。'
      query:
        all-groups: # 查询所有组所用的语句。'
        all-users: # 查询所有用户所用的语句。'
        groups-for-user: # 按照指定用户查询所属组所用的语句'
        user-by-full-name-like: # 按照给定全名查找用户所用的语句。'
        user-by-id: # 按照userId查找用户所用的语句。'
      search-time-limit: 0 # 查询LDAP的超时时间（以毫秒计）。默认值为''0''，即“一直等待”。'
      security-authentication: simple # 连接LDAP系统所用的''java.naming.security.authentication''参数的值。'
      server: # LDAP系统的主机名。如''ldap://localhost''。'
      user: # 连接LDAP系统的用户ID。'
      user-base-dn: # 查找用户的DN。'
    password-encoder: # 使用的密码编码类型。'
    servlet:
      load-on-startup: -1 # 启动时加载IDM servlet。'
      name: Flowable IDM Rest API # IDM servlet的名字。'
      path: /idm-api # IDM servlet的context path。'
  mail:
    server:
      default-from: flowable@localhost # 发送邮件时使用的默认发信人地址。'
      host: localhost # 邮件服务器。'
      password: # 邮件服务器的登录密码。'
      port: 1025 # 邮件服务器的端口号。'
      use-ssl: false # 是否使用SSL/TLS加密SMTP传输连接（即SMTPS/POPS)。'
      use-tls: false # 使用或禁用STARTTLS加密。'
      username: # 邮件服务器的登录用户名。如果为空，则不需要登录。'
  process:
    async:
      executor:
        async-job-lock-time-in-millis: 300000 # 异步作业在被异步执行器取走后的锁定时间（以毫秒计）。在这段时间内，其它异步执行器不会尝试获取及锁定这个任务。'
        default-async-job-acquire-wait-time-in-millis: 10000 # 异步作业获取线程在进行下次获取查询前的等待时间（以毫秒计）。只在当次没有取到新的异步作业，或者只取到很少的异步作业时生效。默认值= 10秒。'
        default-queue-size-full-wait-time-in-millis: 0 # 异步作业（包括定时器作业与异步执行）获取线程在队列满时，等待执行下次查询的等待时间（以毫秒计）。默认值为0（以向后兼容）'
        default-timer-job-acquire-wait-time-in-millis: 10000 # 定时器作业获取线程在进行下次获取查询前的等待时间（以毫秒计）。只在当次没有取到新的定时器作业，或者只取到很少的定时器作业时生效。默认值= 10秒。'
        max-async-jobs-due-per-acquisition: 1 # （译者补）单次查询的异步作业数量。默认值为1，以降低乐观锁异常的可能性。除非你知道自己在做什么，否则请不要修改这个值。'
        retry-wait-time-in-millis: 500 # ???（译者补不了了）'
        timer-lock-time-in-millis: 300000 # 定时器作业在被异步执行器取走后的锁定时间（以毫秒计）。在这段时间内，其它异步执行器不会尝试获取及锁定这个任务。'
    async-executor-activate: true # 是否启用异步执行器。'
    definition-cache-limit: -1 # 流程定义缓存中保存流程定义的最大数量。默认值为-1（缓存所有流程定义）。'
    enable-safe-xml: true # 在解析BPMN XML文件时进行额外检查。参见 https://www.flowable.org/docs/userguide/index.html#advanced.safe.bpmn.xml。不幸的是，部分平台（JDK 6，JBoss）上无法使用这个功能，因此如果你所用的平台在XML解析时不支持StaxSource，需要禁用这个功能。'
    servlet:
      load-on-startup: -1 # 启动时加载Process servlet。'
      name: Flowable BPMN Rest API # Process servlet的名字。'
      path: /process-api # Process servelet的context path。'
  process-definition-location-prefix: classpath*:/processes/ # 自动部署时查找流程的目录。'
  process-definition-location-suffixes: # **.bpmn20.xml,**.bpmn # ''processDefinitionLocationPrefix''路径下需要部署的文件的后缀（扩展名）。'
  db-identity-used: false
```


说明
```shell

三、常见的Java类/实例
3.1 ProcessEngine
流程引擎实例，线程安全，一般一个工作流只需要初始化一次。

3.2 RepositoryService
可以通过流程引擎（ProcessEngine）获取到。使用RepositoryService可以根据xml文件路径创建一个新的部署（Deployment），并调用 Deployment#deploy() 实际执行。
代码示例如下（其中xml是放在src/main/resources中）：

```java
RepositoryService repositoryService = processEngine.getRepositoryService();
Deployment deployment = repositoryService.createDeployment()
 .addClasspathResource("holiday-request.bpmn20.xml")
 .deploy();
``` 
3.3 ProcessDefinition
流程定义实例，包含了流程的基本信息。通过RepositoryService获取，可以根据部署ID（Deployment）查询到对应的流程的基本信息，如流程的名字。首先获取到ProcessDefinitionQuery实例，再根据这个Query实例去查询1或多个流程定义实例。
代码示例如下，查询了1个流程定义实例：
```java 
ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
 .deploymentId(deployment.getId())
 .singleResult();
System.out.println("Found process definition : " + processDefinition.getName());
``` 
3.4 Deployment
部署实例，通过RepositoryService获取。
部署之后可以查询流程定义，也可以启动流程实例。
部署的代码示例见 3.2 小节。
PS：当xml文件放在 src/main/resources/processes/ 里时，会被自动部署。

3.5 RuntimeService
运行时服务实例，用来启动一个部署了的流程实例。
通过流程引擎获取，启动时可以传递参数。
代码示例如下：
```java
RuntimeService runtimeService = processEngine.getRuntimeService();
Map<String, Object> variables = new HashMap<>();
variables.put("employee", employee);
variables.put("nrOfHolidays", nrOfHolidays);
variables.put("description", description);
ProcessInstance processInstance =
 
runtimeService.startProcessInstanceByKey("holidayRequest", variables);
``` 
3.6 ProcessInstance
流程实例，通过 RuntimeService 启动一个流程获取。

3.7 TaskService
任务服务实例，常用于查询、完成任务。

3.8 JavaDelegate
用于定义任务执行后自动执行到的一个逻辑。这是一个服务任务。

3.9 其他
1、IdentityService 用于管理（创建，更新，删除，查询……）组与用户。
2、FormService是可选服务。也就是说Flowable没有它也能很好地运行，而不必牺牲任何功能。
3、HistoryService暴露Flowable引擎收集的所有历史数据。要提供查询历史数据的能力。
4、ManagementService通常在用Flowable编写用户应用时不需要使用。它可以读取数据库表与表原始数据的信息，也提供了对作业(job)的查询与管理操作。
5、DynamicBpmnService可用于修改流程定义中的部分内容，而不需要重新部署它。例如可以修改流程定义中一个用户任务的办理人设置，或者修改一个服务任务中的类名。

四、核心数据库表
4.1 数据库
1、Flowable的所有数据库表都以ACT_开头。第二部分是说明表用途的两字符标示符。服务API的命名也大略符合这个规则。
2、ACT_RE_: 'RE’代表repository。带有这个前缀的表包含“静态”信息，例如流程定义与流程资源（图片、规则等）。
3、ACT_RU_: 'RU’代表runtime。这些表存储运行时信息，例如流程实例（process instance）、用户任务（user task）、变量（variable）、作业（job）等。Flowable只在流程实例运行中保存运行时数据，并在流程实例结束时删除记录。这样保证运行时表小和快。
4、ACT_HI_: 'HI’代表history。这些表存储历史数据，例如已完成的流程实例、变量、任务等。
5、ACT_GE_: 通用数据。在多处使用。

4.2 通用数据表（2个）
act_ge_bytearray：二进制数据表，如流程定义、流程模板、流程图的字节流文件；
act_ge_property：属性数据表（不常用）；

4.3 历史表（8个，HistoryService接口操作的表）
act_hi_actinst：历史节点表，存放流程实例运转的各个节点信息（包含开始、结束等非任务节点）；
act_hi_attachment：历史附件表，存放历史节点上传的附件信息（不常用）；
act_hi_comment：历史意见表；
act_hi_detail：历史详情表，存储节点运转的一些信息（不常用）；
act_hi_identitylink：历史流程人员表，存储流程各节点候选、办理人员信息，常用于查询某人或部门的已办任务；
act_hi_procinst：历史流程实例表，存储流程实例历史数据（包含正在运行的流程实例）；
act_hi_taskinst：历史流程任务表，存储历史任务节点；
act_hi_varinst：流程历史变量表，存储流程历史节点的变量信息；

4.4 用户相关表（4个，IdentityService接口操作的表）
act_id_group：用户组信息表，对应节点选定候选组信息；
act_id_info：用户扩展信息表，存储用户扩展信息；
act_id_membership：用户与用户组关系表；
act_id_user：用户信息表，对应节点选定办理人或候选人信息；

4.5 流程定义、流程模板相关表（3个，RepositoryService接口操作的表）
act_re_deployment：部属信息表，存储流程定义、模板部署信息；
act_re_procdef：流程定义信息表，存储流程定义相关描述信息，但其真正内容存储在act_ge_bytearray表中，以字节形式存储；
act_re_model：流程模板信息表，存储流程模板相关描述信息，但其真正内容存储在act_ge_bytearray表中，以字节形式存储；

4.6 流程运行时表（6个，RuntimeService接口操作的表）
act_ru_task：运行时流程任务节点表，存储运行中流程的任务节点信息，重要，常用于查询人员或部门的待办任务时使用；
act_ru_event_subscr：监听信息表，不常用；
act_ru_execution：运行时流程执行实例表，记录运行中流程运行的各个分支信息（当没有子流程时，其数据与act_ru_task表数据是一一对应的）；
act_ru_identitylink：运行时流程人员表，重要，常用于查询人员或部门的待办任务时使用；
act_ru_job：运行时定时任务数据表，存储流程的定时任务信息；
act_ru_variable：运行时流程变量数据表，存储运行中的流程各节点的变量信息；

```

### 表结构
在Flowable 6.8.0中，以下是每个表的作用并列出每张表的所有字段及其含义：

#### 表act_evt_log (用于记录流程引擎事件的日志)

log_nr：日志编号
type：事件类型
proc_def_id：流程定义ID
proc_inst_id：流程实例ID
execution_id：执行实例ID
task_id：任务ID
time_stamp：时间戳
user_id：用户ID
data：事件数据

#### act_ge_bytearray (用于存储字节数组的通用数据)

id：字节数组ID
rev：版本号
name：名称
deployment_id：部署ID
bytes：字节数组

#### act_ge_property (用于存储全局属性)

name：属性名称
value：属性值

#### act_hi_actinst (用于记录历史的活动实例)

id：活动实例ID
proc_def_id：流程定义ID
proc_inst_id：流程实例ID
execution_id：执行实例ID
act_id：活动ID
task_id：任务ID
call_proc_inst_id：调用的流程实例ID
act_name：活动名称
act_type：活动类型
assignee：指派人
start_time：开始时间
end_time：结束时间
duration：持续时间
tenant_id：租户ID

#### act_hi_attachment (用于记录历史的附件信息)

id：附件ID
rev：版本号
user_id：用户ID
name：名称
description：描述
type：类型
task_id：任务ID
proc_inst_id：流程实例ID
url：URL
content_id：内容ID
#### act_hi_comment (用于记录历史的注释信息)

id：注释ID
type：注释类型
time：时间
user_id：用户ID
task_id：任务ID
proc_inst_id：流程实例ID
action：操作
message：消息内容
#### act_hi_detail (用于记录历史的流程实例的详细信息)

id：详细信息ID
type：信息类型
proc_inst_id：流程实例ID
execution_id：执行实例ID
task_id：任务ID
act_inst_id：活动实例ID
scope_id：范围ID
sub_proc_inst_id：子流程实例ID
time：时间
name：名称
var_type：变量类型
rev：版本号
byte_array_id：字节数组ID
double_val：双精度值
long_val：长整型值
text_val：文本值
text2_val：文本值2
#### act_hi_entitylink (用于记录历史的实体链接信息)

id：链接ID
rev：版本号
user_id：用户ID
group_id：用户组ID
type：链接类型
task_id：任务ID
proc_inst_id：流程实例ID
scope_id：范围ID
sub_scope_id：子范围ID
scope_type：范围类型
#### act_hi_identitylink (用于记录历史的身份链接信息)

id：链接ID
group_id：用户组ID
type：链接类型
task_id：任务ID
proc_inst_id：流程实例ID
user_id：用户ID
#### act_hi_procinst (用于记录历史的流程实例信息)

id：流程实例ID
proc_def_id：流程定义ID
business_key：业务键
start_time：开始时间
end_time：结束时间
duration：持续时间
start_act_id：开始活动ID
end_act_id：结束活动ID
super_process_instance_id：父流程实例ID
delete_reason：删除原因
tenant_id：租户ID
#### act_hi_taskinst (用于记录历史的任务实例信息)

id：任务实例ID
task_def_key：任务定义键
proc_def_id：流程定义ID
proc_inst_id：流程实例ID
execution_id：执行实例ID
name：任务名称
parent_task_id：父任务ID
description：描述
owner：任务持有人
assignee：指派人
start_time：开始时间
end_time：结束时间
duration：持续时间
delete_reason：删除原因
claim_time：认领时间
priority：优先级
due_date：到期日期
category：分类
form_key：表单键
tenant_id：租户ID
#### act_hi_tsk_log (用于记录历史的任务日志信息)

log_nr：日志编号
task_id：任务ID
time_stamp：时间戳
user_id：用户ID
data：日志数据
#### act_hi_varinst (用于记录历史的变量实例信息)

id：变量实例ID
proc_inst_id：流程实例ID
execution_id：执行实例ID
task_id：任务ID
act_inst_id：活动实例ID
name：变量名
var_type：变量类型
rev：版本号
byte_array_id：字节数组ID
double_val：双精度值
long_val：长整型值
text_val：文本值
text2_val：文本值2
#### act_id_bytearray (用于存储身份数据的字节数组)

id：字节数组ID
rev：版本号
name：名称
deployment_id：部署ID
bytes：字节数组
#### act_id_group (用于存储用户组信息)

id：用户组ID
rev：版本号
name：名称
type：类型
#### act_id_info (用于存储用户信息)

id：用户ID
rev：版本号
first：名字
last：姓氏
email：电子邮件
pwd：密码
picture_id：头像ID
#### act_id_membership (用于存储用户与用户组之间的成员关系)

user_id：用户ID
group_id：用户组ID
#### act_id_priv (用于存储权限信息)

id：权限ID
name：名称
#### act_id_priv_mapping (用于存储权限与用户组之间的映射关系)

group_id：用户组ID
priv_id：权限ID
#### act_id_property (用于存储用户和用户组的属性)

name：属性名称
user_id：用户ID
group_id：用户组ID
value：属性值
#### act_id_token (用于存储令牌信息)

id：令牌ID
user_id：用户ID
token_value：令牌值
#### act_id_user (用于存储用户信息)

id：用户ID
rev：版本号
first：名字
last：姓氏
email：电子邮件
pwd：密码
picture_id：头像ID
#### act_procdef_info (用于存储流程定义的额外信息)

id：流程定义ID
rev：版本号
info_json_id：信息JSON ID
#### act_re_deployment (用于存储部署信息)

id：部署ID
rev：版本号
name：名称
deployment_time：部署时间
tenant_id：租户ID
#### act_re_model (用于存储流程模型信息)

id：模型ID
rev：版本号
name：名称
key：键
category：分类
create_time：创建时间
last_update_time：上次更新时间
version：版本号
meta_info：元信息
deployment_id：部署ID
editor_source_value_id：编辑器源值ID
editor_source_extra_value_id：编辑器源额外值ID
tenant_id：租户ID
#### act_re_procdef (用于存储流程定义信息)

id：流程定义ID
rev：版本号
category：分类
name：名称
key：键
version：版本号
deployment_id：部署ID
resource_name：资源名称
dgrm_resource_name：图形资源名称
description：描述
has_start_form_key：是否有起始表单键
has_graphical_notation：是否有图形符号
suspension_state：挂起状态
tenant_id：租户ID
#### act_ru_actinst (用于记录运行时的活动实例)

id：活动实例ID
proc_def_id：流程定义ID
proc_inst_id：流程实例ID
execution_id：执行实例ID
act_id：活动ID
task_id：任务ID
call_proc_inst_id：调用的流程实例ID
act_name：活动名称
act_type：活动类型
assignee：指派人
start_time：开始时间
end_time：结束时间
duration：持续时间
tenant_id：租户ID
#### act_ru_deadletter_job (用于记录运行时的无法执行的作业)

id：作业ID
rev：版本号
type：类型
exclusive：排他性
execution_id：执行实例ID
process_instance_id：流程实例ID
process_def_id：流程定义ID
process_def_key：流程定义键
retries：重试次数
exception_stack_id：异常堆栈ID
exception_msg：异常消息
duedate：到期日期
#### act_ru_entitylink (用于记录运行时的实体链接信息)

id：链接ID
rev：版本号
user_id：用户ID
group_id：用户组ID
type：链接类型
task_id：任务ID
proc_inst_id：流程实例ID
scope_id：范围ID
sub_scope_id：子范围ID
scope_type：范围类型
#### act_ru_event_subscr (用于记录运行时的事件订阅信息)

id：订阅ID
rev：版本号
event_type：事件类型
event_name：事件名称
proc_inst_id：流程实例ID
activity_id：活动ID
configuration：配置
#### act_ru_execution (用于记录运行时的执行实例)

id：执行实例ID
proc_def_id：流程定义ID
proc_inst_id：流程实例ID
parent_id：父执行实例ID
super_exec：父执行实例
root_proc_inst_id：根流程实例ID
act_id：活动ID
is_active：是否活动状态
is_concurrent：是否并发状态
is_scope：是否范围
suspension_state：挂起状态
cached_ent_state：缓存的实体状态
tenant_id：租户ID
#### act_ru_external_job (用于记录运行时的外部作业信息)

id：作业ID
rev：版本号
type：类型
lock_exp_time：锁过期时间
lock_owner：锁所有者
exclusive：排他性
execution_id：执行实例ID
process_instance_id：流程实例ID
process_def_id：流程定义ID
process_def_key：流程定义键
retries：重试次数
exception_stack_id：异常堆栈ID
exception_msg：异常消息
duedate：到期日期
#### act_ru_history_job (用于记录运行时的历史作业信息)

id：作业ID
rev：版本号
type：类型
lock_exp_time：锁过期时间
lock_owner：锁所有者
exclusive：排他性
execution_id：执行实例ID
process_instance_id：流程实例ID
process_def_id：流程定义ID
process_def_key：流程定义键
retries：重试次数
exception_stack_id：异常堆栈ID
exception_msg：异常消息
duedate：到期日期
#### act_ru_identitylink (用于记录运行时的身份链接信息)

id：链接ID
group_id：用户组ID
type：链接类型
task_id：任务ID
proc_inst_id：流程实例ID
user_id：用户ID
#### act_ru_job (用于记录运行时的作业信息)

id：作业ID
rev：版本号
type：类型
lock_exp_time：锁过期时间
lock_owner：锁所有者
exclusive：排他性
execution_id：执行实例ID
process_instance_id：流程实例ID
process_def_id：流程定义ID
process_def_key：流程定义键
retries：重试次数
exception_stack_id：异常堆栈ID
exception_msg：异常消息
duedate：到期日期
#### act_ru_suspended_job (用于记录运行时的被挂起的作业信息)

id：作业ID
rev：版本号
type：类型
lock_exp_time：锁过期时间
lock_owner：锁所有者
exclusive：排他性
execution_id：执行实例ID
process_instance_id：流程实例ID
process_def_id：流程定义ID
process_def_key：流程定义键
retries：重试次数
exception_stack_id：异常堆栈ID
exception_msg：异常消息
duedate：到期日期
#### act_ru_task (用于记录运行时的任务信息)

id：任务ID
rev：版本号
name：名称
parent_task_id：父任务ID
description：描述
owner：任务持有人
assignee：指派人
start_time：开始时间
end_time：结束时间
duration：持续时间
delete_reason：删除原因
claim_time：认领时间
priority：优先级
due_date：到期日期
category：分类
form_key：表单键
suspension_state：挂起状态
tenant_id：租户ID
#### act_ru_timer_job (用于记录运行时的定时作业信息)

id：作业ID
rev：版本号
type：类型
lock_exp_time：锁过期时间
lock_owner：锁所有者
exclusive：排他性
execution_id：执行实例ID
process_instance_id：流程实例ID
process_def_id：流程定义ID
process_def_key：流程定义键
retries：重试次数
exception_stack_id：异常堆栈ID
exception_msg：异常消息
duedate：到期日期
#### act_ru_variable (用于记录运行时的变量信息)

id：变量ID
rev：版本号
type：类型
name：名称
execution_id：执行实例ID
proc_inst_id：流程实例ID
task_id：任务ID
byte_array_id：字节数组ID
double_val：双精度值
long_val：长整型值
text_val：文本值
text2_val：文本值2
#### flw_channel_definition (用于存储流程通道定义信息)

id：通道定义ID
name：名称
channel_expression：通道表达式
channel_type：通道类型
channel_param：通道参数
channel_extension：通道扩展
disabled：是否禁用
#### flw_ev_databasechangelog (用于存储数据库变更日志信息)

id：变更日志ID
author：作者
filename：文件名
dateexecuted：执行日期
orderexecuted：执行顺序
exectype：执行类型
md5sum：MD5校验和
description：描述
comments：备注
tag：标签
liquibase：Liquibase版本
contexts：执行上下文
labels：标签
#### flw_ev_databasechangeloglock (用于存储数据库变更日志锁定信息)

id：锁定ID
locked：是否锁定
lockgranted：锁定授权
lockedby：锁定人
locklastchange：锁定时间
createdate：创建日期
#### flw_event_definition (用于存储事件定义信息)

id：事件定义ID
name：名称
description：描述
process_key：流程定义键
referenced_element_id：引用的元素ID
class_name：类名
skip_expression：跳过表达式
execution_order：执行顺序
behavior_class：行为类名
disabled：是否禁用
#### flw_event_deployment (用于存储事件部署信息)

id：部署ID
name：名称
deployment_time：部署时间
tenant_id：租户ID
#### flw_event_resource (用于存储事件资源信息)

id：资源ID
rev：版本号
name：名称
deployment_id：部署ID
bytes：字节数组
#### flw_ru_batch (用于存储运行时的批处理信息)

id：批处理ID
rev：版本号
type：类型
total_jobs：总工作数
jobs_completed：已完成的工作数
batch_status：批处理状态
create_time：创建时间
end_time：结束时间
tenant_id：租户ID

#### flw_ru_batch_part (用于存储运行时的批处理部分信息)

part_id：部分ID
batch_id：批处理ID
type：类型
status：状态
tenant_id：租户ID

