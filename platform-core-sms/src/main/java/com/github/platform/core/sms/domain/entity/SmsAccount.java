package com.github.platform.core.sms.domain.entity;

import com.github.platform.core.standard.annotation.DomainValueObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 短信账户值对象
 * @author: yxkong
 * @date: 2022/6/23 9:22 AM
 * @version: 1.0
 */
@DomainValueObject
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SmsAccount {
    /**
     * 账户名称
     */
    private String accountName;
    /**
     * 解密后的密码
     */
    private String password;
    /**数据库里AES加密的密码*/
    private String encryptPwd;
    /**AES加密的盐值*/
    private String salt;

    public SmsAccount(String accountName, String password) {
        this.accountName = accountName;
        this.password = password;
    }
}
