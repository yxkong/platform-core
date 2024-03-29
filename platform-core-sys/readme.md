#### 验证码使用说明
```yaml
platform:
  kaptcha:
    type: math
    cache:
      prefix: "sys:captcha:" # 缓存前缀
      timeout: 300  # 缓存时间
    border:
      enabled: true 
      color: black
      thickness: 1
    noise:
      color: blue
      impl: com.google.code.kaptcha.impl.DefaultNoise  # com.google.code.kaptcha.impl.NoNoise 不使用干扰
    obscurificator:
      impl: com.google.code.kaptcha.impl.ShadowGimpy
    producer:
      impl:
    textProducer:
      impl: com.github.platform.core.sys.infra.configuration.kaptcha.KaptchaMathCreator # 数学实现
      character:
        string:
        length: 4
        space: 2
      font:
        names: "Arial,Courier"
        color: black
        size: 38
    background:
      impl:
      colorFrom:
      colorTo:
    word:
      impl:
    image:
      width: 165
      height: 60
```