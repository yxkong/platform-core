package com.github.platform.core.dingtalk.infra.rpc.external;

import com.github.platform.core.dingtalk.infra.rpc.external.command.DingCreateGroupCmd;
import com.github.platform.core.dingtalk.infra.rpc.external.command.DingSendMessageCmd;
import com.github.platform.core.dingtalk.infra.rpc.external.dto.DingCreateGroupDto;
import com.github.platform.core.dingtalk.infra.rpc.external.dto.DingResultBean;
import com.github.platform.core.dingtalk.infra.rpc.external.dto.DingSendMessageDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * 钉钉IM服务 feign
 * @author
 */
@FeignClient(url="${platform.feign.url.ding-oapi}",name = "dingIMFeignClient")
public interface DingIMFeignClient {


	/**
	 * 钉钉建群
	 * @param accessToken
	 * @param cmd
	 * @return
	 */
	@PostMapping(value="/topapi/im/chat/scenegroup/create")
	DingResultBean<DingCreateGroupDto> createGroup(@RequestParam("access_token") String accessToken, @RequestBody DingCreateGroupCmd cmd);

	/**
	 * 发送群消息
	 * @param accessToken
	 * @param cmd
	 * @return
	 */
	@PostMapping(value="/topapi/im/chat/scencegroup/message/send_v2")
	DingSendMessageDto sendMessage(@RequestParam("access_token") String accessToken, @RequestBody DingSendMessageCmd cmd);


}
