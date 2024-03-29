package com.github.platform.core.sms.domain.common.query;

import com.github.platform.core.common.entity.query.PageQueryBaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
/**
* 模板厂商查询基类
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-14 17:39:30.326
* @version 1.0
*/
@Data
@SuperBuilder
@NoArgsConstructor
@Schema(description = "模板厂商查询")
public class SysSmsTemplateStatusQueryBase extends PageQueryBaseEntity {
    /** 模板编号 */
    @Schema(description = "模板编号")
    private String tempNo;
    /** 厂商编号 */
    @Schema(description = "厂商编号")
    private String proNo;
}