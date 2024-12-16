package com.github.platform.core.monitor.domain.context;

import com.github.platform.core.monitor.domain.common.query.SysOptLogQueryBase;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
/**
* 操作日志查询上下文
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-15 10:55:05.960
* @version 1.0
*/
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class SysOptLogQueryContext extends SysOptLogQueryBase {
}
