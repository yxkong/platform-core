package com.github.platform.core.sms.domain.common.query;

import com.github.platform.core.common.entity.query.PageQueryBaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
/**
* 服务商查询基类
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-14 17:39:29.748
* @version 1.0
*/
@Data
@SuperBuilder
@NoArgsConstructor
@Schema(description = "服务商查询")
public class SysSmsServiceProviderQueryBase extends PageQueryBaseEntity {
    /** 厂商名称 */
    @Schema(description = "厂商名称")
    private String proName;
}