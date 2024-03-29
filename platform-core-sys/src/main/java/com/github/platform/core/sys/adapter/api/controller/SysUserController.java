package com.github.platform.core.sys.adapter.api.controller;

import com.github.platform.core.auth.annotation.RequiredLogin;
import com.github.platform.core.auth.util.AuthUtil;
import com.github.platform.core.cache.infra.annotation.RepeatSubmit;
import com.github.platform.core.log.domain.constants.LogOptTypeEnum;
import com.github.platform.core.log.infra.annotation.OptLog;
import com.github.platform.core.common.entity.StrIdReq;
import com.github.platform.core.standard.entity.dto.PageBean;
import com.github.platform.core.standard.entity.dto.ResultBean;
import com.github.platform.core.standard.entity.vue.OptionsDto;
import com.github.platform.core.standard.util.ResultBeanUtil;
import com.github.platform.core.sys.adapter.api.command.account.KeyQuery;
import com.github.platform.core.sys.adapter.api.command.account.AddUserCmd;
import com.github.platform.core.sys.adapter.api.command.account.ResetPwdCmd;
import com.github.platform.core.sys.adapter.api.command.account.SysUserQuery;
import com.github.platform.core.sys.adapter.api.constant.SysAdapterResultEnum;
import com.github.platform.core.sys.adapter.api.convert.SysUserAdapterConvert;
import com.github.platform.core.sys.application.executor.ISysUserExecutor;
import com.github.platform.core.sys.domain.constant.UserChannelEnum;
import com.github.platform.core.sys.domain.constant.UserConstant;
import com.github.platform.core.sys.domain.context.RegisterContext;
import com.github.platform.core.sys.domain.context.ResetPwdContext;
import com.github.platform.core.sys.domain.context.SysUserQueryContext;
import com.github.platform.core.sys.domain.dto.SysUserDto;
import com.github.platform.core.sys.domain.dto.resp.PwdResult;
import com.github.platform.core.web.web.BaseController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户接口
 *
 * @author: yxkong
 * @date: 2022/12/30 3:11 PM
 * @version: 1.0
 */
@Tag(name = "user",description = "用户管理接口")
@RestController
@RequestMapping(value = "/sys/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class SysUserController extends BaseController {

    @Resource
    private ISysUserExecutor userExecutor;

    @Resource
    private SysUserAdapterConvert convert;
    @OptLog(module="user",title="根据条件查询用户",persistent = false)
    @Operation(summary = "根据条件查询",tags = {"user"})
    @PostMapping(value = "/users")
    public ResultBean<List<OptionsDto>> users(@RequestBody SysUserQuery userQuery) {
        SysUserQueryContext query = convert.toQuery(userQuery);
        List<OptionsDto> data = userExecutor.queryUsers(query);
        return ResultBeanUtil.succ(data);
    }
    /**
     * 用户列表查询
     *
     * @param userQuery
     * @return
     */
    @OptLog(module="user",title="用户列表查询",persistent = false)
    @Operation(summary = "用户列表查询",tags = {"user"})
    @PostMapping(value = "/query")
    public ResultBean<PageBean<SysUserDto>> query(@RequestBody @Validated SysUserQuery userQuery) {
        SysUserQueryContext userQueryContext = convert.toQuery(userQuery);
        PageBean<SysUserDto> data = userExecutor.query(userQueryContext);
        return buildSucResp(data);
    }

    @OptLog(module="user",title="根据角色查询",persistent = false)
    @Operation(summary = "根据角色查询",tags = {"user"})
    @PostMapping(value = "/roleQuery")
    @Deprecated
    public ResultBean<PageBean<SysUserDto>> roleQuery(@RequestBody @Validated SysUserQuery userQuery) {
        SysUserQueryContext userQueryContext = convert.toQuery(userQuery);
        PageBean<SysUserDto> data = userExecutor.query(userQueryContext);
        return buildSucResp(data);
    }

    @OptLog(module="user",title="根据条件查询用户，可部门id，可角色id",persistent = false)
    @Operation(summary = "根据条件查询，可角色可部门",tags = {"user"})
    @PostMapping(value = "/queryByDept")
    @ResponseBody
    public ResultBean<PageBean<SysUserDto>> queryBy(@RequestBody SysUserQuery userQuery) {
        SysUserQueryContext userQueryContext = convert.toQuery(userQuery);
        PageBean<SysUserDto> data = userExecutor.query(userQueryContext);
        return buildSucResp(data);
    }
    /**
     * 新增用户
     *
     * @param register
     * @return
     */
    @RepeatSubmit
    @OptLog(module="user",title="新增用户",optType = LogOptTypeEnum.add)
    @Operation(summary = "新增用户",tags = {"user"})
    @PostMapping(value = "/add")
    public ResultBean<Void> add(@RequestBody @Validated AddUserCmd register) {
        register.setLoginName(register.getLoginName().toLowerCase());
        RegisterContext registerContext = convert.toRegister(register);
        registerContext.setChannel(UserChannelEnum.add);
        userExecutor.insert(registerContext);
        return buildSucResp();
    }
    /**
     * 修改用户
     *
     * @param register
     * @return
     */
    @RepeatSubmit
    @OptLog(module="user",title="修改用户",optType = LogOptTypeEnum.modify)
    @Operation(summary = "修改用户",tags = {"user"})
    @PostMapping(value = "/modify")
    public ResultBean<Void> modify(@RequestBody @Validated AddUserCmd register) {
        register.setLoginName(register.getLoginName().toLowerCase());
        if (UserConstant.SUPER_ADMIN.equals(register.getLoginName())){
            exception(SysAdapterResultEnum.dont_allow_opt);
        }
        RegisterContext registerContext = convert.toRegister(register);
        userExecutor.update(registerContext);
        return buildSucResp();
    }

    /**
     * 重置密码
     * @param resetPwdCmd
     * @return
     */
    @OptLog(module="user",title="重置密码",optType = LogOptTypeEnum.modify)
    @Operation(summary = "重置密码",tags = {"user"})
    @PostMapping(value = "/reset")
    public ResultBean<PwdResult> reset(@RequestBody @Validated ResetPwdCmd resetPwdCmd) {
        resetPwdCmd.setLoginName(resetPwdCmd.getLoginName().toLowerCase());
        if (UserConstant.SUPER_ADMIN.equals(resetPwdCmd.getLoginName()) && AuthUtil.isSuperAdmin()){
            return ResultBeanUtil.noAuth("您无重置内置超级管理员的权限！");
        }
        ResetPwdContext resetPwdContext = convert.toRestPwd(resetPwdCmd);
        PwdResult pwdResult = userExecutor.resetPwd(resetPwdContext);
        return buildSucResp(pwdResult);
    }
    @OptLog(module="user",title="用户详情",persistent = false)
    @Operation(summary = "用户详情",tags = {"user"})
    @PostMapping(value = "/detail")
    public ResultBean<SysUserDto> detail(@RequestBody @Validated StrIdReq idReq){
        SysUserDto detail = userExecutor.detail(idReq.getId());
        return buildSucResp(detail);
    }
    @RequiredLogin
    @OptLog(module="user",title="用户下拉框",persistent = false)
    @Operation(summary = "获取用户",tags = {"user"})
    @PostMapping(value = "/fuzzySearch")
    public ResultBean<List<OptionsDto>> fuzzySearch(@RequestBody @Validated KeyQuery query){
        List<OptionsDto> list  = userExecutor.fuzzySearch(query.getKey());
        return buildSucResp(list);
    }
}
