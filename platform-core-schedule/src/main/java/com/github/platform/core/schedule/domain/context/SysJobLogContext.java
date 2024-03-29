package com.github.platform.core.schedule.domain.context;
import com.github.platform.core.schedule.domain.common.entity.SysJobLogBase;
import lombok.experimental.SuperBuilder;
import lombok.*;
/**
* 任务执行日志增加或修改上下文
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
public class SysJobLogContext extends SysJobLogBase {
}
