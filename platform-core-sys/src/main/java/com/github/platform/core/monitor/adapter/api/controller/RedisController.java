package com.github.platform.core.monitor.adapter.api.controller;

import com.github.platform.core.auth.annotation.RequiredLogin;
import com.github.platform.core.cache.infra.service.ICacheService;
import com.github.platform.core.common.utils.StringUtils;
import com.github.platform.core.monitor.adapter.api.convert.RedisAdapterConvert;
import com.github.platform.core.monitor.domain.dto.RedisMonitorDto;
import com.github.platform.core.standard.entity.dto.ResultBean;
import com.github.platform.core.standard.entity.KeyReq;
import com.github.platform.core.web.web.BaseController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.RedisServerCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

/**
 * redis缓存
 * @author: yxkong
 * @date: 2024/2/29 19:12
 * @version: 1.0
 */
@RestController
@Tag(name = "redis",description = "redis监控")
@RequestMapping("/sys/monitor/redis")
@Slf4j
public class RedisController extends BaseController {
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private ICacheService cacheService;
    @Resource
    private RedisAdapterConvert convert;

    @Operation(summary = "redis监控信息",tags = {"redis"})
    @PostMapping("/monitor")
    @RequiredLogin
    public ResultBean<RedisMonitorDto> monitor() {
        // 获得 Redis 统计信息
        Properties info = stringRedisTemplate.execute((RedisCallback<Properties>) RedisServerCommands::info);
        Long dbSize = stringRedisTemplate.execute(RedisServerCommands::dbSize);
        Properties commandStats = stringRedisTemplate.execute((
                RedisCallback<Properties>) connection -> connection.info("commandstats"));
        assert commandStats != null;
        // 拼接结果返回
        return buildSucResp(convert.toDto(info, dbSize, commandStats));
    }

    @Operation(summary = "redis key 查询",tags = {"redis"})
    @PostMapping("/scanKeys")
    @RequiredLogin
    public ResultBean<List<String>> scanKeys(@RequestBody @Validated KeyReq keyReq) {
        List<String> keys = new ArrayList<>();
        stringRedisTemplate.execute((RedisCallback<Object>) (connection) -> {
            // 创建迭代器选项
            ScanOptions options = ScanOptions.scanOptions()
                    .match(keyReq.getKey())
                    // 可以设置每次迭代返回的数量，默认是10
                    .count(100)
                    .build();
            // 使用迭代器扫描
            connection.scan(options).forEachRemaining(key -> keys.add(new String(key)));
            return null;
        });
        return buildSucResp(keys) ;
    }
    @Operation(summary = "查询对应key的值",tags = {"redis"})
    @PostMapping("/findByKey")
    @RequiredLogin
    public ResultBean<String> findByKey(@RequestBody @Validated KeyReq keyReq){
        return buildSucResp(stringRedisTemplate.opsForValue().get(keyReq.getKey()));
    }
    @Operation(summary = "删除对应的key",tags = {"redis"})
    @PostMapping("/deleteByKey")
    public ResultBean<Boolean> deleteByKey(@RequestBody @Validated KeyReq keyReq){
        Boolean delete = stringRedisTemplate.delete(keyReq.getKey());
        return buildSimpleResp(delete,delete? "删除成功！":"删除失败！");
    }



}

