package com.github.platform.core.monitor.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Properties;

/**
 * redis monitor信息
 * @author: yxkong
 * @date: 2024/2/29 19:14
 * @version: 1.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
@Schema(description = "操作日志传输实体")
public class RedisMonitorDto {
    @Schema(description = "Redis info指令结果，参考redis文档")
    private Properties info;

    @Schema(description = "Redis key 数量")
    private Long dbSize;

    @Schema(description = "CommandStat 数组")
    private List<CommandStat> commandStats;

    @Schema(description = "Redis 命令统计结果")
    @Data
    @Builder
    @AllArgsConstructor
    public static class CommandStat {

        @Schema(description = "Redis 命令")
        private String command;

        @Schema(description = "调用次数")
        private Long calls;

        @Schema(description = "消耗 CPU 微秒数")
        private Long usec;

        @Schema(description = "命令平均执行所消耗的微秒数")
        private Double usecPerCall;

    }
}
