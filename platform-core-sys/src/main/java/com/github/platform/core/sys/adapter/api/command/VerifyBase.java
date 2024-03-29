package com.github.platform.core.sys.adapter.api.command;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 验证实体，
 *  1，获取短信验证码时使用
 *    1.1 前端图形验证码  verifyType = captcha
 *    1.2 前端滑块验证 verifyType = sliding  并且 verifySeq 为滑块的id
 *  2，登录时候使用
 *    1.1 图形验证码登录  verifyType = captcha  verifySeq = 图形的唯一标识   verifyCode 为用户输入的图形验证码
 *    1.2 短信登录  verifyType = sms  verifyCode 为用户输入的短信验证码
 * @author: yxkong
 * @date: 2023/1/10 10:28 AM
 * @version: 1.0
 */
@Schema(name = "VerifyBase",title = "验证实体")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class VerifyBase  extends TenantBase{
    /**
     * 验证类型,默认sms
     */
    @Schema(description ="验证类型,默认sms")
    private String verifyType = "sms";

    /**
     * 验证码
     */
    @NotBlank(message = "验证码不能为空")
    @Schema(description ="验证码")
    private String verifyCode;

    /**
     * 验证码唯一标识
     */
    @NotNull(message = "验证码唯一标识不能为空")
    @Schema(description ="验证码唯一标识")
    private String verifySeq;
}
