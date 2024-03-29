package com.github.platform.core.sms.infra.rpc.dto.ctyun;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 发送短信返回实体
 * @author: yxkong
 * @date: 2023/3/1 12:02 PM
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SendSmsDto {
    private String code;
    private String message;
    private String requestId;
}
