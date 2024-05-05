[TOC]
## 系统演示
> http://10.255.200.217:9001/index
> <br>admin / admin

## 部署架构
![img.png](doc/deploy.png)
### 外网域名
- 外网只有一个域名,挂到h5上
- 前端以 域名/h5或static开头
- 后端以
    - sys项目  /sys/项目接口
    - api项目  /api/项目接口
### 前端nginx
- 部署h5
- 路由转发api,防止跨域
- 后台管理前端会在后端接口前加上 demo-sys 用于网关中的转发
### 后端网关
- 后端项目外部调用必须走网关
- 对后端项目的登录鉴权
- 灰度管控；
- 流量控制
- 后端服务的路由（移除demo-sys 后根据项目前缀路由）

## 模块管理
本平台模块定义为三层，最底层是平台底层，管理依赖、交互标准依赖，平台的公共功能封装。

![img_1.png](doc/modules.png)

### 平台底层
- platform-dependencies 所有版本的管理
- platform-core-standard  交互标准，基础标准
    - 通用返回码
    - 出入参标准
    - 事件标准
- platform-core-common 通用功能封装
    - 自定义条件注解
        - @ConditionalOnPropertyInList 判断是否在列表里
    - 配置
        - feign配置
    - 常量
        - 属性常量`PropertyConstant`
        - 全局bean名称常量 `SpringBeanNameConstant`
        - 全局bean排序常量 `SpringBeanOrderConstant`
    - 工具类
        - 获取bean工具类`ApplicationContextHolder`
        - 集合操作工具类`CollectionUtil`
        - md5加密工具类`EncryptUtil`
        - 分布式id工具类 `IdWorker`
        - 占位符替换工具类`PlaceholderUtil`
        - json操作工具类 `JsonUtils`
        - ip操作工具类 ，用于获取本机 `IPUtil`
        - 字符串操作工具类 `StringUtils`
        - yam解析工具类 `YmalUtil`

### 组件层
一方面是对功能的分离封装，定义为功能组件，用于其他项目组合使用，想用哪个用哪个；
另一方面是应用的封装，能力的快速复用，定义为应用组件。比较适用于交付类，如果是自己内部的项目，相应的应用应该封装成服务。
文件、短信，扩展性还是比较强的，能快速的拉出文件服务、短信服务。
#### 功能组件
- platform-core-log 日志组件
    - 定义了日志注解和日志对应的实体
    - 后期可能会统一日志
- platform-core-web web组件
    - web相关功能的引入
    - web切面实现
    - swagger
    - cors配置
    - MockMvc乱码问题解决
    - 接口版本实现
    - web工具类
- platform-core-cloud 分布式组件
    - springCloud相关依赖的引入
    - 后续对于cloud的扩展也会放这里
    - 比如：自定义拉取配置中心的配置
- platform-core-cache 缓存组件
    - 定义公共的config 和 字典（统一由sys写入缓存和删除缓存）
    - redis的引入以及相关操作的实现
    - gateway、api等模块可以引入 cache包进行缓存查询；
- platform-core-lb 负载均衡组件
- platform-core-kafka 工kafka组件
- platform-core-auth 授权统一
    - 定义统一的登录注解`RequiredLogin`
    - 定义统一的threadLocal操作
    - 默认token操作
- platform-core-auth-sys 后台系统鉴权模块
    - token转化为loginInfo切面`LoginInfoConvertAspect`
    - 登陆,角色,权限校验切面`AuthorizeAspect`
    - dataScope切面`DataScopeAspect`（暂时不用）
- platform-core-auth-api  to C 接口鉴权模块
    - token转化为loginInfo切面`LoginInfoConvertAspect`
#### 应用组件，可以集成到后台管理里
- platform-core-file 文件组件
    - 多种方式的文件上传以及预览，已实现本地+天翼云
- platform-core-sms 短信组件
    - 默认路由
    - 短信模板
    - 短信发送及回调
- platform-core-schedule 任务组件
    - 基于quartz封装的动态任务管理
- platform-core-lb-extend 负载均衡灰度组件，灰度管理
    - 灰度规则的管理
- platform-core-workflow 工作流组件
- platform-code-generator 代码生成
    - 同步当前连接数据库的所有的表
    - 根据表配置可以一键生成代码
### 应用层
- platform-core-sys 后台管理基础功能封装
    - 后台管理系统,标准的4层，后续会加一个client层，这里会包装外部使用的交互实体
    - sys模块实现了管理系统的基本操作
    - monitor模块实现了监控相关的操作
- platform-core-gateway 网关通用功能封装
    - 基于nacos开发的动态网关
    - 已实现基于nacos配置中心的的网关规则发布
    - 已实现灰度路由功能
    - 已实现鉴权
        - api项目的鉴权`ApiAuthFilter`
        - sys项目的鉴权`SysAuthFilter`
### 示例demo
- sys 后台管理系统使用
- gateway 网关使用
- api toc的示例
- service 示例

## 约定
> platform-core所有子包，如果需要引入数据库，
> mapper前缀使用 `com.github.platform.core.persistence.mapper`
> entity前缀使用 `com.github.platform.core.persistence.entity`

方便在集成的时候扫描到mapper

例如：
- com.github.platform.core.persistence.mapper.file
- com.github.platform.core.persistence.mapper.sms
- com.github.platform.core.persistence.mapper.schedule

```java
@Configuration
@MapperScan(basePackages = {
        "com.github.platform.core.persistence.mapper",
        "com.自己扩展的包.infra.persistence.mapper"
        }, sqlSessionFactoryRef = "sqlSessionFactorySys")

```

## 开发使用
### 环境
- jdk8
- maven
- mysql
    - 需要设置大小写不敏感 [mysqld] 下设置 `lower_case_table_names=1`
    - 需要支持非`only_full_group_by` 模式 ，设置`sql_mode = "STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION"`
- nacos 2.1
- redis
    - redis 最好是5.0以上，可以用其中的stream消息
- kafka 可选
### 使用
- 先对platform-core进行 mvn clean install
- 对demo进行 mvn clean install
- 项目使用nacos，开发环境配置都已经配置，可以直接在本地启动gateway 和sys这两个项目;
### 开发规范
#### 表名规范
> t_分类_模块_业务

| 分类 | 模块  | 英文全称 | 缩写  | 示例 |
| ---- | ---- | ---- | ---- | ---- |
| 系统(t开头) |  系统管理   |  system    |  sys    | t_sys_user

#### 表名主键映射关系
| 表名 | id  | 外部引用 |
| ---- | ---- | ---- |
| t_sys_role |  id   |  role_id    | 
| t_sys_menu |  id   |  menu_id    | 
| t_sys_dept |  id   |  dept_id    | 
| t_sys_config |  id   |  cfg_id    | 
| t_sys_dict_data |  id   |  dict_id    | 
#### 字段字典
| 字段          |  类型 |  默认值 |描述  |
|-------------| ---- | ---- | ---- |
| id          | bigint | 自增 |所有表主键都叫id
| mobile      | varchar| 11 | 手机号码
| certId      | varchar| 18 | 身份证号
| status      | tinyint| 4 | 状态，1启用/0停用(取值字典)
| deleted     | tinyint| 4 | 删除状态，1启用/0停用(取值字典)
| sort        | int| 8 | 排序字段
| tenant      | varchar(6)| 空字符串  | 租户
| create_by   | varchar(20)| 空字符串  | 创建人(数据归属人)，存储登录名 
| create_time | timestamp| 当前时间  | 创建时间
| update_by   |  varchar(20)| 空字符串 | 更新人，存储登录名
| update_time | timestamp | 更新时间戳 | 更新时间
| remark      | varchar(200)| 空字符串  |备注,只做简单的备注，不一定用

### 缓存
- 缓存操作统一以`ICacheService`，已针对缓存操作做了切面
    - 禁止使用redis 客户端 (排除redisson)
- config
    - 缓存为redis 的一个hash，key为：common:config:
    - hash中 kv，k是config的config_key，v为ConfigEntity
- dict
    - 一个分类一个redis的hash，key为：common:dict:type  type 为具体的分类
    - hash中的kv, 其中k为dict_key， v为DictEntity

以上两个统一封装到了platform-core-cache里，谁需要使用，谁引入。
- sys 模块进行初始化、新增、更新和删除
- 其他模块直接使用

### 数据权限
- 注解声明: `@DataScope(tableAlias = "t"),位置-基础设施层-持久层mapper需要数据权限的方法上`
- 数据权限sql嵌入: `查询sql尾部加上${params.dataScope}`
- 注意点:`mapper的方法第一个参数需要是BaseEntity的子类`

### 前后端交互
正常返回  ResultBean
```json
{ 
  "message": "消息提示",
  "status": "返回状态码",
  "timestamp": "时间戳",
  "data": "业务数据"
}
```
分页数据返回实体
ResultBean嵌套PageBean
```json
{
  "message": "消息提示",
  "status": "返回状态码",
  "timestamp": "时间戳",
  "data": {
    "pageNum": 1,
    "totalSize": 101,
    "pages": 11,
    "pageSize": 10,
    "data": "列表数据"
  }
}
```

## 待完善功能

| 模块      | 功能                              | 认领人    | 预计完成时间  |
|---------|---------------------------------|--------|---------|
| 文件上传    | 页面管理                            | yxkong | 已完成     |
| 文件上传    | 集成腾讯云和七牛的上传                     | yxkong | 阿里云已完成， |
| 短信功能    | 集成阿里云、腾讯云的短信                    |        |         |
| 消息中心    | 短信重构为消息中心                       |        |         |
| 灰度发布    | 通过redis订阅发到各在线节点                | yxkong |         |
| 灰度发布    | 熔断限流发布                          |        |         |
| 流程管理    | 标准工作流                           | yxkong | 已完成     |
| 业务配置    | 业务全局配置中心，给万卡（并发得高）              |        |         |
| 模块管理    | 首页改造，以及其他系统集成）                  |        |         |
| 流程管理    | 钉钉权限审批                          |        |         |
| 菜单      | 增加系统模块                          | yxkong | 已完成     |
| 缓存管理    | 统一放入zset，分页分页                   | yxkong |         |
| 系统监控    | 系统日志中无请求头和body的问题               | yxkong |         |
| redis监控 | 添加redis的简单监控，以及根据key前缀scan扫描key |        |         |
| 登录      | 钉钉扫码登录                          |        |         |
| 系统管理    | 同步钉钉部门，同步钉钉用户                   |        |         |
| 公共      | 增加指定账户演示模式                      |        |
| 公共      | 同一个用户可多处登录                      |        |                  |   
| 系统管理    | 完善在线和踢出功能                       |        |          |          |     
| 工作流     | 工作流支持部门人员，部门负责人                 |        |          |          |     
| 项目管理    | 需求支持内容+链接                       |        |          |          |   
| 工作流     | 表单支持附件                          |        |          |          |
| 工作流     | 流程回退                            |          |          |      |
| 低代码     | 低代码平台的设计                        |          |          |      |
| 项目管理    | 需求节点评分                          |          |          |      |
| 项目管理    | 增加bug登记                         |          |          |      |
| 项目管理    | 自动生成开发任务                        |          |          |      |
| 项目管理    | 增加评论备注功能                        |          |          |      |
| 系统管理    | 配置抽到数据库(ldap,钉钉，附件等)            |          |          |      |