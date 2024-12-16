package com.github.platform.core.sys.domain.context;

import com.github.platform.core.sys.domain.common.query.SysTokenCacheQueryBase;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.time.LocalDateTime;
/**
 * token缓存查询上下文
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
public class SysTokenCacheQueryContext extends SysTokenCacheQueryBase {
}
