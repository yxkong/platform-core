package com.github.platform.core.sms.domain.entity;

import com.github.platform.core.standard.annotation.DomainEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 自定义短信模板实体
 * @author: yxkong
 * @date: 2023/3/1 6:42 PM
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@DomainEntity
public class SmsTemplateEntity {
    private Long id;
    /**
     * 模板编号
     */
    private String tempNo;
    /**短信签名*/
    private String signName;
    /**
     * 模板内容
     */
    private String content;
    /**
     * 路由类型
     * */
    private String routeType;
    /**
     * 指定厂商
     * */
    private String proNo;
    /**
     * 状态，1启用，0禁用
     */
    private Integer status;
    /**
     * 可用时间类型：0，无限制，1自定义
     */
    private Integer useTimeType;
    /**
     * 工作时间可发送区间 00:00:00 - 23:59:59
     */
    private String workInterval;
    /**
     * 节假日时间可发送区间 00:00:00 - 23:59:59
     */
    private String holidayInterval;

    public boolean isValid(){
        if (status == 1 ){
            return true;
        }
        return false;
    }
}
