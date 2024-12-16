package com.github.platform.core.sys.domain.dto;

import lombok.*;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;
/**
* 登录日志传输实体
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @time 2023-06-07 14:37:49.624
* @version 1.0
*/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginLogDto implements Serializable {
    /** 访问ID */
    @Schema(description = "访问ID")
    private Long id;
    /** 登录IP地址 */
    @Schema(description = "登录IP地址")
    private String requestIp;
    /** 登录方式 */
    @Schema(description = "登录方式")
    private String loginWay;
    /** 登录地点 */
    @Schema(description = "登录地点")
    private String loginLocation;
    /** 浏览器类型 */
    @Schema(description = "浏览器类型")
    private String browser;
    /** 登录token */
    @Schema(description = "登录token")
    private String token;
    /** 操作系统 */
    @Schema(description = "操作系统")
    private String os;
    /** 登录状态（0成功 1失败） */
    @Schema(description = "登录状态")
    private String status;
    /** 提示消息 */
    @Schema(description = "提示消息")
    private String message;
    /** 租户 */
    @Schema(description = "租户")
    private Integer tenantId;
    /** 登录账户 */
    @Schema(description = "登录账户")
    private String createBy;
    /** 登录时间 */
    @Schema(description = "登录时间")
    private LocalDateTime createTime;
    /** 备注 */
    @Schema(description = "备注")
    private String remark;
}