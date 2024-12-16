package com.github.platform.core.message.dingtalk.feign;

import com.github.platform.core.message.dingtalk.feign.command.DingAppAccessTokenCmd;
import com.github.platform.core.message.dingtalk.feign.command.DingUserAccessTokenCmd;
import com.github.platform.core.message.dingtalk.feign.dto.DingAccessUserDto;
import com.github.platform.core.message.dingtalk.feign.dto.DingAppAccessTokenDto;
import com.github.platform.core.message.dingtalk.feign.dto.DingGroupUserDto;
import com.github.platform.core.message.dingtalk.feign.dto.DingUserAccessTokenDto;
import com.github.platform.core.message.dingtalk.feign.query.DingGroupUserQuery;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;


/**
 * 钉钉基础feign
 * @author
 */
@FeignClient(url="${platform.feign.url.ding-api}",name = "dingBaseFeignClient")
public interface DingBaseFeignClient {

	/**
	 * 获取应用token
	 * @param cmd 获取token请求
	 * @return
	 */
	@PostMapping(value="/v1.0/oauth2/accessToken")
	DingAppAccessTokenDto getAccessToken(DingAppAccessTokenCmd cmd);

	/**
	 * 获取用户的请求token
	 * @param cmd
	 * @return
	 */
	@PostMapping(value="/v1.0/oauth2/userAccessToken")
	DingUserAccessTokenDto getUserAccessToken(DingUserAccessTokenCmd cmd);


	/**
	 * 获取用户信息
	 * @param accessToken 调用服务端接口的授权凭证。使用个人用户的accessToken
	 * @param unionId 用户的unionId
	 * @return
	 */
	@GetMapping(value="/v1.0/contact/users/{unionId}")
	DingAccessUserDto getUserInfo(@RequestHeader("x-acs-dingtalk-access-token") String accessToken, @PathVariable("unionId") String unionId);

	/**
	 * 获取群消息，群成员id
	 * @param accessToken 访问token
	 * @param query  查询条件
	 * @return
	 */
	@GetMapping(value="/v1.0/im/sceneGroups/members/batchQuery")
	DingGroupUserDto getGroupUsers(@RequestHeader("x-acs-dingtalk-access-token") String accessToken, @RequestBody DingGroupUserQuery query);
}
