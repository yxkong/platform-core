package com.github.platform.core.monitor.adapter.api.command;

import com.github.platform.core.monitor.domain.common.query.SysOptLogQueryBase;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
/**
* 操作日志查询
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-15 10:55:05.960
* @version 1.0
*/
@Data
@SuperBuilder
@NoArgsConstructor
@Schema(description = "操作日志查询")
public class SysOptLogQuery extends SysOptLogQueryBase {
}