package com.github.platform.core.sys.adapter.api.command;
import com.github.platform.core.sys.domain.common.query.SysTokenCacheQueryBase;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.time.LocalDateTime;
/**
 * token缓存查询
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
public class SysTokenCacheQuery extends SysTokenCacheQueryBase {
}