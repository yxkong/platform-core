package com.github.platform.core.schedule.adapter.api.controller;

import com.github.platform.core.common.entity.StrIdReq;
import com.github.platform.core.log.infra.annotation.OptLog;
import com.github.platform.core.schedule.adapter.api.command.TriggerJobCmd;
import com.github.platform.core.schedule.application.executor.ISysJobExecutor;
import com.github.platform.core.schedule.adapter.api.command.SysJobCmd;
import com.github.platform.core.schedule.adapter.api.command.SysJobQuery;
import com.github.platform.core.schedule.adapter.api.convert.SysJobAdapterConvert;
import com.github.platform.core.schedule.domain.context.SysJobContext;
import com.github.platform.core.schedule.domain.context.SysJobQueryContext;
import com.github.platform.core.schedule.domain.dto.SysJobDto;
import com.github.platform.core.standard.entity.dto.PageBean;
import com.github.platform.core.log.domain.constants.LogOptTypeEnum;
import com.github.platform.core.standard.entity.dto.ResultBean;
import com.github.platform.core.standard.util.ResultBeanUtil;
import com.github.platform.core.web.web.BaseController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import javax.annotation.Resource;

import org.quartz.SchedulerException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
/**
* 任务管理
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-09-05 11:37:59.627
* @version 1.0
*/
@RestController
@Tag(name = "sysJob",description = "任务管理管理")
@RequestMapping("sys/job")
@Slf4j
public class SysJobController extends BaseController{
    @Resource
    private ISysJobExecutor executor;
    @Resource
    private SysJobAdapterConvert convert;

    /**
    * 查询任务管理列表
    * @param query 查询实体
    * @return 分页结果
    */
    @OptLog(module="sysJob",title="查询任务管理列表",persistent = false)
    @Operation(summary = "查询任务管理列表",tags = {"sysJob"})
    @PostMapping("/query")
    public ResultBean<PageBean<SysJobDto>> query(@RequestBody SysJobQuery query){
        SysJobQueryContext context = convert.toQuery(query);
        PageBean<SysJobDto> pageBean = executor.query(context);
        return ResultBeanUtil.succ(pageBean);
    }

    /**
    * 新增任务管理
    * @param cmd 新增实体
    * @return 操作结果
    */
    @OptLog(module="sysJob",title="新增任务管理",optType = LogOptTypeEnum.add)
    @Operation(summary = "新增任务管理",tags = {"sysJob"})
    @PostMapping("/add")
    public ResultBean add(@Validated @RequestBody SysJobCmd cmd) {
        SysJobContext context = convert.toContext(cmd);
        executor.addJob(context);
        return buildSucResp();
    }

    /**
    * 根据id查询任务管理明细
    * @param id 主键id
    * @return 单条记录
    */
    @OptLog(module="sysJob",title="根据id查询任务管理明细",optType = LogOptTypeEnum.detail,persistent = false)
    @Operation(summary = "根据id查询任务管理明细",tags = {"sysJob"})
    @PostMapping("/detail")
    public ResultBean<SysJobDto> detail(@Validated @RequestBody StrIdReq id) {
        SysJobDto dto = executor.findById(id.getId());
        return ResultBeanUtil.success(dto);
    }

    /**
     * 根据id删除任务管理记录
     * @param id 主键id
     * @return 操作结果
     */
    @OptLog(module="sysJob",title="根据id删除任务管理记录",optType = LogOptTypeEnum.delete)
    @Operation(summary = "根据id删除任务管理记录",tags = {"sysJob"})
    @PostMapping("/delete")
    public ResultBean delete(@Validated @RequestBody StrIdReq id) throws SchedulerException {
        executor.delete(id.getId());
        return buildSucResp();
    }
    @OptLog(module="sysJob",title="触发任务",optType = LogOptTypeEnum.trigger)
    @Operation(summary = "触发任务",tags = {"sysJob"})
    @PostMapping("/trigger")
    public ResultBean triggerJob(@Validated @RequestBody TriggerJobCmd cmd) throws SchedulerException {
        executor.triggerJob(cmd.getId(),cmd.getHandlerParam());
        return buildSucResp();
    }
    @OptLog(module="sysJob",title="暂停任务",optType = LogOptTypeEnum.trigger)
    @Operation(summary = "暂停任务",tags = {"sysJob"})
    @PostMapping("/pause")
    public ResultBean pauseJob(@Validated @RequestBody StrIdReq id) throws SchedulerException {
        executor.pauseJob(id.getId());
        return buildSucResp();
    }
    @OptLog(module="sysJob",title="恢复任务",optType = LogOptTypeEnum.trigger)
    @Operation(summary = "恢复任务",tags = {"sysJob"})
    @PostMapping("/resume")
    public ResultBean resumeJob(@Validated @RequestBody StrIdReq id) throws SchedulerException {
        executor.resumeJob(id.getId());
        return buildSucResp();
    }

    /**
     * 修改任务管理
     * @param cmd 修改实体
     * @return 操作结果
     */
    @OptLog(module="sysJob",title="修改任务管理",optType = LogOptTypeEnum.modify)
    @Operation(summary = "修改任务管理",tags = {"sysJob"})
    @PostMapping("/modify")
    public ResultBean modify(@Validated @RequestBody SysJobCmd cmd) throws SchedulerException {
        SysJobContext context = convert.toContext(cmd);
        executor.updateJob(context);
        return buildSucResp();
    }
}