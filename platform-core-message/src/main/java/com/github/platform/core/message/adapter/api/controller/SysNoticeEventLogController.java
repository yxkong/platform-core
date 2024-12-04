package com.github.platform.core.message.adapter.api.controller;

import com.github.platform.core.log.infra.annotation.OptLog;
import com.github.platform.core.common.entity.StrIdReq;
import com.github.platform.core.message.adapter.api.command.SysNoticeEventLogCmd;
import com.github.platform.core.message.adapter.api.command.SysNoticeEventLogQuery;
import com.github.platform.core.message.adapter.api.convert.SysNoticeEventLogAdapterConvert;
import com.github.platform.core.message.application.executor.ISysNoticeEventLogExecutor;
import com.github.platform.core.message.domain.context.SysNoticeEventLogContext;
import com.github.platform.core.message.domain.context.SysNoticeEventLogQueryContext;
import com.github.platform.core.message.domain.dto.SysNoticeEventLogDto;
import com.github.platform.core.standard.entity.dto.PageBean;
import com.github.platform.core.log.domain.constants.LogOptTypeEnum;
import com.github.platform.core.standard.entity.dto.ResultBean;
import com.github.platform.core.web.web.BaseController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
/**
 * 通知事件日志
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author yxkong
 * @date 2024-12-04 13:36:01.514
 * @version 1.0
 */
@RestController
@Tag(name = "sysNoticeEventLog",description = "通知事件日志管理")
@RequestMapping("api/message/notice/event")
@Slf4j
public class SysNoticeEventLogController extends BaseController{
    @Resource
    private ISysNoticeEventLogExecutor sysNoticeEventLogExecutor;
    @Resource
    private SysNoticeEventLogAdapterConvert sysNoticeEventLogConvert;

    /**
    * 查询通知事件日志列表
    * @param query 查询实体
    * @return 分页结果
    */
    @OptLog(module="sysNoticeEventLog",title="查询通知事件日志列表",persistent = false)
    @Operation(summary = "查询通知事件日志列表",tags = {"sysNoticeEventLog"})
    @PostMapping("/query")
    public ResultBean<PageBean<SysNoticeEventLogDto>> query(@RequestBody SysNoticeEventLogQuery query){
        SysNoticeEventLogQueryContext context = sysNoticeEventLogConvert.toQuery(query);
        PageBean<SysNoticeEventLogDto> pageBean = sysNoticeEventLogExecutor.query(context);
        return buildSucResp(pageBean);
    }

    /**
    * 新增通知事件日志
    * @param cmd 新增实体
    * @return 操作结果
    */
    @OptLog(module="sysNoticeEventLog",title="新增通知事件日志",optType = LogOptTypeEnum.add)
    @Operation(summary = "新增通知事件日志",tags = {"sysNoticeEventLog"})
    @PostMapping("/add")
    public ResultBean<String> add(@Validated @RequestBody SysNoticeEventLogCmd cmd) {
        SysNoticeEventLogContext context= sysNoticeEventLogConvert.toContext(cmd);
        String id = sysNoticeEventLogExecutor.insert(context);
        return buildSucResp(id);
    }

    /**
    * 根据id查询通知事件日志明细
    * @param id 主键id
    * @return 单条记录
    */
    @OptLog(module="sysNoticeEventLog",title="根据id查询通知事件日志明细",optType = LogOptTypeEnum.detail,persistent = false)
    @Operation(summary = "根据id查询通知事件日志明细",tags = {"sysNoticeEventLog"})
    @PostMapping("/detail")
    public ResultBean<SysNoticeEventLogDto> detail(@Validated @RequestBody StrIdReq id) {
        SysNoticeEventLogDto dto = sysNoticeEventLogExecutor.findById(id.getId());
        return buildSucResp(dto);
    }

    /**
     * 根据id删除通知事件日志记录
     * @param id 主键id
     * @return 操作结果
     */
    @OptLog(module="sysNoticeEventLog",title="根据id删除通知事件日志记录",optType = LogOptTypeEnum.delete)
    @Operation(summary = "根据id删除通知事件日志记录",tags = {"sysNoticeEventLog"})
    @PostMapping("/delete")
    public ResultBean delete(@Validated @RequestBody StrIdReq id) {
        sysNoticeEventLogExecutor.delete(id.getId());
        return buildSucResp();
    }

    /**
     * 修改通知事件日志
     * @param cmd 修改实体
     * @return 操作结果
     */
    @OptLog(module="sysNoticeEventLog",title="修改通知事件日志",optType = LogOptTypeEnum.modify)
    @Operation(summary = "修改通知事件日志",tags = {"sysNoticeEventLog"})
    @PostMapping("/modify")
    public ResultBean modify(@Validated @RequestBody SysNoticeEventLogCmd cmd) {
        sysNoticeEventLogExecutor.update(sysNoticeEventLogConvert.toContext(cmd));
        return buildSucResp();
    }
}