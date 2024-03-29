package com.github.platform.core.dingtalk.infra.rpc.external;

import com.github.platform.core.dingtalk.infra.rpc.external.command.DingAccessTokenCmd;
import com.github.platform.core.dingtalk.infra.rpc.external.dto.DingAccessTokenDto;
import com.github.platform.core.dingtalk.infra.rpc.external.dto.DingGroupUserDto;
import com.github.platform.core.dingtalk.infra.rpc.external.query.DingGroupUserQuery;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;


/**
 * 钉钉基础feign
 * @author
 */
@FeignClient(url="${platform.feign.url.ding-api}",name = "dingBaseFeignClient")
public interface DingBaseFeignClient {

	/**
	 * 获取token
	 * @param cmd 获取token请求
	 * @return
	 */
	@PostMapping(value="/v1.0/oauth2/accessToken")
	DingAccessTokenDto getToken(DingAccessTokenCmd cmd);

	/**
	 * 获取群消息，群成员id
	 * @param accessToken 访问token
	 * @param query  查询条件
	 * @return
	 */
	@GetMapping(value="/v1.0/im/sceneGroups/members/batchQuery")
	DingGroupUserDto getGroupUsers(@RequestHeader("x-acs-dingtalk-access-token") String accessToken, @RequestBody DingGroupUserQuery query);
}
