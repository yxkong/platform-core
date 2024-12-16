package com.github.platform.core.sys.adapter.api.command.account;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * 修改密码
 *
 * @author: yxkong
 * @date: 2022/12/30 4:36 PM
 * @version: 1.0
 */
@Schema(name = "ModifyPwdCmd",title = "修改密码")
@Data
@NoArgsConstructor
public class ModifyPwdCmd {
    /**
     * 旧密码
     */
    @NotBlank(message = "旧密码不能为空")
    @Schema(description ="旧密码")
    private String oldPwd;

    /**
     * 新密码
     */
    @NotBlank(message = "新密码不能为空")
    @Length(min = 6, max = 12, message = "用户密码应为6~12位(数字+字母+特殊字符串)")
    @Schema(description ="新密码")
    private String newPwd;

}
