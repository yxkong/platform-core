//package com.github.platform.core.sys.infra.gateway;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.TimeUnit;
//import java.util.stream.Collectors;
//
//import javax.annotation.Resource;
//
//import com.github.platform.core.auth.util.LoginUserInfoUtil;
//import com.github.platform.core.dingding.infra.rpc.external.command.DingCreateGroupCmd;
//import com.github.platform.core.dingding.infra.rpc.external.command.DingSendMessageCmd;
//import com.github.platform.core.dingding.infra.rpc.external.dto.*;
//import com.github.platform.core.persistence.entity.sys.DingUserInfoDO;
//import com.github.platform.core.sys.domain.context.SendProccessContext;
//import com.github.platform.core.sys.infra.convert.FormsInfraConvert;
//import com.github.platform.core.sys.infra.service.sys.IDingUserInfoService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.alibaba.cloud.commons.lang.StringUtils;
//import com.github.platform.core.persistence.entity.sys.FormInfoDO;
//import com.github.platform.core.persistence.entity.sys.FormsDO;
//import com.github.platform.core.standard.constant.ResultStatusEnum;
//import com.github.platform.core.standard.exception.ApplicationException;
//import com.github.platform.core.sys.domain.gateway.DingGateway;
//import com.github.platform.core.dingding.infra.rpc.external.DingApproveFeignClient;
//import com.github.platform.core.dingding.infra.rpc.external.DingIMFeignClient;
//import com.github.platform.core.dingding.infra.rpc.external.dto.DingDeptUserDto.DingUserInfo;
//import com.github.platform.core.dingding.infra.rpc.external.command.DingCreateFormsCmd;
//import com.github.platform.core.dingding.infra.rpc.external.command.DingCreateFormsCmd.FormComponents;
//import com.github.platform.core.dingding.infra.rpc.external.command.DingCreateFormsCmd.Props;
//import com.github.platform.core.dingding.infra.rpc.external.command.DingCreateProcessCmd;
//import com.github.platform.core.dingding.infra.rpc.external.command.DingCreateProcessCmd.Approver;
//import com.github.platform.core.dingding.infra.rpc.external.command.DingCreateProcessCmd.FormComponentValues;
//import org.springframework.data.redis.core.RedisTemplate;
//import lombok.extern.slf4j.Slf4j;
//
///**
// * 用户网关实现
// * @author Administrator
// *
// */
//@Slf4j
//@Service
//public class DingGatewayImpl implements DingGateway {
//
//	/**
//	 * 通用的系统标识
//	 */
//	String appkey = "dingqe9baw7vbprklahl";
//	String appsecret = "poJ8IrpbKnyenXa8w1juRdJf4iiRJOc1CHEgXy1OSifNBnOaBANlNEp8rYNws5OD";
//
//	@Autowired
//	private DingIMFeignClient dingOapiFeignClient;
//
//	@Autowired
//	private DingApproveFeignClient dingApiFeignClient;
//
//	@Autowired
//	private IDingUserInfoService dingUserInfoService;
//
//	@Resource(name="stringRedisTemplate")
//    private RedisTemplate redisTemplate;
//
//	@Resource
//	private FormsInfraConvert convert;
//
//
//	String dingToken = "dingToken";
//
//
//
//	@Override
//	public String gettoken() {
//		String token = (String) redisTemplate.opsForValue().get(dingToken);
//		if(StringUtils.isNotEmpty(token)){
//			return token;
//		}
//		DingTokenDto result = dingOapiFeignClient.gettoken(appkey, appsecret);
//		token = result.getAccess_token();
//		if(StringUtils.isNotEmpty(token)){
//			//钉钉有效时间为2小时，防止有问题，提前10分钟失效
//			redisTemplate.opsForValue().set(dingToken, token, 110, TimeUnit.SECONDS);
//	        return token;
//		}
//		throw new ApplicationException(ResultStatusEnum.BAD_REQUEST);
//    }
//
//
//	@Override
//	public List<DingUserInfo> getDepartmentUserInfo(Long deptId) {
//		String token = gettoken();
//		List<DingUserInfo> userList = new ArrayList<DingUserInfo>();
//    	DingDeptUserDto departmentUserVo = new DingDeptUserDto();
//    	departmentUserVo.setHas_more(true);
//    	departmentUserVo.setNext_cursor(0);
//    	while(departmentUserVo.isHas_more()){
//    		Map<String, Object> mapquery = new HashMap<String, Object>();
//    		mapquery.put("dept_id", deptId);
//    		mapquery.put("cursor", departmentUserVo.getNext_cursor());//最开始传0，后续传返回参数中的next_cursor值。
//    		mapquery.put("size", 2);//页数
//        	DingResultBean<DingDeptUserDto> result = dingOapiFeignClient.getDepartmentUserInfo(token, mapquery);
//        	if(!result.isSuc()){
//        		log.error("调用钉钉异常getDepartmentUserInfo{}", result);
//        	}
//        	departmentUserVo = result.getResult();
//        	userList.addAll(departmentUserVo.getList());
//		}
//		return userList;
//	}
//
//	@Override
//	public List<DingDeptDto> getDepartmentList(Long deptId, List<DingDeptDto> deptListAll){
//		String token = gettoken();
//		Map<String, Object> map= new HashMap<>();
//    	map.put("dept_id", deptId);
//		DingResultBean<List<DingDeptDto>> result = dingOapiFeignClient.getDepartmentList(token, map);
//		if(!result.isSuc()){
//    		log.error("调用钉钉异常getDepartmentList{}", result);
//    	}
//		List<DingDeptDto> deptList = result.getResult();
//		if(deptList == null || deptList.size() == 0){
//			return null;
//		}
//		deptListAll.addAll(deptList);
//		for (DingDeptDto deptVo : deptList) {
//			List<DingDeptDto> deptListVo = getDepartmentList(deptVo.getDept_id(), deptListAll);
//			/*if(deptListVo == null || deptListVo.size() == 0){
//				break;
//			}*/
//		}
//		return deptListAll;
//	}
//
//	@Override
//	public DingUserInfo getUserInfo(String userId) {
//		String token = gettoken();
//		Map<String, Object> map= new HashMap<>();
//    	map.put("userid", userId);
//    	DingResultBean<DingUserInfo> result = dingOapiFeignClient.getUserInfo(token, map);
//    	if(!result.isSuc()){
//    		log.error("调用钉钉异常getUserInfo{}", result);
//    	}
//    	return result.getResult();
//    }
//
//	@Override
//	public String createForms(FormsDO formsDO, List<FormInfoDO> formInfoList) {
//		List<FormComponents> formComponents = formInfoList.stream().map(info -> {
//			Props pop = Props.builder().label(info.getName()).build();
//			FormComponents f = FormComponents.builder().componentType(info.getType()).props(pop).build();
//			return f;
//		}).collect(Collectors.toList());
//    	DingCreateFormsCmd vo = DingCreateFormsCmd.builder().name(formsDO.getFormName()).description(formsDO.getFormDescribe()).
//    			processCode(formsDO.getProcessCode()).formComponents(formComponents).build();
//    	String token = gettoken();
//    	DingResultBean<DingFormDto> result = dingApiFeignClient.createForms(vo, token);
//        return result.getResult().getProcessCode();
//    }
//	@Override
//	public String createProccess(SendProccessContext context) {
//		//表单信息
//		List<FormComponentValues> formInfoList = convert.dingFormContentList(context.getForms());
//
//		DingUserInfoDO userInfoDO = dingUserInfoService.findByName(LoginUserInfoUtil.getLoginUserInfo().getUserName());
//		if(userInfoDO == null){
//			log.error("未查询到当前登录用户的钉钉信息，表单code={}，当前登录名{}",context.getFormCode(), LoginUserInfoUtil.getLoginUserInfo());
//			throw new ApplicationException(ResultStatusEnum.BAD_REQUEST);
//		}
//		//转换审批人
//		List<SendProccessContext.ProcessApprover> approverList = context.getApprovers();
//		List<Approver> approvers = approverList.stream().map( record ->
//						Approver.builder().actionType(record.getType())
//								.userIds(replaceUserInfo(record.getApprover()))
//								.build()).collect(Collectors.toList());
//    	DingCreateProcessCmd dto = DingCreateProcessCmd.builder().processCode(context.getProcessCode()).originatorUserId(userInfoDO.getUserId()).
//    			formComponentValues(formInfoList).deptId(userInfoDO.getDeptId())
//    			.approvers(approvers)
//    			.ccList(replaceUserInfo(context.getCcUser())).build();
//    	String token = gettoken();
//    	DingProcessInstanceDto result = dingApiFeignClient.processInstances(dto, token);
//        return result.getInstanceId();
//    }
//
//	@Override
//	public String createGroup(String userIds, String ownerUserId, String title) {
//		String token = gettoken();
//		DingCreateGroupCmd vo = DingCreateGroupCmd.builder().title(title).user_ids(userIds).owner_user_id(ownerUserId).build();
//		DingResultBean<Map> result = dingOapiFeignClient.createGroup(token, vo);
//		System.out.println(result);
//		if(result.isSuc()){
//			String groupId = (String)result.getResult().get("open_conversation_id");
//			return groupId;
//		}
//		return null;
//	}
//
//	@Override
//	public boolean sendMessage(String groupId, String userIds, boolean atAll, String message) {
//		String token = gettoken();
//		DingSendMessageCmd vo = DingSendMessageCmd.builder().msg_param_map("{\"content\": " +message+ "}")
//				.is_at_all(atAll).at_users(userIds).target_open_conversation_id(groupId).build();
//		DingResultBean result = dingOapiFeignClient.sendMessage(token, vo);
//		if(result.isSuc()){
//			return true;
//		}
//		return false;
//	}
//
//	/**
//	 * 替换审核人信息（替换为钉钉的）
//	 * @param appovers
//	 * @return
//	 */
//	private List<String> replaceUserInfo(String appovers){
//		if(StringUtils.isEmpty(appovers)){
//			return null;
//		}
//		String[] args = appovers.split(",");
//		List<String> list = new ArrayList<>();
//		for (String s : args){
//			DingUserInfoDO userInfoDO = dingUserInfoService.findByName(s);
//			if (userInfoDO == null){
//				log.error("发起钉钉审批流异常，未查询到钉钉审批人信息", s);
//				return null;
//			}
//			list.add(userInfoDO.getUserId());
//		}
//		return list;
//	}
//
//}
