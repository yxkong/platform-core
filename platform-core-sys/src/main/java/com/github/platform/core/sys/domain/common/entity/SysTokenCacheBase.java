package com.github.platform.core.sys.domain.common.entity;
import lombok.*;
import lombok.experimental.SuperBuilder;
import com.github.platform.core.common.entity.BaseAdminEntity;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

/**
 * token缓存模型实体
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author yxkong
 * @date 2024-04-28 16:25:03.690
 * @version 1.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
public class SysTokenCacheBase extends BaseAdminEntity   {
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
    /** 登录方式 */
    @Schema(description = "登录方式")
    @NotEmpty(message="登录方式（loginWay）不能为空")
    protected String loginWay;
}
