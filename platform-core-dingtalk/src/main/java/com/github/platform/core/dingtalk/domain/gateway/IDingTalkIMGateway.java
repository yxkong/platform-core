package com.github.platform.core.dingtalk.domain.gateway;

import java.util.List;

/**
 * 钉钉im服务
 * @author: yxkong
 * @date: 2024/2/27 10:45
 * @version: 1.0
 */
public interface IDingTalkIMGateway {
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
     * 发送markdown消息
     * @param groupId
     * @param title
     * @param markdown
     * @return
     */
    boolean sendMarkdownMessage(String groupId, String title, String markdown);


    /**
     * 发送流程流转消息
     * @param groupId
     * @param title
     * @param nodeKey
     * @param desc
     * @return
     */
    boolean sendProcessMessage(String groupId,String title, String nodeKey,String desc);
}
