package com.github.platform.core.sys.adapter.api.controller;

import com.github.platform.core.auth.annotation.NoLogin;
import com.github.platform.core.auth.annotation.RequiredLogin;
import com.github.platform.core.auth.entity.LoginUserInfo;
import com.github.platform.core.auth.service.ITokenService;
import com.github.platform.core.cache.domain.constant.CacheConstant;
import com.github.platform.core.cache.infra.annotation.RepeatSubmit;
import com.github.platform.core.common.configuration.property.PlatformProperties;
import com.github.platform.core.common.utils.JsonUtils;
import com.github.platform.core.common.utils.StringUtils;
import com.github.platform.core.log.domain.constants.LogOptTypeEnum;
import com.github.platform.core.log.infra.annotation.OptLog;
import com.github.platform.core.standard.entity.dto.ResultBean;
import com.github.platform.core.sys.adapter.api.command.account.ModifyPwdCmd;
import com.github.platform.core.sys.adapter.api.command.account.NormalLoginCmd;
import com.github.platform.core.sys.adapter.api.command.account.SmsLoginCmd;
import com.github.platform.core.sys.adapter.api.convert.SysUserAdapterConvert;
import com.github.platform.core.sys.application.dto.VerifyCodeResult;
import com.github.platform.core.sys.application.executor.IAuthExecutor;
import com.github.platform.core.sys.domain.constant.CaptchaTypeEnum;
import com.github.platform.core.sys.domain.constant.LoginWayEnum;
import com.github.platform.core.sys.domain.constant.VerifyTypeEnum;
import com.github.platform.core.sys.domain.context.LoginContext;
import com.github.platform.core.sys.domain.context.ModifyPwdContext;
import com.github.platform.core.sys.domain.dto.resp.LoginResult;
import com.github.platform.core.sys.infra.constant.SysInfraResultEnum;
import com.github.platform.core.sys.infra.service.ICaptchaService;
import com.github.platform.core.web.web.BaseController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * 用户认证接口
 *
 * @author: yxkong
 * @date: 2022/12/27 5:23 下午
 * @version: 1.0
 */
@RestController
@Tag(name = "auth",description = "用户认证接口")
@RequestMapping(value = "/sys/auth", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class AuthController extends BaseController {
    @Resource
    private ICaptchaService captchaService;
    @Resource
    private SysUserAdapterConvert convert;
    @Resource
    private IAuthExecutor authExecutor;
    @Resource(name = CacheConstant.sysTokenService)
    private ITokenService tokenService;
    @Resource
    private PlatformProperties platformProperties;
    /**
     * 获取图形验证码
     *
     * @return
     */
    @NoLogin
    @Operation(summary = "获取图形验证码",tags = {"auth"})
    @PostMapping("/captcha")
    public ResultBean<VerifyCodeResult> captcha() {
        VerifyCodeResult captchaDto = captchaService.createImage(null, CaptchaTypeEnum.MATH);
        return buildSucResp(captchaDto);
    }

    /**
     * 用户获取验证码
     * @param cmd
     * @return
     */
    @NoLogin
    @OptLog(module="auth",title="获取验证码")
    @Operation(summary = "获取验证码",tags = {"auth"})
    @PostMapping("/verifyCode")
    @ApiResponse
    public ResultBean<VerifyCodeResult> verifyCode(@RequestBody @Validated SmsLoginCmd cmd) {
        LoginContext context = convert.toLogin(cmd);
        //获取验证码先验证图形验证码
//        context.setVerifyType(VerifyTypeEnum.captcha);
        VerifyCodeResult verifyCode = authExecutor.getVerifyCode(context);
        return buildSucResp(verifyCode);
    }
//    @Operation(summary = "验证滑块发送短信验证码",tags = {"auth"})
//    @PostMapping(value = "/sendBySlidingBlock")
//    public ResultBean sendBySlidingBlock(@RequestBody @Validated SmsBySlidingBlockCmd smsCmd) {
//        SlidingBlockContext context = new SlidingBlockContext(smsCmd.getTenantId(),smsCmd.getMobile(), smsCmd.getSlidingBlockId(), smsCmd.getSlidingBlockSupplier(),requestIp,smsCmd.getSmsType());
//        context.setProId(smsCmd.getProId());
//        return smsExecutor.sendBySlidingBlock(context);
//    }
    /**
     * 设置mo默认租户id
     * @param context
     */
    protected void setDefaultTenantId(LoginContext context) {
        if (Objects.isNull(context.getTenantId())){
            context.setTenantId(platformProperties.getDefaultTenantId());
        }
    }
    /**
     *ldap登录
     * @param cmd
     * @return
     */
    @NoLogin
    @OptLog(module="auth",title="ldap登录",maskParamNames = {"pwd"},optType = LogOptTypeEnum.mix)
    @Operation(summary = "ldap登录",tags = {"auth"})
    @PostMapping("/ldapLogin")
    @ApiResponse
    public ResultBean<LoginResult> ldapLogin(@RequestBody @Validated NormalLoginCmd cmd) {
        cmd.setLoginName(cmd.getLoginName().toLowerCase());
        LoginContext context = convert.toLogin(cmd);
        context.setLoginWay(LoginWayEnum.ldap);
        context.setVerifyType(VerifyTypeEnum.CAPTCHA);
        setDefaultTenantId(context);
        LoginResult login = authExecutor.login(context);
        return buildSucResp("登录成功！",login);
    }
    /**
     * 账户密码登陆
     * @param cmd
     * @return
     */
    @NoLogin
    @OptLog(module="auth",title="账户密码登陆",maskParamNames = {"pwd"},optType = LogOptTypeEnum.mix)
    @Operation(summary = "账户密码登陆",tags = {"auth"})
    @PostMapping("/normalLogin")
    @ApiResponse
    public ResultBean<LoginResult> normalLogin(@RequestBody @Validated NormalLoginCmd cmd) {
        cmd.setLoginName(cmd.getLoginName().toLowerCase());
        LoginContext context = convert.toLogin(cmd);
        context.setLoginWay(LoginWayEnum.normal);
        context.setVerifyType(VerifyTypeEnum.CAPTCHA);
        setDefaultTenantId(context);
        LoginResult login = authExecutor.login(context);
        return buildSucResp(login);
    }



    /**
     * 短信验证码登陆
     * @param cmd
     * @return
     */
    @NoLogin
    @OptLog(module="auth",title="短信验证码登陆")
    @Operation(summary = "短信验证码登陆",tags = {"auth"})
    @PostMapping("/smsLogin")
    @ApiResponse
    public ResultBean<LoginResult> smsLogin(@RequestBody @Validated SmsLoginCmd cmd) {
        LoginContext context = convert.toLogin(cmd);
        context.setVerifyType(VerifyTypeEnum.SMS);
        context.setLoginWay(LoginWayEnum.sms);
        setDefaultTenantId(context);
        LoginResult login = authExecutor.login(context);
        return buildSucResp(login);
    }

    /**
     * 用户信息
     *
     * @return
     */
    @RequiredLogin
    @Operation(summary = "用户信息",tags = {"auth"})
    @PostMapping("/info")
    @ApiResponse
    public ResultBean<LoginUserInfo> info() {
        String loginInfoStr = tokenService.getLoginInfoStr(getToken());
        LoginUserInfo userInfo = JsonUtils.fromJson(loginInfoStr,LoginUserInfo.class);
        return buildSucResp(userInfo);
    }
    
    /**
     * 用户登出
     *
     * @return
     */
    @RequiredLogin
    @OptLog(module="auth",title="用户退出",optType = LogOptTypeEnum.mix)
    @Operation(summary = "用户退出",tags = {"auth"})
    @PostMapping("/logout")
    public ResultBean<Void> logout() {
        Boolean flag =  authExecutor.logout();
        return buildSucResp();
    }

    /**
     * 用户修改密码
     * @param modifyPwdCmd
     * @return
     */
    @RequiredLogin
    @RepeatSubmit
    @OptLog(module = "auth",title = "修改密码",optType = LogOptTypeEnum.modify)
    @Operation(summary = "用户修改密码",tags = {"auth"})
    @PostMapping("/modifyPwd")
    @ApiResponse
    public ResultBean<?> modifyPwd(@RequestBody @Validated ModifyPwdCmd modifyPwdCmd) {
        ModifyPwdContext modifyPwdContext = convert.toModifyPwd(modifyPwdCmd);
        if (!StringUtils.isPwdPattern(modifyPwdCmd.getNewPwd())) {
            throw exception(SysInfraResultEnum.PWD_WARN);
        }
        authExecutor.modifyPwd(modifyPwdContext);
        return buildSucResp();
    }
}
