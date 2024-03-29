//package com.github.platform.core.sys.domain.gateway;
//
//import java.util.List;
//
//import com.github.platform.core.persistence.entity.sys.FormInfoDO;
//import com.github.platform.core.persistence.entity.sys.FormsDO;
//import com.github.platform.core.sys.domain.context.SendProccessContext;
//import com.github.platform.core.dingding.infra.rpc.external.dto.DingDeptDto;
//import com.github.platform.core.dingding.infra.rpc.external.dto.DingDeptUserDto.DingUserInfo;
//
///**
// * 〈钉钉网关〉
// *
// * @author
// * @create 2023/5/29
// * @since 1.0.0
// */
//public interface DingGateway {
//
//	/**
//	 * 获取钉钉用户token
//	 * @return
//	 */
//	String gettoken();
//
//	/**
//	 * 根据部门id查询部门用户
//	 * @param deptId
//	 * @return
//	 */
//	List<DingUserInfo> getDepartmentUserInfo(Long deptId);
//
//	/**
//	 * 查询所有部门
//	 * @param deptId 没有部门，默认为空，代表查询根部门
//	 * @param deptListAll
//	 * @return
//	 */
//	List<DingDeptDto> getDepartmentList(Long deptId, List<DingDeptDto> deptListAll);
//
//	/**
//	 * 根据用户id查询详情
//	 * @param userId
//	 * @return
//	 */
//	DingUserInfo getUserInfo(String userId);
//
//	/**
//	 * 创建表单
//	 * @param formsDO
//	 * @param formInfoList
//	 * @return
//	 */
//	String createForms(FormsDO formsDO, List<FormInfoDO> formInfoList);
//
//	/**
//	 * 发起钉钉审批流
//	 * @param context
//	 * @return
//	 */
//	String createProccess(SendProccessContext context);
//
//	/**
//	 * 创建群
//	 * @param userIds 群成员（逗号分割）
//	 * @param ownerUserId（创建者）
//	 * @param title 群名称
//	 * @return 群id（后续发消息要用）
//	 */
//	String createGroup(String userIds, String ownerUserId, String title);
//
//	/**
//	 * 发送钉钉消息
//	 * @param groupId（群id）
//	 * @param userIds（需要@的人）（可以为空，逗号分割）
//	 * @param atAll（是否需要@所有人）
//	 * @param message（发送的消息）
//	 * @return
//	 */
//	boolean sendMessage(String groupId, String userIds, boolean atAll, String message);
//
//}
