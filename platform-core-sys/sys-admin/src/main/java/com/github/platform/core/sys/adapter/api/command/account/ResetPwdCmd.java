package com.github.platform.core.sys.adapter.api.command.account;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * 重置密码
 *
 * @author: yxkong
 * @date: 2022/12/30 4:36 PM
 * @version: 1.0
 */
@Schema(name = "ResetPwdCmd",title = "重置密码")
@Data
@NoArgsConstructor
public class ResetPwdCmd {
    /**
     * 登陆名称
     */
    @NotBlank(message = "登录账户不能为空")
    @Schema(description ="登录名")
    private String loginName;
}
