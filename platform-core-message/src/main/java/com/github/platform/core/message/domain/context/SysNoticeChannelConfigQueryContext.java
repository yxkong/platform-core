package com.github.platform.core.message.domain.context;

import com.github.platform.core.message.domain.common.query.SysNoticeChannelConfigQueryBase;
import lombok.*;
import lombok.experimental.SuperBuilder;
/**
 * 通知通道配置查询上下文
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author yxkong
 * @date 2024-12-04 13:32:28.892
 * @version 1.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SysNoticeChannelConfigQueryContext extends SysNoticeChannelConfigQueryBase {
}
