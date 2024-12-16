package com.github.platform.core.message.domain.context;
import com.github.platform.core.message.domain.common.entity.SysNoticeEventLogBase;
import lombok.experimental.SuperBuilder;
import lombok.*;
/**
 * 通知事件日志增加或修改上下文
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author yxkong
 * @date 2024-12-04 13:36:01.514
 * @version 1.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SysNoticeEventLogContext extends SysNoticeEventLogBase {
}
