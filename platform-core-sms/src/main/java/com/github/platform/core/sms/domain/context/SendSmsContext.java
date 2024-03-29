package com.github.platform.core.sms.domain.context;

import com.github.platform.core.sms.domain.constant.SmsType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 短信发送上下文
 * @author: yxkong
 * @date: 2023/3/1 3:10 PM
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SendSmsContext {
    /**
     * 验证码专用
     * @param mobile
     * @param tempNo
     * @param verifyCode
     * @param msgId
     */
    public SendSmsContext(String mobile, String tempNo,String verifyCode,String msgId) {
        this.mobile = mobile;
        this.tempNo = tempNo;
        this.params.put("verifyCode",verifyCode);
        this.smsType = SmsType.verfiyCode;
        this.msgId = msgId;
    }

    /** 消息唯一标识，幂等去重*/
    private String msgId;
    /**发送手机号*/
    private String  mobile;
    /**短信模板号*/
    private String  tempNo ;
    /**短信模板对应的参数*/
    private Map<String,Object> params = new HashMap<>();
    /**短信类型*/
    private SmsType smsType;

    /**
     * 验证码的时候，直接填此参数
     * @param verifyCode
     */
    public void setVerifyCode(String verifyCode){
        if (Objects.isNull(params)){
            params = new HashMap<>();
        }
        params.put("verifyCode",verifyCode);
    }
    public String getVerifyCode(){
        return params!= null ? String.valueOf(params.get("verifyCode")) : null;
    }
}
