package com.github.platform.core.sms.domain.common.query;

import com.github.platform.core.common.entity.query.PageQueryBaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
/**
* 短信白名单查询基类
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-14 17:39:30.643
* @version 1.0
*/
@Data
@SuperBuilder
@NoArgsConstructor
@Schema(description = "短信白名单查询")
@EqualsAndHashCode(callSuper=true)
public class SysSmsWhiteListQueryBase extends PageQueryBaseEntity {
    /** 渠道 */
    @Schema(description = "渠道")
    private String channel;
    /** 姓名 */
    @Schema(description = "姓名")
    private String name;
    /** 手机号 */
    @Schema(description = "手机号")
    private String mobile;
}