package com.github.platform.core.monitor.domain.common.entity;

import com.github.platform.core.common.entity.BaseAdminEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
* 操作日志模型实体
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-15 10:55:05.960
* @version 1.0
*/
@Data
@SuperBuilder
@NoArgsConstructor
public class SysOptLogBase extends BaseAdminEntity   {
    /** 跟踪ID */
    @Schema(description = "跟踪ID")
    @NotEmpty(message="跟踪ID（traceId）不能为空")
    protected String traceId;
    /** 所属模块 */
    @Schema(description = "所属模块")
    protected String module;
    /** 日志标题 */
    @Schema(description = "日志标题")
    @NotEmpty(message="日志标题（title）不能为空")
    protected String title;
    /** 执行方法 */
    @Schema(description = "执行方法")
    protected String method;
    /** 请求路径 */
    @Schema(description = "请求路径")
    @NotEmpty(message="请求路径（url）不能为空")
    protected String url;
    protected String headers;
    /** 请求参数 */
    @Schema(description = "请求参数")
    @NotEmpty(message="请求参数（requestBody）不能为空")
    protected String requestBody;
    /** 返回参数 */
    @Schema(description = "返回参数")
    @NotEmpty(message="返回参数（responseBody）不能为空")
    protected String responseBody;
    /** ip地址 */
    @Schema(description = "ip地址")
    @NotEmpty(message="ip地址（requestIp）不能为空")
    protected String requestIp;
    protected String exception;
    /** 执行耗时 */
    @Schema(description = "执行耗时")
    @NotNull(message="执行耗时（executeTime）不能为空")
    protected Integer executeTime;
}
