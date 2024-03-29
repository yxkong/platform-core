package com.github.platform.core.schedule.adapter.api.controller;

import com.github.platform.core.common.entity.StrIdReq;
import com.github.platform.core.log.domain.constants.LogOptTypeEnum;
import com.github.platform.core.log.infra.annotation.OptLog;
import com.github.platform.core.schedule.adapter.api.command.SysJobLogQuery;
import com.github.platform.core.schedule.adapter.api.convert.SysJobLogAdapterConvert;
import com.github.platform.core.schedule.application.executor.ISysJobLogExecutor;
import com.github.platform.core.schedule.domain.context.SysJobLogQueryContext;
import com.github.platform.core.schedule.domain.dto.SysJobLogDto;
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
* 任务执行日志
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-09-06 18:53:10.711
* @version 1.0
*/
@RestController
@Tag(name = "sysJobLog",description = "任务执行日志管理")
@RequestMapping("sys/job/log")
@Slf4j
public class SysJobLogController extends BaseController{
    @Resource
    private ISysJobLogExecutor executor;
    @Resource
    private SysJobLogAdapterConvert convert;

    /**
    * 查询任务执行日志列表
    * @param query 查询实体
    * @return 分页结果
    */
    @OptLog(module="sysJobLog",title="查询任务执行日志列表",persistent = false)
    @Operation(summary = "查询任务执行日志列表",tags = {"sysJobLog"})
    @PostMapping("/query")
    public ResultBean<PageBean<SysJobLogDto>> query(@RequestBody SysJobLogQuery query){
        SysJobLogQueryContext context = convert.toQuery(query);
        PageBean<SysJobLogDto> pageBean = executor.query(context);
        return ResultBeanUtil.succ(pageBean);
    }
    /**
    * 根据id查询任务执行日志明细
    * @param id 主键id
    * @return 单条记录
    */
    @OptLog(module="sysJobLog",title="根据id查询任务执行日志明细",optType = LogOptTypeEnum.detail,persistent = false)
    @Operation(summary = "根据id查询任务执行日志明细",tags = {"sysJobLog"})
    @PostMapping("/detail")
    public ResultBean<SysJobLogDto> detail(@Validated @RequestBody StrIdReq id) {
        SysJobLogDto dto = executor.findById(id.getId());
        return ResultBeanUtil.success(dto);
    }

}