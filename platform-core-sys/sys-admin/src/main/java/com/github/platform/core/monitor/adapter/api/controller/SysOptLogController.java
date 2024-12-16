package com.github.platform.core.monitor.adapter.api.controller;

import com.github.platform.core.log.domain.constants.LogOptTypeEnum;
import com.github.platform.core.log.infra.annotation.OptLog;
import com.github.platform.core.monitor.adapter.api.command.SysOptLogCmd;
import com.github.platform.core.monitor.adapter.api.command.SysOptLogQuery;
import com.github.platform.core.monitor.adapter.api.convert.SysOptLogAdapterConvert;
import com.github.platform.core.monitor.application.executor.SysOptLogExecutor;
import com.github.platform.core.monitor.domain.context.SysOptLogContext;
import com.github.platform.core.monitor.domain.context.SysOptLogQueryContext;
import com.github.platform.core.monitor.domain.dto.SysOptLogDto;
import com.github.platform.core.standard.entity.IdReq;
import com.github.platform.core.standard.entity.dto.PageBean;
import com.github.platform.core.standard.entity.dto.ResultBean;
import com.github.platform.core.standard.util.ResultBeanUtil;
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
* 操作日志
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-15 10:55:05.960
* @version 1.0
*/
@RestController
@Tag(name = "sysOptLog",description = "操作日志管理")
@RequestMapping("sys/monitor/optLog")
@Slf4j
public class SysOptLogController extends BaseController{
    @Resource
    private SysOptLogExecutor executor;
    @Resource
    private SysOptLogAdapterConvert convert;

    /**
    * 查询操作日志列表
    * @param query 查询实体
    * @return 分页结果
    */
    @Operation(summary = "查询操作日志列表",tags = {"sysOptLog"})
    @PostMapping("/query")
    public ResultBean<PageBean<SysOptLogDto>> query(@RequestBody SysOptLogQuery query){
        SysOptLogQueryContext context = convert.toQuery(query);
        PageBean<SysOptLogDto> pageBean = executor.query(context);
        return ResultBeanUtil.succ(pageBean);
    }
    /**
    * 根据id查询操作日志明细
    * @param id 主键id
    * @return 单条记录
    */
    @Operation(summary = "根据id查询操作日志明细",tags = {"sysOptLog"})
    @PostMapping("/detail")
    public ResultBean<SysOptLogDto> detail(@Validated @RequestBody IdReq id) {
        SysOptLogDto dto = executor.findById(id.getId());
        return ResultBeanUtil.success(dto);
    }
}