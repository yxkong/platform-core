package com.github.platform.core.sys.domain.common.query;

import com.github.platform.core.common.entity.query.PageQueryBaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
/**
* 三方用户查询基类
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-15 10:55:06.505
* @version 1.0
*/
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
@Schema(description = "三方用户查询")
public class SysThirdUserQueryBase extends PageQueryBaseEntity {
    /** 三方唯一标识 */
    @Schema(description = "三方唯一标识")
    private String openId;
    /** 用户名称 */
    @Schema(description = "用户名称")
    private String userName;
    /** 用户来源 */
    @Schema(description = "用户来源")
    private String channel;
}