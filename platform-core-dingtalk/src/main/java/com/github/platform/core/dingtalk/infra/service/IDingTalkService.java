package com.github.platform.core.dingtalk.infra.service;

import com.github.platform.core.dingtalk.domain.constant.DingMessageTemplateTypeEnum;
import com.github.platform.core.dingtalk.domain.constant.DingUserTypeEnum;
import com.github.platform.core.dingtalk.infra.rpc.external.dto.DingDeptDto;
import com.github.platform.core.dingtalk.infra.rpc.external.dto.DingUserDto;

import java.util.List;
import java.util.Map;

/**
 * 钉钉服务
 * @author: yxkong
 * @date: 2024/1/18 17:42
 * @version: 1.0
 */
public interface IDingTalkService {

    /**
     * 获取访问token
     * @return
     */
    String getAccessToken();

    /**
     * 查询指定部门下所有部门
     * @param deptId 传null查全部
     * @return
     */
    List<DingDeptDto> getALLDept(Long deptId);
    /**
     * 获取钉钉部门用户
     * @param deptId 指定组织所有的部门
     * @return
     */
    List<DingUserDto> getDeptUsers(Long deptId);

    /**
     * 获取钉钉用户信息
     * @param dingUserId
     * @return
     */
    DingUserDto getUserInfo(String dingUserId);

    /**
     * 创建群
     * @param templateId 模板id
     * @param userList 群成员
     * @param ownerUserId（创建者）
     * @param title 群名称
     * @param subAdminList 子管理员id
     * @return 群id（后续发消息要用）
     */
    String createGroup(String templateId,List<String> userList, String ownerUserId, String title,List<String> subAdminList);

    /**
     * 发送钉钉消息
     * @param groupId（群id）
     * @param robotCode 群机器人编码
     * @param users（需要@的人）（可以为空，逗号分割）
     * @param userType 用户的类型 ,可以是手机号，也可以是钉钉用户id
     * @param atAll（是否需要@所有人）
     * @param templateType 模板类型
     * @param map（发送的消息）
     * @return
     */
    boolean sendMessage(String groupId, String robotCode, List<String> users, DingUserTypeEnum userType, boolean atAll, DingMessageTemplateTypeEnum templateType, Map<String,String> map);

}
