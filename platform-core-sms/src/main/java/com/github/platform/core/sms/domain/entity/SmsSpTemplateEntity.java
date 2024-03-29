package com.github.platform.core.sms.domain.entity;

import com.github.platform.core.standard.annotation.AggregateRoot;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 厂商模板实体
 * @author: yxkong
 * @date: 2023/3/21 11:11 AM
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@AggregateRoot
public class SmsSpTemplateEntity extends SmsServiceProviderEntity {

    /**签名状态：0，未申请，1申请成功，2，申请中  -1，申请失败*/
    private Integer signStatus;
    /**厂商模板对应的状态,0，未申请，1申请成功，2申请中  -1，申请失败*/
    private Integer tempStatus;
    /**状态，1启用，0禁用*/
    private Integer status;
    /**厂商对应的模板id*/
    private String tempId;
    /**模板编号*/
    private String tempNo;
    /**
     * 模板内容
     */
    private String template;

}
