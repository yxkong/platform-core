package com.github.platform.core.message.dingtalk.service;

import com.github.platform.core.message.dingtalk.constant.DingMessageTemplateTypeEnum;
import com.github.platform.core.message.dingtalk.constant.DingUserTypeEnum;
import com.github.platform.core.message.dingtalk.feign.dto.DingAccessUserDto;
import com.github.platform.core.message.dingtalk.feign.dto.DingDeptDto;
import com.github.platform.core.message.dingtalk.feign.dto.DingUserDto;
import org.apache.commons.lang3.tuple.Pair;

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
     * 获取应用访问token
     * @return
     */
    String getAppAccessToken();

    /**
     * 获取访问用户信息
     * @return
     */
    DingAccessUserDto getUserAccessUserInfo(String authCode);

    /**
     * 根据手机号获取用户的userId
     * @param mobile
     * @return
     */
    String getUserIdByMobile(String mobile);

    /**
     * 查询指定部门下所有部门
     * @param deptId 传null查全部
     * @return
     */
    List<DingDeptDto> getAllDept(Long deptId);
    /**
     * 获取钉钉部门用户
     * @param deptId 指定组织所有的部门
     * @return
     */
    List<DingUserDto> getDeptUsers(Long deptId);

    /**
     * 获取钉钉用户信息
     * @param dingUserId 钉钉用户id
     * @return
     */
    DingUserDto getUserInfo(String dingUserId);

    /**
     * 工作通知：文本消息
     * @param userList 用户列表
     * @param text 通知文本
     * @return
     */

    boolean workNoticeText(List<String> userList, String text);

    /**
     * 工作通知：markdown
     * @param userList 用户列表
     * @param text 通知内容
     * @return
     */
    boolean workNoticeMarkDown(List<String> userList,String title, String text);

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

    /**
     * 群人员操作
     * @param groupId 群id
     * @param users  群用户
     * @param isAdd  是否添加
     * @return
     */
    Pair<Boolean,String> groupUserOpt(String groupId, List<String> users, Boolean isAdd);



}
