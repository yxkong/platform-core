package com.github.platform.core.monitor.adapter.api.convert;

import com.github.platform.core.common.utils.StringUtils;
import com.github.platform.core.monitor.domain.dto.RedisMonitorDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.ArrayList;
import java.util.Properties;

/**
 * redis转换
 * @author: yxkong
 * @date: 2024/2/29 19:19
 * @version: 1.0
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RedisAdapterConvert {
    default RedisMonitorDto toDto(Properties info, Long dbSize, Properties commandStats) {
        RedisMonitorDto rst = RedisMonitorDto.builder().info(info).dbSize(dbSize)
                .commandStats(new ArrayList<>(commandStats.size())).build();
        commandStats.forEach((key, value) -> {
            rst.getCommandStats().add(RedisMonitorDto.CommandStat.builder()
                    .command(StringUtils.substringAfter((String) key, "cmdstat_"))
                    .calls(Long.valueOf(StringUtils.substringBetween((String) value, "calls=", ",")))
                    .usec(Long.valueOf(StringUtils.substringBetween((String) value, "usec=", ",")))
                    .build());
        });
        return rst;
    }
}
