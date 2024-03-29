### 使用说明

#### 存储
存储只能使用单一存储
- 目前已经实现两种存储方式,由 `file.storage` 来决定使用哪种存储
  - disk(本地存储)
  - ctyun 天翼云oos
- `file.routers` 表示会初始化的upload实例，为必须配置
- 对应存储配置，由 `file.对应存储类型` 开头的配置 
  - 目前只支持 `file.disk`和 `file.ctyun`
- `file.disk` 参数说明
  - `disk-path` 表示磁盘存储路径，为绝对路径
  - `file-url` 表示文件访问的前缀url，通常由nginx 路由
- `file.ctyun` 参数说明
  - `access-id` 天翼云生成的
  - `access-key` 天翼云生成
  - `bucket-name` 创建的bucket
  - `link-expire-minutes` 生成访问链接的有效期
  - `endpoint` 访问api的url
##### 本地存储示例
```yaml
file:
  storage: disk
  disk:
     disk-path: /tmp
     file-url: http://127.0.0.1
```
##### ctyun存储示例
```yaml
file:
  storage: ctyun
  routers: disk,ctyun
  ctyun:
    access-id: 123
    access-key: 123
    bucket-name: 123
    link-expire-minutes: 30
    endpoint: oos-hbwh.ctyunapi.cn
```
#### 访问
访问可以根据不同阶段使用的不同存储方式路由，比如前2个月用的磁盘存储，后面改成了天翼云了，会根据数据库配置路由
- 访问支持多种方式访问
- `file.routers` 为可支持的访问列表，必须配置
- **配置几种访问，对应这种类型的访问的实现配置必须配置**
```yaml
file:
  routers: disk,ctyun
  dist: disk访问相关配置
  ctyun: 访问相关的配置
```

#### 类使用：
- 配置上面的文件类型
- 直接注入 `IUploadFileExecutor`
```java
   @Service
   public class DemoService{
       @Resource
       private IUploadFileExecutor  uploadFileExecutor;
       public void upload(){
           uploadFileExecutor.uploadAndSave(xxxx,xxx);
       }
   }
```

   