package com.github.platform.core.dingtalk.domain.gateway;

import com.github.platform.core.dingtalk.domain.constant.DingUserTypeEnum;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

/**
 * 钉钉im服务
 * @author: yxkong
 * @date: 2024/2/27 10:45
 * @version: 1.0
 */
public interface IDingTalkIMGateway {
    /**
     * 工作通知：文本消息
     * @param userList
     * @param text 消息内容
     * @return
     */
    boolean workNoticeText(List<String> userList,String text);

    /**
     * 工作通知，markdown消息
     * @param userList
     * @param title 消息标题
     * @param text 消息内容
     * @return
     */
    boolean workNoticeMarkDown(List<String> userList,String title,String text);
    /**
     * 创建群
     * @param userList 群成员
     * @param ownerUserId（创建者）
     * @param title 群名称
     * @param subAdminList 子管理员id
     * @return 群id（后续发消息要用）
     */
    String createGroup(List<String> userList, String ownerUserId, String title, List<String> subAdminList);

    /**
     * 发送markdown消息，@所有人
     * @param groupId  群id
     * @param title 消息标题
     * @param markdown markdown 文本
     * @return
     */
    boolean sendMarkdownMessage(String groupId, String title, String markdown);

    /**
     * 指定用户类型，发送 markdown消息
     * @param groupId  群id
     * @param title 消息标题
     * @param markdown markdown 文本
     * @param users  @用户
     * @param type 用户类型
     * @return
     */
    boolean sendMarkdownMessage(String groupId, String title, String markdown, List<String> users, DingUserTypeEnum type);

    /**
     * 群人员操作
     * @param groupId
     * @param users
     * @param isAdd  是否添加
     * @return
     */
    Pair<Boolean,String> groupUserOpt(String groupId, List<String> users, Boolean isAdd);
}
