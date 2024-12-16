package com.github.platform.core.message.dingtalk.feign.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.platform.core.common.utils.StringUtils;
import lombok.Data;

/**
 * 创建群返回结果
 * @author: yxkong
 * @date: 2024/1/19 15:41
 * @version: 1.0
 */
@Data
public class DingCreateGroupDto{
    /**群id*/
    @JsonProperty("open_conversation_id")
    private String openConversationId;
    @JsonProperty("chat_id")
    private String chatId;
}
