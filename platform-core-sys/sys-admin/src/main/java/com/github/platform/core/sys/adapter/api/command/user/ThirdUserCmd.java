package com.github.platform.core.sys.adapter.api.command.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
* 三方用户增加或修改
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @time 2023-05-31 16:04:49.640
* @version 1.0
*/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "三方用户增加或修改")
public class ThirdUserCmd {
    /** 主键 */
    @Schema(description = "主键")
    private Long id;
    /** 绑定用户id */
    @Schema(description = "绑定用户id")
    private String userId;
    /** 三方唯一标识 */
    @Schema(description = "三方唯一标识")
    private String openId;
    /** 访问token */
    @Schema(description = "访问token")
    private String accessToken;
    /** 用户名称 */
    @NotEmpty(message=")用户名称（userName）不能为空")
    @Schema(description = "用户名称")
    private String userName;
    /** 用户类型 */
    @Schema(description = "用户类型")
    private Integer userType;
    /** 用户来源 */
    @Schema(description = "用户来源")
    private String channel;
    /** 邮件地址 */
    @Schema(description = "邮件地址")
    private String email;
    /** 加入时间 */
    @Schema(description = "加入时间")
    private LocalDate joinDate;
    /** 租户 */
    @Schema(description = "租户")
    private Integer tenantId;
    /** 状态（1正常 0停用） */
    @Schema(description = "状态（1正常 0停用）")
    private Integer status;
    /** 备注,扩展细腻 */
    @Schema(description = "备注,扩展细腻")
    private String remark;
}
