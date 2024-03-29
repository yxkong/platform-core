package com.github.platform.core.sms.adapter.api.controller;

import com.github.platform.core.log.domain.constants.LogOptTypeEnum;
import com.github.platform.core.log.infra.annotation.OptLog;
import com.github.platform.core.sms.adapter.api.command.SysSysSmsLogQuery;
import com.github.platform.core.sms.adapter.api.convert.SysSmsLogAdapterConvert;
import com.github.platform.core.sms.application.executor.ISysSmsLogExecutor;
import com.github.platform.core.sms.domain.context.SmsLogQueryContext;
import com.github.platform.core.sms.domain.dto.SysSmsLogDto;
import com.github.platform.core.standard.entity.IdReq;
import com.github.platform.core.standard.entity.dto.PageBean;
import com.github.platform.core.standard.entity.dto.ResultBean;
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
* 短信日志
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @time 2023-07-04 10:23:45.615
* @version 1.0
*/
@RestController
@Tag(name = "sysSmsLog",description = "短信日志管理")
@RequestMapping("/sys/sms/log")
@Slf4j
public class SysSmsLogController extends BaseController{
    @Resource
    private ISysSmsLogExecutor executor;
    @Resource
    private SysSmsLogAdapterConvert convert;

    /**
    * 查询短信日志列表
    * @param query
    * @return
    */
    @OptLog(module="sysSmsLog",title="查询短信日志列表")
    @Operation(summary = "查询短信日志列表",tags = {"sysSmsLog"})
    @PostMapping("/query")
    public ResultBean<PageBean<SysSmsLogDto>> query(@RequestBody SysSysSmsLogQuery query){
        SmsLogQueryContext context = convert.toQuery(query);
        PageBean<SysSmsLogDto> pageBean = executor.query(context);
        return buildSucResp(pageBean) ;
    }

    /**
    * 根据id查询短信日志明细
    * @param id
    * @return
    */
    @OptLog(module="sysSmsLog",title="根据id查询短信日志明细",optType = LogOptTypeEnum.detail)
    @Operation(summary = "根据id查询短信日志明细",tags = {"sysSmsLog"})
    @PostMapping("/detail")
    public ResultBean<SysSmsLogDto> detail(@Validated @RequestBody IdReq id) {
        SysSmsLogDto dto = executor.findById(id.getId());
        return buildSucResp(dto);
    }
}