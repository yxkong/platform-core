package com.github.platform.core.message.adapter.api.controller;

import com.github.platform.core.log.infra.annotation.OptLog;
import com.github.platform.core.common.entity.StrIdReq;
import com.github.platform.core.message.adapter.api.command.SysNoticeTemplateCmd;
import com.github.platform.core.message.adapter.api.command.SysNoticeTemplateQuery;
import com.github.platform.core.message.adapter.api.convert.SysNoticeTemplateAdapterConvert;
import com.github.platform.core.message.application.executor.ISysNoticeTemplateExecutor;
import com.github.platform.core.message.domain.context.SysNoticeTemplateContext;
import com.github.platform.core.message.domain.context.SysNoticeTemplateQueryContext;
import com.github.platform.core.message.domain.dto.SysNoticeTemplateDto;
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
 * 消息通知模板
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author yxkong
 * @date 2024-12-04 13:32:24.593
 * @version 1.0
 */
@RestController
@Tag(name = "sysNoticeTemplate",description = "消息通知模板管理")
@RequestMapping("api/message/notice/template")
@Slf4j
public class SysNoticeTemplateController extends BaseController{
    @Resource
    private ISysNoticeTemplateExecutor sysNoticeTemplateExecutor;
    @Resource
    private SysNoticeTemplateAdapterConvert sysNoticeTemplateConvert;

    /**
    * 查询消息通知模板列表
    * @param query 查询实体
    * @return 分页结果
    */
    @OptLog(module="sysNoticeTemplate",title="查询消息通知模板列表",persistent = false)
    @Operation(summary = "查询消息通知模板列表",tags = {"sysNoticeTemplate"})
    @PostMapping("/query")
    public ResultBean<PageBean<SysNoticeTemplateDto>> query(@RequestBody SysNoticeTemplateQuery query){
        SysNoticeTemplateQueryContext context = sysNoticeTemplateConvert.toQuery(query);
        PageBean<SysNoticeTemplateDto> pageBean = sysNoticeTemplateExecutor.query(context);
        return buildSucResp(pageBean);
    }

    /**
    * 新增消息通知模板
    * @param cmd 新增实体
    * @return 操作结果
    */
    @OptLog(module="sysNoticeTemplate",title="新增消息通知模板",optType = LogOptTypeEnum.add)
    @Operation(summary = "新增消息通知模板",tags = {"sysNoticeTemplate"})
    @PostMapping("/add")
    public ResultBean<String> add(@Validated @RequestBody SysNoticeTemplateCmd cmd) {
        SysNoticeTemplateContext context= sysNoticeTemplateConvert.toContext(cmd);
        String id = sysNoticeTemplateExecutor.insert(context);
        return buildSucResp(id);
    }

    /**
    * 根据id查询消息通知模板明细
    * @param id 主键id
    * @return 单条记录
    */
    @OptLog(module="sysNoticeTemplate",title="根据id查询消息通知模板明细",optType = LogOptTypeEnum.detail,persistent = false)
    @Operation(summary = "根据id查询消息通知模板明细",tags = {"sysNoticeTemplate"})
    @PostMapping("/detail")
    public ResultBean<SysNoticeTemplateDto> detail(@Validated @RequestBody StrIdReq id) {
        SysNoticeTemplateDto dto = sysNoticeTemplateExecutor.findById(id.getId());
        return buildSucResp(dto);
    }

    /**
     * 根据id删除消息通知模板记录
     * @param id 主键id
     * @return 操作结果
     */
    @OptLog(module="sysNoticeTemplate",title="根据id删除消息通知模板记录",optType = LogOptTypeEnum.delete)
    @Operation(summary = "根据id删除消息通知模板记录",tags = {"sysNoticeTemplate"})
    @PostMapping("/delete")
    public ResultBean delete(@Validated @RequestBody StrIdReq id) {
        sysNoticeTemplateExecutor.delete(id.getId());
        return buildSucResp();
    }

    /**
     * 修改消息通知模板
     * @param cmd 修改实体
     * @return 操作结果
     */
    @OptLog(module="sysNoticeTemplate",title="修改消息通知模板",optType = LogOptTypeEnum.modify)
    @Operation(summary = "修改消息通知模板",tags = {"sysNoticeTemplate"})
    @PostMapping("/modify")
    public ResultBean modify(@Validated @RequestBody SysNoticeTemplateCmd cmd) {
        sysNoticeTemplateExecutor.update(sysNoticeTemplateConvert.toContext(cmd));
        return buildSucResp();
    }
}