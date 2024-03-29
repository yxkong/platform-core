//package com.github.platform.core.sys.application.executor;
//import com.alibaba.fastjson.JSONObject;
//
//import lombok.extern.slf4j.Slf4j;
//@Slf4j
//public enum DingBackExecutorEnum implements DingBackService{
//	Bpms_instance("bpms_instance_change"){
//		@Override
//		public void eventDeal(JSONObject eventJson) {
//			String processCode = eventJson.getString("processCode");
//	        String result = eventJson.getString("result");//正常结束时result为agree，拒绝时result为refuse，审批终止时没这个值。
//	        String processInstanceId = eventJson.getString("processInstanceId");
//	        String type = eventJson.getString("type");//finish：审批正常结束（同意或拒绝）,terminate：审批终止（发起人撤销审批单）
//	        if(!"finish".equalsIgnoreCase(type)){
//	            return;
//	        }
//	        log.info("审批事件处理，type={}，processInstanceId={}，processCode={}", type, processInstanceId, processCode);
//	        //TODO 审批时间结果处理，更新表
//		}
//    };
//
//	private final String eventType; //接口调用方式
//
//	DingBackExecutorEnum(String eventType){
//		this.eventType = eventType;
//	}
//	public static DingBackExecutorEnum getDingBackExecutorEnum(String eventType){
//		for (DingBackExecutorEnum enums : DingBackExecutorEnum.values()) {
//			if(enums.eventType.equals(eventType)){
//				return enums;
//			}
//		}
//		return null;
//	}
//
//}
