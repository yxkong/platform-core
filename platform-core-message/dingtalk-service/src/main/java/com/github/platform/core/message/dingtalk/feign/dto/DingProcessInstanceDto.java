package com.github.platform.core.message.dingtalk.feign.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 钉钉创建流程实例返回
 * @author
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class DingProcessInstanceDto extends DingResultBean {
	
    private String instanceId;
}
