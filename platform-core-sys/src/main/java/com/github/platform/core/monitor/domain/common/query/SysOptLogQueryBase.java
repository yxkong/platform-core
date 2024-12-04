package com.github.platform.core.monitor.domain.common.query;

import com.github.platform.core.common.entity.query.PageQueryBaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
/**
* 操作日志查询基类
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-15 10:55:05.960
* @version 1.0
*/
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
@Schema(description = "操作日志查询")
public class SysOptLogQueryBase extends PageQueryBaseEntity {
    /** 所属模块 */
    private String module;
    /** 日志标题 */
    private String title;
    /** 执行方法 */
    private String method;
    /** 请求路径 */
    private String url;
    /**ip地址*/
    private String requestIp;
    /**登录用户*/
    private String loginName;
}