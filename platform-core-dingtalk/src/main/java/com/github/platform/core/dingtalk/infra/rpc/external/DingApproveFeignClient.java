package com.github.platform.core.dingtalk.infra.rpc.external;

import com.github.platform.core.dingtalk.infra.rpc.external.command.DingCreateProcessCmd;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.github.platform.core.dingtalk.infra.rpc.external.dto.DingFormDto;
import com.github.platform.core.dingtalk.infra.rpc.external.dto.DingProcessInstanceDto;
import com.github.platform.core.dingtalk.infra.rpc.external.dto.DingResultBean;
import com.github.platform.core.dingtalk.infra.rpc.external.command.DingCreateFormsCmd;


/**
 * 钉钉审批服务
 * @author
 */
@FeignClient(url= "${platform.feign.url.ding-api}",name = "dingApproveFeignClient")
public interface DingApproveFeignClient {

	/**
	 * ② 根据表单发起流程
	 * @param cmd
	 * @param accessToken
	 * @return
	 */
	@PostMapping(value="/v1.0/workflow/processInstances")
	DingProcessInstanceDto processInstances(@RequestBody DingCreateProcessCmd cmd, @RequestHeader("x-acs-dingtalk-access-token") String accessToken);


	/**
	 * ① 创建流程表单
	 * @param cmd
	 * @param accessToken
	 * @return
	 */
	@PostMapping(value="/v1.0/workflow/forms")
	DingResultBean<DingFormDto> createForms(@RequestBody DingCreateFormsCmd cmd, @RequestHeader("x-acs-dingtalk-access-token") String accessToken);

}
