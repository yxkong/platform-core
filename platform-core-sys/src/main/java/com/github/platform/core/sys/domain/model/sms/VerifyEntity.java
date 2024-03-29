package com.github.platform.core.sys.domain.model.sms;

import com.github.platform.core.standard.annotation.DomainEntity;
import com.github.platform.core.sys.domain.constant.VerifyTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 验证实体
 * @author: yxkong
 * @date: 2023/1/4 11:21 AM
 * @version: 1.0
 */
@DomainEntity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VerifyEntity {
    /**验证的流水号,全局唯一*/
    private String verifySeq;
    /**验证码*/
    private String verifyCode;
    /**供应商，为滑块的时候意义*/
    private String supplier;
    /**请求ip*/
    private String requestIp;
    /**验证类型*/
    private VerifyTypeEnum verifyType;
}
