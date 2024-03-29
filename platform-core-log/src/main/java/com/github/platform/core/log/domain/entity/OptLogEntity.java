package com.github.platform.core.log.domain.entity;
import com.github.platform.core.log.domain.constants.LogOptTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * 操作日志实体
 * @author: yxkong
 * @date: 2023/4/23 5:03 PM
 * @version: 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OptLogEntity implements Serializable {

    /**traceId*/
    private String traceId;
    /** 日志模块 */
    private String module;
    /** 标题 */
    private String title;
    /** 方法名 */
    private String method;
    /** 请求路径 */
    private String url;
    /** 操作用户 */
    private String loginName;
    /** 请求header */
    private Map<String, String> headersMap;
    /** 请求参数 */
    private Map<String, String[]> parameterMap;
    /** 请求body */
    private String requestBody;
    /** 响应body */
    private String responseBody;
    /** 请求ip */
    private String requestIp;
    /** 请求耗时 ,毫秒级*/
    private Long executeTime;
    /** 异常详细  */
    private String exception;
    /** 租户 */
    private Integer tenantId;
    /** 创建日期 */
    private LocalDateTime createTime;
    /**备注*/
    private String remark;

    /**
     * 操作类型
     */
    private LogOptTypeEnum optType;
    /**
     * 是否持久化
     */
    private Boolean persistent;
}
