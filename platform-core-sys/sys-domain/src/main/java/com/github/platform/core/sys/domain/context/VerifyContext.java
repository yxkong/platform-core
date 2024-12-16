package com.github.platform.core.sys.domain.context;

import com.github.platform.core.sys.domain.constant.VerifyTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author: yxkong
 * @date: 2023/1/5 10:04 下午
 * @version: 1.0
 */
@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VerifyContext {
    VerifyTypeEnum verifyType;
    String verifyCode;
    String verifySeq;
}
