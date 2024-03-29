package com.github.platform.core.sms.domain.common.query;

import com.github.platform.core.common.entity.query.PageQueryBaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
/**
* 短信模板查询基类
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-14 17:39:30.124
* @version 1.0
*/
@Data
@SuperBuilder
@NoArgsConstructor
@Schema(description = "短信模板查询")
public class SysSmsTemplateQueryBase extends PageQueryBaseEntity {
    /** 模板编号 */
    @Schema(description = "模板编号")
    private String tempNo;
    /** 模板名称 */
    @Schema(description = "模板名称")
    private String name;
}