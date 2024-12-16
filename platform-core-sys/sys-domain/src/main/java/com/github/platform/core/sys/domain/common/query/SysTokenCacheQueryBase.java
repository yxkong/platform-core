package com.github.platform.core.sys.domain.common.query;
import com.github.platform.core.common.entity.query.PageQueryBaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;
    import java.time.LocalDateTime;
/**
 * token缓存查询基类
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author yxkong
 * @date 2024-04-28 16:25:03.690
 * @version 1.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Schema(description = "token缓存查询")
public class SysTokenCacheQueryBase extends PageQueryBaseEntity {
    /** 用户token */
    @Schema(description = "用户token")
    private String token;
    /** 登录名称 */
    @Schema(description = "登录名称")
    private String loginName;
    /** 过期时间 */
    @Schema(description = "过期时间")
    private LocalDateTime expireTime;
    /** 最后一次登录时间 */
    @Schema(description = "最后一次登录时间")
    private LocalDateTime lastLoginTime;
}