package com.github.platform.core.kafka.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 大数据推送信息实体
 *
 * @author: yxkong
 * @date: 2023/8/24 11:44 AM
 * @version: 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BigDataMsgContent<T> implements Serializable {
    private String serviceName;
    private String tableName;
    private Integer tenantId = 1001;
    @Builder.Default
    private Long sendTime = System.currentTimeMillis();
    private String mobile;
    private Long customerId;
    private String certId;
    private String token;
    private String deviceId;
    private String traceId;
    private T data;
}
