package com.github.platform.core.sys.domain.dto;
import com.github.platform.core.sys.domain.common.entity.SysTokenCacheBase;
import lombok.*;
import lombok.experimental.SuperBuilder;
import io.swagger.v3.oas.annotations.media.Schema;
/**
 * token缓存传输实体
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
@Schema(description = "token缓存传输实体")
public class SysTokenCacheDto extends SysTokenCacheBase{
}