package com.github.platform.core.log.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 缓存实体
 * @author: yxkong
 * @date: 2023/5/5 2:54 PM
 * @version: 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CacheLogEntity implements Serializable {
    /**rediskey*/
    private String key;
    /**操作类型*/
    private String type;
    /**执行方法*/
    private String method;
    /**执行命令*/
    private String command;
    /**登录名称*/
    private String loginName;
    /**租户*/
    private Integer tenantId;
    /**
     * 过期时间，绝对
     */
    private LocalDateTime expireTime;
}
