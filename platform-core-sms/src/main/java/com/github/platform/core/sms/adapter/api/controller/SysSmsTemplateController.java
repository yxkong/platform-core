package com.github.platform.core.sms.adapter.api.controller;

import com.github.platform.core.log.domain.constants.LogOptTypeEnum;
import com.github.platform.core.log.infra.annotation.OptLog;
import com.github.platform.core.sms.adapter.api.command.SysSmsTemplateCmd;
import com.github.platform.core.sms.adapter.api.command.SysSmsTemplateQuery;
import com.github.platform.core.sms.adapter.api.convert.SysSmsTemplateAdapterConvert;
import com.github.platform.core.sms.application.executor.ISysSmsTemplateExecutor;
import com.github.platform.core.sms.domain.context.SysSmsTemplateContext;
import com.github.platform.core.sms.domain.context.SysSmsTemplateQueryContext;
import com.github.platform.core.sms.domain.dto.SysSmsTemplateDto;
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
* 短信模板
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-14 17:39:30.124
* @version 1.0
*/
@RestController
@Tag(name = "sysSmsTemplate",description = "短信模板管理")
@RequestMapping("/sys/sms/template")
@Slf4j
public class SysSmsTemplateController extends BaseController{
    @Resource
    private ISysSmsTemplateExecutor executor;
    @Resource
    private SysSmsTemplateAdapterConvert convert;

    /**
    * 查询短信模板列表
    * @param query 查询实体
    * @return 分页结果
    */
    @Operation(summary = "查询短信模板列表",tags = {"sysSmsTemplate"})
    @PostMapping("/query")
    public ResultBean<PageBean<SysSmsTemplateDto>> query(@RequestBody SysSmsTemplateQuery query){
        SysSmsTemplateQueryContext context = convert.toQuery(query);
        PageBean<SysSmsTemplateDto> pageBean = executor.query(context);
        return buildSucResp(pageBean);
    }

    /**
    * 新增短信模板
    * @param cmd 新增实体
    * @return 操作结果
    */
    @OptLog(module="sysSmsTemplate",title="新增短信模板",optType = LogOptTypeEnum.add)
    @Operation(summary = "新增短信模板",tags = {"sysSmsTemplate"})
    @PostMapping("/add")
    public ResultBean add(@Validated @RequestBody SysSmsTemplateCmd cmd) {
        SysSmsTemplateContext context = convert.toContext(cmd);
        executor.insert(context);
        return buildSucResp();
    }

    /**
    * 根据id查询短信模板明细
    * @param id 主键id
    * @return 单条记录
    */
    @Operation(summary = "根据id查询短信模板明细",tags = {"sysSmsTemplate"})
    @PostMapping("/detail")
    public ResultBean<SysSmsTemplateDto> detail(@Validated @RequestBody IdReq id) {
        SysSmsTemplateDto dto = executor.findById(id.getId());
        return buildSucResp(dto);
    }


    /**
     * 修改短信模板
     * @param cmd 修改实体
     * @return 操作结果
     */
    @OptLog(module="sysSmsTemplate",title="修改短信模板",optType = LogOptTypeEnum.modify)
    @Operation(summary = "修改短信模板",tags = {"sysSmsTemplate"})
    @PostMapping("/modify")
    public ResultBean modify(@Validated @RequestBody SysSmsTemplateCmd cmd) {
        SysSmsTemplateContext context = convert.toContext(cmd);
        executor.update(context);
        return buildSucResp();
    }
}