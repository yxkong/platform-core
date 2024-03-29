package com.github.platform.core.sms.adapter.api.command;

import com.github.platform.core.sms.domain.common.query.SysSmsLogQueryBase;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;
/**
* 短信日志查询
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @time 2023-07-04 10:23:45.615
* @version 1.0
*/
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Schema(description = "短信日志查询")
public class SysSysSmsLogQuery extends SysSmsLogQueryBase {
}