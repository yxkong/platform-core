package com.github.platform.core.schedule.domain.context;

import com.github.platform.core.schedule.domain.common.query.SysJobQueryBase;
import lombok.*;
import lombok.experimental.SuperBuilder;
/**
* 任务管理查询上下文
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-09-05 11:37:59.627
* @version 1.0
*/
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SysJobQueryContext extends SysJobQueryBase {
}
