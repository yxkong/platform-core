package com.github.platform.core.message.dingtalk.feign.command;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DingCreateGroupCmd {
    /**（必填）群名称*/
    private String title;
    /**（必填）群模板ID(钉钉后台拿到的）*/
    @JsonProperty("template_id")
    private String templateId ;
    /**（必填）群主*/
    @JsonProperty("owner_user_id")
    private String ownerUserId;
    /**（非必填）群成员userid列表。逗号分割，最多999个*/
    @JsonProperty("user_ids")
    private String userIds;
    /**（非必填） 群管理员列表*/
    @JsonProperty("subadmin_ids")
    private String subAdminIds;
    /**(非必填) 建群去重的业务ID，由接口调用方指定*/
    private String uuid;
    /**（非必填）群头像，得先商城*/
    private String icon;
    /** @all 权限 0 默认，所有人都可以@all， 1 仅群主可以@all*/
    @JsonProperty("mention_all_authority")
    private int mentionAllAuthority;
    /** 新成员是否可查看聊天历史消息, 0默认，不可以查看历史消息，1可以查看*/
    @JsonProperty("show_history_type")
    private int showHistoryType;
    /** 入群是否需要验证 0 默认不严重，1 入群验证*/
    @JsonProperty("validation_type")
    private int validationType;
    private int searchable;
    @JsonProperty("chat_banned_type")
    private int chatBannedType;
    @JsonProperty("management_type")
    private int managementType;
    @JsonProperty("only_admin_can_ding")
    private int onlyAdminCanDing;
    @JsonProperty("all_members_can_create_mcs_conf")
    private int allMembersCanCreateMcsConf;
    @JsonProperty("all_members_can_create_calendar")
    private int allMembersCanCreateCalendar;
    @JsonProperty("group_email_disabled")
    private int groupEmailDisabled;
    @JsonProperty("only_admin_can_set_msg_top")
    private int onlyAdminCanSetMsgTop;
    @JsonProperty("add_friend_forbidden")
    private int addFriendForbidden;
    @JsonProperty("group_live_switch")
    private int groupLiveSwitch;
    @JsonProperty("members_to_admin_chat")
    private int membersToAdminChat;
}
