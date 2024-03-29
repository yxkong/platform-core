//package com.github.platform.core.sys.infra.convert;
//
//import java.util.List;
//
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//import org.mapstruct.MappingConstants;
//import org.mapstruct.Mappings;
//
//import com.github.platform.core.persistence.entity.sys.DingDepartmentDO;
//import com.github.platform.core.persistence.entity.sys.DingUserInfoDO;
//import com.github.platform.core.dingding.infra.rpc.external.dto.DingDeptDto;
//import com.github.platform.core.dingding.infra.rpc.external.dto.DingDeptUserDto.DingUserInfo;
//
//@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
//public interface DingDingConvert {
//
//
//	@Mappings({
//        @Mapping(target = "deptId", source = "dept_id"),
//        @Mapping(target = "parentId", source = "parent_id"),
//	})
//	DingDepartmentDO convertDept(DingDeptDto vo);
//
//	List<DingDepartmentDO> convertDept(List<DingDeptDto> list);
//
//
//	@Mappings({
//        @Mapping(target = "userId", source = "userid"),
//        @Mapping(target = "unionId", source = "unionid"),
//        @Mapping(target = "deptId", expression = "java(vo.getDept_id_list().get(0))"),
//        @Mapping(target = "jobNumber", source = "job_number"),
//	})
//	DingUserInfoDO convertUser(DingUserInfo vo);
//
//	List<DingUserInfoDO> convertUserDbList(List<DingUserInfo> userList);
//}
