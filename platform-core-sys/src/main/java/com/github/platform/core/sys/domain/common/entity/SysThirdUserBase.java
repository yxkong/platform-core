package com.github.platform.core.sys.domain.common.entity;

import com.github.platform.core.common.entity.BaseAdminEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
* 三方用户模型实体
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-15 10:55:06.505
* @version 1.0
*/
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class SysThirdUserBase extends BaseAdminEntity   {
    /** 绑定用户id，系统用户 */
    @Schema(description = "绑定用户id，系统用户")
    protected Long userId;
    /** 三方唯一标识，也是三方的用户id */
    @Schema(description = "三方唯一标识，也是三方的用户id")
    protected String openId;
    /** 绑定以后的id，用户在对应的开发中的唯一id */
    @Schema(description = "绑定以后的id，用户在对应的开发中的唯一id")
    protected String unionId;
    /** 用户名称 */
    @Schema(description = "用户名称")
    @NotEmpty(message="用户名称（userName）不能为空")
    protected String userName;
    /** 用户类型,1正式，0外包 */
    @Schema(description = "用户类型,1正式，0外包")
    protected Integer userType;
    /** 用户来源 */
    @Schema(description = "用户来源")
    protected String channel;
    /** 邮件地址 */
    @Schema(description = "邮件地址")
    protected String email;
    /** 手机号 */
    @Schema(description = "手机号")
    protected String mobile;
    /** 预留字段1 */
    @Schema(description = "预留字段1")
    protected String extend1;
    /** 预留字段2 */
    @Schema(description = "预留字段2")
    protected String extend2;
    /** 加入时间 */
    @Schema(description = "加入时间")
    protected LocalDate joinDate;
}
