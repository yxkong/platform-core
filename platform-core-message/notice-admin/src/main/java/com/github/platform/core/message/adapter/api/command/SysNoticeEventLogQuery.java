package com.github.platform.core.message.adapter.api.command;
import com.github.platform.core.message.domain.common.query.SysNoticeEventLogQueryBase;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;
/**
 * 通知事件日志查询
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
@Schema(description = "通知事件日志查询")
public class SysNoticeEventLogQuery extends SysNoticeEventLogQueryBase {
}