package com.github.platform.core.message.dingtalk.feign.command;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

/**
 * 钉钉群成员操作
 * @author: yxkong
 * @date: 2024/4/1 17:06
 * @version: 1.0
 */
@Data
@Builder
public class DingGroupUserCmd {
    /**群id*/
    @JsonProperty("open_conversation_id")
    private String openConversationId ;
    /**操作的用户id，以英文逗号分隔*/
    @JsonProperty("user_ids")
    private String userIds ;
}
