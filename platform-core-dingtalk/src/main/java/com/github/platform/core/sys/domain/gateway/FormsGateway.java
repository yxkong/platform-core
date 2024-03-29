//package com.github.platform.core.sys.domain.gateway;
//
//import java.util.List;
//
//import com.github.platform.core.persistence.entity.sys.FormApproverDO;
//import com.github.platform.core.persistence.entity.sys.FormInfoDO;
//import com.github.platform.core.persistence.entity.sys.FormsDO;
//import com.github.platform.core.sys.domain.context.SendProccessContext;
//
///**
// * 表单网关
// * @author Administrator
// *
// */
//public interface FormsGateway {
//
//	/**
//	 * 表单更新或者新增
//	 * @param formsDO
//	 * @param formInfoList
//	 * @param formAppList
//	 * @return
//	 */
//	FormsDO addOrUpdateForm(FormsDO formsDO, List<FormInfoDO> formInfoList, List<FormApproverDO> formAppList);
//
//	/**
//	 * 根据表单code查询
//	 * @param formCode
//	 * @return
//	 */
//	FormsDO findFormsByFormCode(String formCode);
//
//	/**
//	 * 根据表单code查询
//	 * @param formCode
//	 * @return
//	 */
//	List<FormApproverDO> findFormsApproverByFormCode(String formCode);
//
//	/**
//	 * 根据表单code查询
//	 * @param formCode
//	 * @return
//	 */
//	List<FormInfoDO> findFormsInfoByFormCode(String formCode);
//
//	int updateFormsStatusById(Long id, int status);
//
//	/**
//	 * insert 发送记录
//	 * @param context
//	 * @param processId
//	 */
//	void inserProcessLog(SendProccessContext context, String processId);
//}
