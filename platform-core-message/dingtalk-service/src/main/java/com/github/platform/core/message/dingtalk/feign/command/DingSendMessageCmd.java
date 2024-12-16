package com.github.platform.core.message.dingtalk.feign.command;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DingSendMessageCmd {

    /**群id*/
    @JsonProperty("target_open_conversation_id")
    private String targetOpenConversationId;

    /**消息模板id 文本类型，支持图片，按钮（后续对接）*/
    @Builder.Default
    @JsonProperty("msg_template_id")
    private String msgTemplateId = "inner_app_template_markdown";

    /**消息模板内容替换参数，普通文本类型,取值为Json格式的字符串*/
    @JsonProperty("msg_param_map")
    private String msgParamMap;
    /**图片消息模板替换参数，普通文本类型,取值为Json格式的字符串*/
    @JsonProperty("msg_media_id_param_map")
    private String msgMediaIdParamMap;

    @JsonProperty("receiver_user_ids")
    private String receiverUserIds;
    @JsonProperty("receiver_union_ids")
    private String receiverUnionIds;
    /** @人的userid列表,最多支持50人 */
    @JsonProperty("at_users")
    private String atUsers;
    /** @人的userid列表,最多支持50人 */
    @JsonProperty("at_mobiles")
    private String atMobiles;
    /**是否@所有人*/
    @JsonProperty("is_at_all")
    private boolean isAtAll;
    /**机器人（群模板里面自带的，可以自己设置）*/
    @JsonProperty("robot_code")
    private String robotCode ;
}


