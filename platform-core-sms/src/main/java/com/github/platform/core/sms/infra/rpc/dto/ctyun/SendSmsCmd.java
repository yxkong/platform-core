package com.github.platform.core.sms.infra.rpc.dto.ctyun;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 发送短信请求实体
 * @author: yxkong
 * @date: 2023/3/1 11:57 PM
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SendSmsCmd {

    /**
     * 系统规定参数，固定
     */
    private String action = "SendSms";
    /**
     * 接收短信的手机号码，格式：国内短信：无任何前缀的11位手机号码
     */
    private String phoneNumber;

    /**
     * 短信签名名称
     */
    private String signName;

    /**
     * 短信模板ID
     */
    private String templateCode;

    /**
     * 短信模板变量对应的实际值，JSON格式。说明：如果JSON中需要带换行符，请参照标准的JSON协议处理
     */
    private String templateParam;

    /**
     * 上行短信扩展码，上行短信，指发送给通信服务提供商的短信，用于定制某种服务、完成查询，或是办理某种业务等，需要收费的按运营商普通短信资费进行扣费
     */
    private String extendCode = "";

    /**
     * 客户自带短信标识，在状态报告中会原样返回
     */
    private String sessionId;
}
