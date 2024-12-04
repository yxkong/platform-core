package com.github.platform.core.sys.adapter.api.command.account;

import com.github.platform.core.sys.adapter.api.command.VerifyBase;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * 短信验证码登录请求
 * @author: yxkong
 * @date: 2023/4/4 2:42 PM
 * @version: 1.0
 */
@Schema(name = "SmsLoginCmd",title = "短信验证码登录请求")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class SmsLoginCmd extends VerifyBase {
    /**
     * 登录手机号
     */
    @NotBlank(message = "手机号码不能为空")
    @Length(min = 11, max = 11, message = "手机号码长度必须是11位")
    @Schema(description ="登录手机号")
    private String mobile;
}
