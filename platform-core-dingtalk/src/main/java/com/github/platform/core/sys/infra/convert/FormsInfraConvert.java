//package com.github.platform.core.sys.infra.convert;
//
//import com.github.platform.core.dingding.infra.rpc.external.command.DingCreateProcessCmd;
//import com.github.platform.core.sys.domain.context.SendProccessContext;
//import org.mapstruct.Mapper;
//import org.mapstruct.MappingConstants;
//
//import java.util.List;
//
///**
// * form infra转换
// *
// * @author: hdy
// * @date: 2023/6/29 3:50 PM
// * @version: 1.0
// */
//@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
//public interface FormsInfraConvert {
//
//	DingCreateProcessCmd.FormComponentValues dingFormContent(SendProccessContext.ProcessForm record);
//
//	List<DingCreateProcessCmd.FormComponentValues> dingFormContentList(List<SendProccessContext.ProcessForm> list);
//}
