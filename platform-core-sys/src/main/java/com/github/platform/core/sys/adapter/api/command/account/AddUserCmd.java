package com.github.platform.core.sys.adapter.api.command.account;

import com.github.platform.core.sys.adapter.api.command.TenantBase;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * 密码注册
 *
 * @author: yxkong
 * @date: 2022/12/30 4:36 PM
 * @version: 1.0
 */
@Schema(name = "AddUserCmd",title = "密码注册")
@Data
@NoArgsConstructor
public class AddUserCmd extends TenantBase {
    /**
     * 登陆名称
     */
    @NotBlank(message = "登录账户不能为空")
    @Schema(description ="登陆名称")
    private String loginName;

    /**
     * 部门ID
     */
    @Schema(description ="部门ID")
    private String deptId;

    /**
     * 用户姓名,长度2-20位
     */
    @NotBlank(message = "用户名不能为空")
    @Length(min = 2, max = 20, message = "用户名长度2-20")
    @Schema(description ="用户姓名")
    private String userName;
    /**
     * 用户邮箱
     */
    @Schema(description ="邮箱")
    private String email;
    /**
     * 登陆密码
     */
    @Schema(description ="登陆密码")
    private String pwd;

    /**
     * 手机号,11位
     */
    @NotBlank(message = "手机号码不能为空")
    @Length(min = 11, max = 11, message = "手机号码长度必须是11位")
    @Schema(description ="手机号码")
    private String mobile;

    /**
     * 用户状态
     */
    @Schema(description ="用户状态")
    private String status;

    /**
     * 用户角色
     */
    @Schema(description ="用户角色")
    private String[] roleKeys;

}
