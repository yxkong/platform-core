package com.github.platform.core.sys.adapter.api.command.account;

import com.github.platform.core.sys.adapter.api.command.TenantBase;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

/**
 * 个人信息修改
 *
 * @author: yxkong
 * @date: 2022/12/30 9:36 PM
 * @version: 1.0
 */
@Schema(name = "UserProfileCmd",title = "个人信息修改")
@Data
@NoArgsConstructor
public class UserProfileCmd extends TenantBase {

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
     * 手机号,11位
     */
    @NotBlank(message = "手机号码不能为空")
    @Length(min = 11, max = 11, message = "手机号码长度必须是11位")
    @Schema(description ="手机号码")
    private String mobile;
    /** 头像 */
    @Schema(description = "头像")
    protected String avatar;
    /** 接口密钥 */
    @Schema(description = "接口密钥")
    protected String secretKey;
}
