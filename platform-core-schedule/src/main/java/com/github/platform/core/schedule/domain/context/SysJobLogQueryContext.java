package com.github.platform.core.schedule.domain.context;

import com.github.platform.core.schedule.domain.common.query.SysJobLogQueryBase;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.time.LocalDateTime;
/**
* 任务执行日志查询上下文
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-09-06 18:53:10.711
* @version 1.0
*/
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SysJobLogQueryContext extends SysJobLogQueryBase {
}
