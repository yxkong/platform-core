//package com.github.platform.core.sys.infra.gateway;
//
//import java.util.List;
//
//import javax.annotation.Resource;
//
//import com.github.platform.core.common.utils.JsonUtils;
//import com.github.platform.core.persistence.entity.sys.FormProcessLogDO;
//import com.github.platform.core.persistence.mapper.sys.DingFormInfoMapper;
//import com.github.platform.core.persistence.mapper.sys.FormProcessLogMapper;
//import com.github.platform.core.sys.domain.context.SendProccessContext;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.alibaba.cloud.commons.lang.StringUtils;
//import com.github.platform.core.common.utils.IdWorker;
//import com.github.platform.core.persistence.entity.sys.FormApproverDO;
//import com.github.platform.core.persistence.entity.sys.FormInfoDO;
//import com.github.platform.core.persistence.entity.sys.FormsDO;
//import com.github.platform.core.persistence.mapper.sys.FormApproverMapper;
//import com.github.platform.core.persistence.mapper.sys.FormsMapper;
//import com.github.platform.core.standard.constant.ResultStatusEnum;
//import com.github.platform.core.standard.exception.ApplicationException;
//import com.github.platform.core.sys.domain.constant.ApproverTypeEnum;
//import com.github.platform.core.sys.domain.gateway.FormsGateway;
//
//import lombok.extern.slf4j.Slf4j;
//
///**
// * 用户网关
// *
// * @author: yxkong
// * @date: 2023/1/4 4:04 PM
// * @version: 1.0
// */
//@Slf4j
//@Service
//public class FormsGatewayImpl implements FormsGateway {
//    @Autowired
//    private FormsMapper formsMapper;
//
//    @Autowired
//    private DingFormInfoMapper dingFormInfoMapper;
//
//    @Autowired
//    private FormApproverMapper formApproverMapper;
//
//	@Autowired
//	private FormProcessLogMapper formProcessLogMapper;
//
//    @Resource
//    private IdWorker idWorker;
//
//    @Override
//    public FormsDO addOrUpdateForm(FormsDO formsDO, List<FormInfoDO> formInfoList, List<FormApproverDO> formAppList){
//    	formsDO = addOrUpdateForms(formsDO);
//    	addOrUpdateFormInfo(formsDO.getFormCode(), formInfoList);
//    	addOrUpdateFormApprover(formsDO.getFormCode(), formAppList);
//    	return formsDO;
//    }
//
//    /**
//     * 用户查询
//     *
//     * @param formsDO
//     * @return
//     */
//    private FormsDO addOrUpdateForms(FormsDO formsDO) {
//    	//新增或者更新主表
//    	if(StringUtils.isNotBlank(formsDO.getFormCode())){
//    		//更新
//    		FormsDO formsDb = formsMapper.findByFormCode(formsDO.getFormCode());
//    		if(formsDb == null || !formsDO.getFormCode().equals(formsDb.getFormCode())){
//    			throw new ApplicationException(ResultStatusEnum.BAD_REQUEST);
//    		}
//    		formsDO.setId(formsDb.getId());
//    		formsMapper.updateByPrimaryKeySelective(formsDO);
//    		return formsDO;
//    	}
//    	//新增
//		formsDO.setFormCode(idWorker.nextId()+"");
//		formsMapper.insertSelective(formsDO);
//    	return formsDO;
//    }
//
//    private List<FormInfoDO> addOrUpdateFormInfo(String formCode, List<FormInfoDO> list){
//    	if(list == null || list.size() == 0){
//    		throw new ApplicationException(ResultStatusEnum.BAD_REQUEST);
//    	}
//    	for (FormInfoDO formInfoDO : list) {
//    		formInfoDO.setFormCode(formCode);
//    		if(formInfoDO.getId() != null && formInfoDO.getId() != 0L){
//    			//更新
//				dingFormInfoMapper.updateByPrimaryKeySelective(formInfoDO);
//    		}else{
//				dingFormInfoMapper.insertSelective(formInfoDO);
//    		}
//		}
//    	return list;
//    }
//
//
//    private List<FormApproverDO> addOrUpdateFormApprover(String formCode, List<FormApproverDO> list){
//    	if(list == null || list.size() == 0){
//    		return null;
//    	}
//    	for (FormApproverDO record : list) {
//    		record.setFormCode(formCode);
//    		if(ApproverTypeEnum.optional.getType().equals(record.getApproverType())){
//    			record.setApprover(null);
//    		}
//    		if(record.getId() != null && record.getId() != 0L){
//    			//更新
//    			formApproverMapper.updateByPrimaryKeySelective(record);
//    		}else{
//    			record.setFormCode(formCode);
//    			formApproverMapper.insertSelective(record);
//    		}
//		}
//    	return list;
//    }
//
//	@Override
//	public FormsDO findFormsByFormCode(String formCode) {
//		return formsMapper.findByFormCode(formCode);
//	}
//
//	@Override
//	public List<FormApproverDO> findFormsApproverByFormCode(String formCode) {
//		return formApproverMapper.findByFormCode(formCode);
//	}
//
//	@Override
//	public List<FormInfoDO> findFormsInfoByFormCode(String formCode) {
//		return dingFormInfoMapper.findByFormCode(formCode);
//	}
//
//	@Override
//	public int updateFormsStatusById(Long id, int status) {
//		FormsDO record = new FormsDO();
//		record.setId(id);
//		record.setStatus(status);
//		return formsMapper.updateByPrimaryKeySelective(record);
//	}
//	@Override
//	public void inserProcessLog(SendProccessContext context, String processId){
//		FormProcessLogDO record = new FormProcessLogDO();
//		record.setProcessId(processId);
//		record.setFormsInfo(JsonUtils.toJson(context.getForms()));
//		record.setApprover(JsonUtils.toJson(context.getApprovers()));
//		record.setFormCode(context.getFormCode());
//		formProcessLogMapper.insertSelective(record);
//	}
//}
