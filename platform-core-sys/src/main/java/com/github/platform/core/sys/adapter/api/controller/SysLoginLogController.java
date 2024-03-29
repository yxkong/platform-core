package com.github.platform.core.sys.adapter.api.controller;

import com.github.platform.core.common.entity.StrIdReq;
import com.github.platform.core.log.infra.annotation.OptLog;
import com.github.platform.core.standard.entity.dto.PageBean;
import com.github.platform.core.standard.entity.dto.ResultBean;
import com.github.platform.core.standard.util.ResultBeanUtil;
import com.github.platform.core.sys.adapter.api.command.loginlog.SysLoginLogQuery;
import com.github.platform.core.sys.adapter.api.convert.SysLoginLogAdapterConvert;
import com.github.platform.core.sys.application.executor.ILoginLogExecutor;
import com.github.platform.core.sys.domain.context.SysLoginLogQueryContext;
import com.github.platform.core.sys.domain.dto.SysLoginLogDto;
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
* 登录日志
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @time 2023-06-07 14:37:49.624
* @version 1.0
*/
@RestController
@Tag(name = "loginLog",description = "登录日志管理")
@RequestMapping("/sys/login/log")
@Slf4j
public class SysLoginLogController extends BaseController {
    @Resource
    private ILoginLogExecutor executor;
    @Resource
    private SysLoginLogAdapterConvert convert;

    /**
    * 查询登录日志列表
    * @param query
    * @return
    */
    @OptLog(module="loginLog",title="查询登录日志列表",persistent = false)
    @Operation(summary = "查询登录日志列表",tags = {"loginLog"})
    @PostMapping("/query")
    public ResultBean<PageBean<SysLoginLogDto>> query(@RequestBody SysLoginLogQuery query){
        SysLoginLogQueryContext context = convert.toQuery(query);
        PageBean<SysLoginLogDto> pageBean = executor.query(context);
        return ResultBeanUtil.succ(pageBean);
    }

    /**
    * 根据id查询登录日志明细
    * @param id
    * @return
    */
    @OptLog(module="loginLog",title="根据id查询登录日志明细")
    @Operation(summary = "根据id查询登录日志明细",tags = {"loginLog"})
    @PostMapping("/detail")
    public ResultBean<SysLoginLogDto> detail(@Validated @RequestBody StrIdReq id) {
        SysLoginLogDto dto = executor.findById(id.getId());
        return ResultBeanUtil.success(dto);
    }


}