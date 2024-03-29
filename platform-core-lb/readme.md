### 说明
本模块是灰度的基础包
使用场景：
- 开发环境联调，大家都启一个项目的时候，联调的请求会随机路由
- 灰度发布，只针对某些用户开启验证

NacosLoadBalancerConfiguration  基于nacos注册中心的实现
PublicAutoConfiguration  基于feign和restTemplate请求的时候灰度标签透传



### 使用
在nacos的元数据上添加label
```yaml
spring:
  cloud:
    nacos:
      discovery:
        metadata:
          label: gray
```

在接口请求的时候，在header中添加`label=gray` 
在经过网关和api项目的时候，会根据label路由到具体的服务
如果没有匹配到对应的label会从所有的服务中以轮询的方式获取一个服务