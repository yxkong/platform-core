package com.github.platform.core.dingtalk.infra.rpc.external.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 钉钉发送消息返回
 * @author: yxkong
 * @date: 2024/1/19 16:21
 * @version: 1.0
 */
@Data
public class DingSendMessageDto extends DingResultBean{
    @JsonProperty("open_msg_id")
    private String openMsgId;
}
