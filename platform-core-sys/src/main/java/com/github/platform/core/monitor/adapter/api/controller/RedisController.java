package com.github.platform.core.monitor.adapter.api.controller;

import com.github.platform.core.auth.annotation.RequiredLogin;
import com.github.platform.core.monitor.adapter.api.convert.RedisAdapterConvert;
import com.github.platform.core.monitor.domain.dto.RedisMonitorDto;
import com.github.platform.core.standard.entity.dto.ResultBean;
import com.github.platform.core.web.web.BaseController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.RedisServerCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
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
}

