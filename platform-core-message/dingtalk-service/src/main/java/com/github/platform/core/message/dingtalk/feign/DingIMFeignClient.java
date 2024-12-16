package com.github.platform.core.message.dingtalk.feign;

import com.github.platform.core.message.dingtalk.feign.command.DingCreateGroupCmd;
import com.github.platform.core.message.dingtalk.feign.command.DingGroupUserCmd;
import com.github.platform.core.message.dingtalk.feign.command.DingSendMessageCmd;
import com.github.platform.core.message.dingtalk.feign.command.DingWorkNoticeCmd;
import com.github.platform.core.message.dingtalk.feign.dto.DingCreateGroupDto;
import com.github.platform.core.message.dingtalk.feign.dto.DingResultBean;
import com.github.platform.core.message.dingtalk.feign.dto.DingSendMessageDto;
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
	 * 发送工作通知
	 * @param accessToken
	 * @param cmd
	 * @return
	 */
	@PostMapping(value="/topapi/message/corpconversation/asyncsend_v2")
	DingResultBean<String> workNotice(@RequestParam("access_token") String accessToken, @RequestBody DingWorkNoticeCmd cmd);

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

	/**
	 * 新增群成员
	 * @param accessToken
	 * @param cmd
	 * @return
	 */
	@PostMapping(value="/topapi/im/chat/scenegroup/member/add")
	DingResultBean groupAddUser(@RequestParam("access_token") String accessToken, @RequestBody DingGroupUserCmd cmd);

	/**
	 * 删除群成员
	 * @param accessToken
	 * @param cmd
	 * @return
	 */
	@PostMapping(value="/topapi/im/chat/scenegroup/member/delete")
	DingResultBean groupDeleteUser(@RequestParam("access_token") String accessToken, @RequestBody DingGroupUserCmd cmd);




}
