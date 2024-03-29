package com.github.platform.core.dingtalk.infra.rpc.external.dto;

import lombok.Data;

/**
 * 钉钉创建流程实例返回
 * @author
 */
@Data
public class DingProcessInstanceDto extends DingResultBean {
	
    private String instanceId;
}
