package com.github.platform.core.sys.adapter.api.controller;

import com.github.platform.core.common.entity.StrIdReq;
import com.github.platform.core.common.utils.CollectionUtil;
import com.github.platform.core.common.utils.StringUtils;
import com.github.platform.core.log.domain.constants.LogOptTypeEnum;
import com.github.platform.core.log.infra.annotation.OptLog;
import com.github.platform.core.standard.entity.dto.PageBean;
import com.github.platform.core.standard.entity.dto.ResultBean;
import com.github.platform.core.sys.adapter.api.command.thirduser.SysThirdUserCmd;
import com.github.platform.core.sys.adapter.api.command.thirduser.SysThirdUserQuery;
import com.github.platform.core.sys.adapter.api.command.user.ThirdApproveCmd;
import com.github.platform.core.sys.adapter.api.convert.SysThirdUserAdapterConvert;
import com.github.platform.core.sys.application.executor.ISysThirdUserExecutor;
import com.github.platform.core.sys.domain.context.SysThirdUserContext;
import com.github.platform.core.sys.domain.context.SysThirdUserQueryContext;
import com.github.platform.core.sys.domain.dto.SysThirdUserDto;
import com.github.platform.core.web.web.BaseController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
* 三方用户
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @time 2023-05-31 16:04:49.640
* @version 1.0
*/
@RestController
@Tag(name = "thirdUser",description = "三方用户管理")
@RequestMapping("/sys/third/user")
@Slf4j
public class ThirdUserController extends BaseController {
    @Resource
    private ISysThirdUserExecutor executor;
    @Resource
    private SysThirdUserAdapterConvert convert;

    /**
    * 查询三方用户列表
    * @param query
    * @return
    */
    @OptLog(module="thirdUser",title="查询三方用户列表",persistent = false)
    @Operation(summary = "查询三方用户列表",tags = {"thirdUser"})
    @PostMapping("/query")
    public ResultBean<PageBean<SysThirdUserDto>> query(@RequestBody SysThirdUserQuery query){
        SysThirdUserQueryContext context = convert.toQuery(query);
        PageBean<SysThirdUserDto> pageBean = executor.query(context);
        dealData(pageBean);
        return buildSucResp(pageBean);
    }
    private static void dealData(PageBean<SysThirdUserDto> data) {
        if (CollectionUtil.isNotEmpty( data.getData())){
            data.getData().stream().forEach(
                    s -> {
                        s.setMobile(StringUtils.getMobileVague(s.getMobile()));
                    }
            );
        }
    }
    /**
     * 修改三方用户
     * @param cmd
     * @return
     */
    @OptLog(module="thirdUser",title="修改三方用户",optType = LogOptTypeEnum.modify)
    @Operation(summary = "修改三方用户",tags = {"thirdUser"})
    @PostMapping("/modify")
    public ResultBean modify(@Validated @RequestBody SysThirdUserCmd cmd) {
        SysThirdUserContext context = convert.toContext(cmd);
        //禁止修改的数据
        context.setChannel(null);
        context.setUserId(null);
        context.setOpenId(null);
        executor.update(context);
        return buildSucResp();
    }
    @OptLog(module="thirdUser",title="审批三方用户",optType = LogOptTypeEnum.modify)
    @Operation(summary = "审批三方用户",tags = {"thirdUser"})
    @PostMapping("/approve")
    public ResultBean approve(@Validated @RequestBody ThirdApproveCmd cmd) {
        executor.approve( convert.toApprove(cmd));
        return buildSucResp();
    }

    @OptLog(module="thirdUser",title="三方用户详情",persistent = false)
    @Operation(summary = "三方用户详情",tags = {"thirdUser"})
    @PostMapping(value = "/detail")
    public ResultBean<SysThirdUserDto> detail(@RequestBody @Validated StrIdReq idReq){
        return buildSucResp(executor.detail(idReq.getId()));
    }
}