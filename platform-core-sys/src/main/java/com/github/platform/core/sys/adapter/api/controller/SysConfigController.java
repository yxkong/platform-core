package com.github.platform.core.sys.adapter.api.controller;

import com.github.platform.core.auth.annotation.RequiredLogin;
import com.github.platform.core.cache.domain.entity.ConfigEntity;
import com.github.platform.core.common.entity.StrIdReq;
import com.github.platform.core.log.domain.constants.LogOptTypeEnum;
import com.github.platform.core.log.infra.annotation.OptLog;
import com.github.platform.core.standard.entity.dto.PageBean;
import com.github.platform.core.standard.entity.dto.ResultBean;
import com.github.platform.core.standard.validate.Query;
import com.github.platform.core.sys.adapter.api.command.config.SysConfigCmd;
import com.github.platform.core.sys.adapter.api.command.config.SysConfigQuery;
import com.github.platform.core.sys.adapter.api.convert.SysConfigAdapterConvert;
import com.github.platform.core.sys.application.executor.ISysConfigExecutor;
import com.github.platform.core.sys.domain.context.SysConfigContext;
import com.github.platform.core.sys.domain.context.SysConfigQueryContext;
import com.github.platform.core.sys.domain.dto.SysConfigDto;
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
 * 配置管理
 * @author: yxkong
 * @date: 2023/4/19 2:50 PM
 * @version: 1.0
 */
@RestController
@Tag(name = "config",description = "配置管理")
@RequestMapping(value = "/sys/config")
@Slf4j
public class SysConfigController extends BaseController {

    @Resource
    private SysConfigAdapterConvert convert;
    @Resource
    private ISysConfigExecutor executor;
    @PostMapping("/query")
    @OptLog(module = "config",title = "配置查询",persistent = false)
    @Operation(summary = "配置查询",tags = {"config"})
    public ResultBean<PageBean<SysConfigDto>> query(@RequestBody SysConfigQuery query) {
        SysConfigQueryContext context = convert.toQuery(query);
        PageBean<SysConfigDto> pageBean = executor.query(context);
        return buildSucResp(pageBean);
    }
    @RequiredLogin
    @PostMapping("/getConfig")
    @OptLog(module = "config",title = "根据配置key查询",persistent = false)
    @Operation(summary = "根据配置key查询",tags = {"config"})
    public ResultBean<ConfigEntity> getConfig(@Validated({Query.class}) @RequestBody SysConfigQuery query) {
        SysConfigDto config = executor.getConfig(query.getKey());
        return buildSucResp(convert.toEntity(config));
    }

    @Operation(summary = "新增配置",tags = {"config"})
    @OptLog(module = "config",title = "新增配置",optType = LogOptTypeEnum.add)
    @PostMapping("/add")
    public ResultBean add(@RequestBody SysConfigCmd cmd) {
        SysConfigContext context = convert.toContext(cmd);
        executor.insert(context);
        return buildSucResp();
    }
    @Operation(summary = "修改配置",tags = {"config"})
    @OptLog(module = "config",title = "修改配置",optType = LogOptTypeEnum.modify)
    @PostMapping("/modify")
    public ResultBean modify(@RequestBody SysConfigCmd cmd) {
        SysConfigContext context = convert.toContext(cmd);
        executor.update(context);
        return buildSucResp();
    }
    @Operation(summary = "删除配置",tags = {"config"})
    @OptLog(module = "config",title = "删除配置")
    @PostMapping("/delete")
    public ResultBean delete(@Validated  @RequestBody StrIdReq idReq) {
        executor.delete(idReq.getId());
        return buildSucResp();
    }

    @Operation(summary = "重载配置",tags = {"config"})
    @OptLog(module = "reload",title = "重载配置",optType = LogOptTypeEnum.modify)
    @PostMapping("/reload")
    public ResultBean reload(@Validated({Query.class}) @RequestBody SysConfigQuery query) {
        executor.reload(query.getKey());
        return buildSucResp();
    }

}
