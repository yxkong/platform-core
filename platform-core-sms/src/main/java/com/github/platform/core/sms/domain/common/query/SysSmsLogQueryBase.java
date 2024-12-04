package com.github.platform.core.sms.domain.common.query;

import com.github.platform.core.common.entity.query.PageQueryBaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
/**
* 短信日志查询基类
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-14 17:39:30.502
* @version 1.0
*/
@Data
@SuperBuilder
@NoArgsConstructor
@Schema(description = "短信日志查询")
@EqualsAndHashCode(callSuper=true)
public class SysSmsLogQueryBase extends PageQueryBaseEntity {
    /** 厂商编号，用于统计花费 */
    @Schema(description = "厂商编号，用于统计花费")
    private String proNo;
    /** 手机号 */
    @Schema(description = "手机号")
    private String mobile;
    /** 三方消息id */
    @Schema(description = "三方消息id")
    private String thirdMsgId;
    /** 模板编号 */
    @Schema(description = "模板编号")
    private String tempNo;
}