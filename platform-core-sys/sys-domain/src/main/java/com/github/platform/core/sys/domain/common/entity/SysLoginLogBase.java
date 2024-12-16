package com.github.platform.core.sys.domain.common.entity;

import com.github.platform.core.common.entity.BaseAdminEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;

/**
* 登录日志模型实体
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-15 10:55:05.685
* @version 1.0
*/
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class SysLoginLogBase extends BaseAdminEntity   {
    /** 登录IP地址 */
    @Schema(description = "登录IP地址")
    @NotEmpty(message="登录IP地址（requestIp）不能为空")
    protected String requestIp;
    /** 登录方式 */
    @Schema(description = "登录方式")
    @NotEmpty(message="登录方式（loginWay）不能为空")
    protected String loginWay;
    /** 登录地点 */
    @Schema(description = "登录地点")
    @NotEmpty(message="登录地点（loginLocation）不能为空")
    protected String loginLocation;
    /** 浏览器类型 */
    @Schema(description = "浏览器类型")
    @NotEmpty(message="浏览器类型（browser）不能为空")
    protected String browser;
    /** 登录token */
    @Schema(description = "登录token")
    @NotEmpty(message="登录token（token）不能为空")
    protected String token;
    /** 操作系统 */
    @Schema(description = "操作系统")
    @NotEmpty(message="操作系统（os）不能为空")
    protected String os;
    /** 提示消息 */
    @Schema(description = "提示消息")
    @NotEmpty(message="提示消息（message）不能为空")
    protected String message;
}
