package com.github.platform.core.monitor.adapter.api.command;

import com.github.platform.core.common.entity.query.PageQueryBaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @time 2023-05-04 17:37:00.879
* @version 1.0
*/
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "在线用户查询")
public class OnLineQuery extends PageQueryBaseEntity {
    /** 时间戳 */
    @Schema(description = "时间戳，后端返回，前端下次查询传入")
    private Long start;

}