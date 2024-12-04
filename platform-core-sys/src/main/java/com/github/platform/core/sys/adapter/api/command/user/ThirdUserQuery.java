package com.github.platform.core.sys.adapter.api.command.user;

import com.github.platform.core.common.entity.query.PageQueryBaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;
/**
* 三方用户查询
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @time 2023-05-31 16:04:49.640
* @version 1.0
*/
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=true)
@Schema(description = "三方用户查询")
public class ThirdUserQuery extends PageQueryBaseEntity {
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