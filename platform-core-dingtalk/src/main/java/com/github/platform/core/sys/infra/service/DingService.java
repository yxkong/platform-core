//package com.github.platform.core.sys.infra.service;
//
//import com.github.platform.core.persistence.entity.sys.DingDepartmentDO;
//import com.github.platform.core.persistence.entity.sys.DingUserInfoDO;
//import com.github.platform.core.sys.domain.gateway.DingGateway;
//import com.github.platform.core.sys.infra.convert.DingDingConvert;
//import com.github.platform.core.dingding.infra.rpc.external.dto.DingDeptDto;
//import com.github.platform.core.sys.infra.service.sys.IDingDepartmentService;
//import com.github.platform.core.sys.infra.service.sys.IDingUserInfoService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.Resource;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Objects;
//
///**
// * 用户领域服务
// *
// * @author: yxkong
// * @date: 2023/1/4 3:03 PM
// * @version: 1.0
// */
//@Service
//@Slf4j
//public class DingService {
//
//	@Autowired
//	private DingGateway dingGateway;
//
//	@Autowired
//	private IDingDepartmentService dingDepartmentService;
//
//	@Autowired
//	private IDingUserInfoService dingUserInfoService;
//
//	@Resource
//	private DingDingConvert convert;
//
//
//	/**
//	 * 初始化钉钉部门（纯insert）
//	 * @param deptId
//	 */
//    public void initDingDept(Long deptId) {
//    	if(Objects.isNull(deptId) || deptId == 0L){
//    		//只处理2021架构的
//    		deptId = 427508304L;
//    	}
//    	//查询钉钉部门
//    	List<DingDeptDto> deptList = dingGateway.getDepartmentList(deptId, new ArrayList<DingDeptDto>());
//    	if(deptList == null || deptList.size() == 0){
//    		return;
//    	}
//    	//转换类型
//    	List<DingDepartmentDO> list = convert.convertDept(deptList);
//    	//数据库批量插入
//    	int count = dingDepartmentService.insertBatch(list);
//    }
//
//
//    public void initDingUser(Long deptId){
//    	//查询数据库的部门
//    	List<DingDepartmentDO> deptList = dingDepartmentService.findListByDeptId(deptId);
//
//    	List<DingUserInfo> userList = new ArrayList<>();
//    	for (DingDepartmentDO deptDo : deptList) {
//    		List<DingUserInfo> list =  dingGateway.getDepartmentUserInfo(deptDo.getDeptId());
//    		if(Objects.nonNull(list) && list.size() >0){
//    			userList.addAll(list);
//    		}
//		}
//    	if(Objects.isNull(userList) || userList.size() == 0){
//    		return;
//    	}
//    	List<DingUserInfoDO> userDbList = convert.convertUserDbList(userList);
//    	dingUserInfoService.insertBatch(userDbList);
//    }
//
//	public DingUserInfoDO findByName(String name){
//		DingUserInfoDO userInfoDO = dingUserInfoService.findByName(name);
//		return userInfoDO;
//	}
//
//}
