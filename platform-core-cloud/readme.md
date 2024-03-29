
### 说明
本模块主要是引入spring-cloud相关的功能，同时对spring-cloud相关功能的扩展。

### 功能1：拉取nacos配置并放入Environment中
具体看 NacosConfigToEnvConfiguration

使用示例
```yaml
env:
  nacos:
    enabled: true
    map: {"onecard-sys.yml": "DEFAULT_GROUP"}
```
- 开关`env.nacos.enabled: true` 是否开启
- 拉取配置的dataId和group `env.nacos.map`

获取配置
```java
PropertySource<?> propertySource = context.getEnvironment().getPropertySources().get("");
propertySource.get("全路径key")
```