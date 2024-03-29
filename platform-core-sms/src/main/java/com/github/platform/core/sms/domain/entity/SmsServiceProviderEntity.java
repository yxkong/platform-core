package com.github.platform.core.sms.domain.entity;

import com.github.platform.core.standard.annotation.AggregateRoot;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 短信服务商实体
 * @author: yxkong
 * @date: 2022/6/23 9:25 AM
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@AggregateRoot
public class SmsServiceProviderEntity extends SmsAccount {
    /**厂商编码，具备唯一性*/
    private String proNo;
    /**厂商的bean名称*/
    private String beanName;


}
