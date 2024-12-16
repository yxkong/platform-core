package com.github.platform.core.message.dingtalk.feign;

import com.github.platform.core.message.dingtalk.feign.command.DingMobileQuery;
import com.github.platform.core.message.dingtalk.feign.dto.*;
import com.github.platform.core.message.dingtalk.feign.query.DingDeptQuery;
import com.github.platform.core.message.dingtalk.feign.query.DingDeptUserQuery;
import com.github.platform.core.message.dingtalk.feign.query.DingUserQuery;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 钉钉通讯录
 * @author: yxkong
 * @date: 2024/1/19 13:56
 * @version: 1.0
 */
@FeignClient(url="${platform.feign.url.ding-oapi}",name = "dingContactFeignClient")
public interface DingContactFeignClient {
    /**
     * 获取下一级部门基础信息
     * @param accessToken
     * @param query
     * @return
     */
    @PostMapping(value="/topapi/v2/department/listsub")
    DingResultBean<List<DingDeptDto>> listSub(@RequestParam("access_token") String accessToken, @RequestBody DingDeptQuery query);

    /**
     * 获取指定部门中的用户详细信息
     * @param accessToken
     * @param query
     * @return
     */
    @PostMapping(value="/topapi/v2/user/list")
    DingResultBean<DingDeptUserDto> getDeptUsers(@RequestParam("access_token") String accessToken, @RequestBody DingDeptUserQuery query);

    /**
     * 根据userId查询用户信息
     * @param accessToken
     * @param query
     * @return
     */
    @PostMapping(value="/topapi/v2/user/get")
    DingResultBean<DingUserDto> getUserInfo(@RequestParam("access_token") String accessToken, @RequestBody DingUserQuery query);
    /**
     * 根据手机号码获取用户的userid,只返回userId
     * @param accessToken
     * @param query
     * @return
     */
    @PostMapping(value="/topapi/v2/user/getbymobile")
    DingResultBean<DingAccessUserDto> getUserIdByMobile(@RequestParam("access_token") String accessToken, @RequestBody DingMobileQuery query);


}
