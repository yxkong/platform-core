package com.github.platform.core.sms.domain.entity;

import lombok.Builder;
import lombok.Data;

/**
 * 短信结果实体
 * @author: hdy
 * @date: 2022/6/29 10:27 AM
 * @version: 1.0
 */
@Data
@Builder
public class SendSmsResultEntity {
	/**发送状态   2，已发送，1 发送成功，-1 发送失败 */
	private int status = 2;

    /**三方唯一id,只有成功才会有*/
    private String msgId;

    /**错误具体的信息*/
    private String errorMsg;
    /**之所以在这返回，是因为每个厂商需要的id不一样*/
    private String templateId;

    public boolean isSuc(){
        if(status == 1){
            return true;
        }
        return false;
    }

}
