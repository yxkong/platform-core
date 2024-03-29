package com.github.platform.core.sys.adapter.api.command.account;

import com.github.platform.core.sys.adapter.api.command.VerifyBase;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

/**
 * 普通登录请求
 * @author: yxkong
 * @date: 2022/4/26 2:10 PM
 * @version: 1.0
 */
@Schema(name = "NormalLoginCmd",title = "登录请求")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class NormalLoginCmd extends VerifyBase {
    /**
     * 登录名
     */
    @NotEmpty(message = "登录名不能为空")
    @Length(min = 2, max = 20, message = "用户名长度2-20")
    @Schema(description ="登录名")
    private String loginName;

    /**
     * 密码
     */
    @NotEmpty(message = "密码不能为空")
    @Schema(description ="密码")
    private String pwd;
}
