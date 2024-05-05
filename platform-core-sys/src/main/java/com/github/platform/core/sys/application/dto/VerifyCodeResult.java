package com.github.platform.core.sys.application.dto;

import com.github.platform.core.sys.domain.constant.VerifyTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * @author: yxkong
 * @date: 2022/4/26 8:05 PM
 * @version: 1.0
 */
@Data
@Builder
public class VerifyCodeResult {
    @Schema(description ="验证码类型")
    @Builder.Default
    private VerifyTypeEnum verifyType = VerifyTypeEnum.CAPTCHA;
    @Schema(description ="验证流水号")
    private String verifySeq;
    @Schema(description ="图片base64数据")
    private String images;
    @Schema(description ="验证码过期时间")
    private long expireTime;

}
