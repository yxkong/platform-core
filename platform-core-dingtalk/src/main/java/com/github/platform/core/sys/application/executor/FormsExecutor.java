//package com.github.platform.core.sys.application.executor;
//
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//import javax.annotation.Resource;
//
//import com.github.platform.core.sys.adapter.api.command.FormSendProCmd;
//import com.github.platform.core.sys.domain.context.SendProccessContext;
//import org.springframework.stereotype.Service;
//
//import com.alibaba.cloud.commons.lang.StringUtils;
//import com.github.platform.core.persistence.entity.sys.FormApproverDO;
//import com.github.platform.core.persistence.entity.sys.FormInfoDO;
//import com.github.platform.core.persistence.entity.sys.FormsDO;
//import com.github.platform.core.standard.entity.dto.ResultBean;
//import com.github.platform.core.standard.util.ResultBeanUtil;
//import com.github.platform.core.sys.adapter.api.command.FormAddOrUpdateCmd;
//import com.github.platform.core.sys.adapter.api.convert.FormsConvert;
//import com.github.platform.core.sys.domain.gateway.DingGateway;
//import com.github.platform.core.sys.domain.gateway.FormsGateway;
//
///**
// * 表单
// *
// * @author hdy
// * @create 2023/6/29 下午2:16
// * @desc
// */
//@Service
//public class FormsExecutor {
//    @Resource
//    private FormsGateway formsGateway;
//
//
//
//    @Resource
//    private DingGateway dingGateway;
//
//
//	/**
//	 * 更新或者初始化forms表单
//	 * @param formsDO
//	 * @param formInfoList
//	 * @param formAppList
//	 * @return
//	 */
//	public ResultBean addOrUpdateForms(FormsDO formsDO, List<FormInfoDO> formInfoList, List<FormApproverDO> formAppList) {
//
//    	//更新库表
//    	formsDO = formsGateway.addOrUpdateForm(formsDO, formInfoList, formAppList);
//    	return ResultBeanUtil.success();
//    }
//
//    /**
//     * 激活表单，调用三方真实创建表单，并保存code
//     * @param formCode
//     * @return
//     */
//    public ResultBean activeForms(String formCode){
//    	FormsDO formsDO = formsGateway.findFormsByFormCode(formCode);
//    	if(formsDO == null || formsDO.getStatus() != 0){
//    		return ResultBeanUtil.fail("forms无数据或者状态非初始化");
//    	}
//    	List<FormInfoDO> formInfoList = formsGateway.findFormsInfoByFormCode(formCode);
//    	//调用三方（钉钉）创建流程，返回三方的流程code
//    	String proccessCode = getProccessCode(formsDO, formInfoList);
//    	if(StringUtils.isBlank(proccessCode)){
//    		return ResultBeanUtil.fail("调用三方创建表单异常");
//    	}
//    	//更新表
//    	formsGateway.updateFormsStatusById(formsDO.getId(), 1);
//    	return ResultBeanUtil.success();
//
//    }
//    /**
//     * 调用三方创建表单，获取流程code
//     * @param formsDO
//     * @param formInfoList
//     * @return
//     */
//    private String getProccessCode(FormsDO formsDO, List<FormInfoDO> formInfoList){
//    	String proccessCode = "";
//    	//调用渠道
//    	switch (formsDO.getFormsChannel()) {
//		case "dingding":
//			proccessCode = dingGateway.createForms(formsDO, formInfoList);
//			break;
//		default:
//			break;
//		}
//    	return proccessCode;
//    }
//
//	public ResultBean sendApprovalProcess(FormSendProCmd cmd){
//		String formCode = cmd.getFormCode();
//		FormsDO formsDO = formsGateway.findFormsByFormCode(cmd.getFormCode());
//		if(formsDO == null || formsDO.getStatus() != 1){
//			return ResultBeanUtil.fail("forms无数据或者表单流程未启用");
//		}
//		Map formInfoMap = cmd.getFormInfoMap();
//		//查询表单信息
//		List<FormInfoDO> formsInfoList = formsGateway.findFormsInfoByFormCode(formCode);
//		List<SendProccessContext.ProcessForm> forms = formsInfoList.stream().map(info -> SendProccessContext.ProcessForm.builder()
//				.name(info.getName()).value((String) formInfoMap.get(info.getName())).build()).collect(Collectors.toList());
//		List<SendProccessContext.ProcessApprover> approvers = cmd.getApprovers().stream().map(record -> SendProccessContext.ProcessApprover.builder()
//				.approver(record.getApprover().stream().collect(java.util.stream.Collectors.joining(",")))
//				.type(record.getType().getType()).build()).collect(Collectors.toList());
//
//		//审核人信息
//		List<FormApproverDO> approverList = formsGateway.findFormsApproverByFormCode(formCode);
//		String ccUser = cmd.getCcUser();
//		ccUser = StringUtils.isEmpty(ccUser)? formsDO.getCcUser(): ccUser;
//		SendProccessContext context = SendProccessContext.builder().formCode(formsDO.getFormCode()).
//				processCode(formsDO.getProcessCode()).ccUser(ccUser).forms(forms).approvers(approvers).build();
//		String proccessId = dingGateway.createProccess(context);
//		if(StringUtils.isBlank(proccessId)){
//			return ResultBeanUtil.fail("发起审批流异常");
//		}
//		//增加日志表
//		formsGateway.inserProcessLog(context, proccessId);
//		return ResultBeanUtil.success();
//	}
//
//}
