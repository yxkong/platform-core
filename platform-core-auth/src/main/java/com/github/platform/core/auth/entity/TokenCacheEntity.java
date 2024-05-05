package com.github.platform.core.auth.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * token缓存实体
 * @author: yxkong
 * @date: 2024/4/29 22:06
 * @version: 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenCacheEntity {
    @Schema(description = "主键")
    protected Long id;
    @Schema(description = "加密id")
    protected String strId;
    @Schema(description = "租户")
    protected Integer tenantId;
    /** 用户token */
    @Schema(description = "用户token")
    @NotEmpty(message="用户token（token）不能为空")
    protected String token;
    /** 登录名称 */
    @Schema(description = "登录名称")
    @NotEmpty(message="登录名称（loginName）不能为空")
    protected String loginName;
    /** 过期时间 */
    @Schema(description = "过期时间")
    @NotNull(message="过期时间（expireTime）不能为空")
    protected LocalDateTime expireTime;
    /** 最后一次登录时间 */
    @Schema(description = "最后一次登录时间")
    @NotNull(message="最后一次登录时间（lastLoginTime）不能为空")
    protected LocalDateTime lastLoginTime;
    /** 登录信息 */
    @Schema(description = "登录信息")
    @NotEmpty(message="登录信息（loginInfo）不能为空")
    protected String loginInfo;
}
