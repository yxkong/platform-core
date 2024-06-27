package com.github.platform.core.sys.domain.common.entity;

import com.github.platform.core.common.entity.BaseAdminEntity;
import com.github.platform.core.standard.util.LocalDateTimeUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
* 用户信息模型实体
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-15 10:55:06.651
* @version 1.0
*/
@Data
@SuperBuilder
@NoArgsConstructor
public class SysUserBase extends BaseAdminEntity   {
    /** 部门ID */
    @Schema(description = "部门ID")
    @NotNull(message="部门ID（deptId）不能为空")
    protected Long deptId;
    /** 登录账号 */
    @Schema(description = "登录账号")
    protected String loginName;
    /** 用户姓名 */
    @Schema(description = "用户姓名")
    protected String userName;
    /** 手机号码 */
    @Schema(description = "手机号码")
    @NotEmpty(message="手机号码（mobile）不能为空")
    protected String mobile;
    /** 登录密码 */
    @Schema(description = "登录密码")
    @NotEmpty(message="登录密码（pwd）不能为空")
    protected String pwd;
    /** 密码盐值 */
    @Schema(description = "密码盐值")
    @NotEmpty(message="密码盐值（salt）不能为空")
    protected String salt;
    /** 用户邮箱 */
    @Schema(description = "用户邮箱")
    @NotEmpty(message="用户邮箱（email）不能为空")
    protected String email;
    /** 来源渠道 */
    @Schema(description = "来源渠道")
    @NotEmpty(message="来源渠道（channel）不能为空")
    protected String channel;
    /** 头像 */
    @Schema(description = "头像")
    @NotEmpty(message="头像（avatar）不能为空")
    protected String avatar;
    /** 接口密钥 */
    @Schema(description = "接口密钥")
    protected String secretKey;
    @Schema(description = "岗位编码")
    protected String postCode;
    @Schema(description = "上次修改密码时间")
    protected LocalDateTime lastModifyPwdTime;
    /** 删除状态 */
    @Schema(description = "删除状态")
    protected Integer deleted;
}
