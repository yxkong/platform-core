package com.github.platform.core.dingtalk.adapter.api.controller;

import com.github.platform.core.auth.util.AuthUtil;
import com.github.platform.core.auth.util.LoginUserInfoUtil;
import com.github.platform.core.dingtalk.adapter.api.command.DingInitCmd;
import com.github.platform.core.dingtalk.domain.gateway.IDingTalkGateway;
import com.github.platform.core.log.infra.annotation.OptLog;
import com.github.platform.core.web.web.BaseController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 钉钉接口
 * @author: yxkong
 * @date: 2024/1/23 18:33
 * @version: 1.0
 */
@RestController
@Tag(name = "form",description = "表单配置管理")
@RequestMapping("api/ding")
@Slf4j
public class DingController extends BaseController {
    @Autowired
    private IDingTalkGateway dingTalkGateway;

    @OptLog(module="form",title="查询表单配置列表",persistent = false)
    @Operation(summary = "查询表单配置列表",tags = {"form"})
    @PostMapping("/initDept")
    public void initDept(@RequestBody DingInitCmd cmd){
        if (AuthUtil.isSuperAdmin()){
            dingTalkGateway.initDept(cmd.getDeptId(),cmd.getTenantId(), LoginUserInfoUtil.getLoginName());
            buildSucResp();
        }else {
            buildSimpleResp(false,"没有操作权限");
        }
    }
    @OptLog(module="form",title="查询表单配置列表",persistent = false)
    @Operation(summary = "查询表单配置列表",tags = {"form"})
    @PostMapping("/initAllUser")
    public void initAllUser(@RequestBody DingInitCmd cmd){
        if (AuthUtil.isSuperAdmin()){
            dingTalkGateway.initAllUser(cmd.getTenantId(),LoginUserInfoUtil.getLoginName());
            buildSucResp();
        }else {
            buildSimpleResp(false,"没有操作权限");
        }
    }

}
