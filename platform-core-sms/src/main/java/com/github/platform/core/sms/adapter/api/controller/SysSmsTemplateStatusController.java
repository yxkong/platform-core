package com.github.platform.core.sms.adapter.api.controller;

import com.github.platform.core.log.infra.annotation.OptLog;
import com.github.platform.core.common.entity.StrIdReq;
import com.github.platform.core.sms.adapter.api.command.SysSmsTemplateStatusCmd;
import com.github.platform.core.sms.adapter.api.command.SysSmsTemplateStatusQuery;
import com.github.platform.core.sms.adapter.api.convert.SysSmsTemplateStatusAdapterConvert;
import com.github.platform.core.sms.application.executor.ISysSmsTemplateStatusExecutor;
import com.github.platform.core.sms.domain.context.SysSmsTemplateStatusQueryContext;
import com.github.platform.core.sms.domain.dto.SysSmsTemplateStatusDto;
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
 * 模板厂商
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author yxkong
 * @date 2023-12-25 15:04:33.948
 * @version 1.0
 */
@RestController
@Tag(name = "sysSmsTemplateStatus",description = "模板厂商管理")
@RequestMapping("sys/sms/template/status")
@Slf4j
public class SysSmsTemplateStatusController extends BaseController{
    @Resource
    private ISysSmsTemplateStatusExecutor executor;
    @Resource
    private SysSmsTemplateStatusAdapterConvert convert;

    /**
    * 查询模板厂商列表
    * @param query 查询实体
    * @return 分页结果
    */
    @OptLog(module="sysSmsTemplateStatus",title="查询模板厂商列表",persistent = false)
    @Operation(summary = "查询模板厂商列表",tags = {"sysSmsTemplateStatus"})
    @PostMapping("/query")
    public ResultBean<PageBean<SysSmsTemplateStatusDto>> query(@RequestBody SysSmsTemplateStatusQuery query){
        SysSmsTemplateStatusQueryContext context = convert.toQuery(query);
        PageBean<SysSmsTemplateStatusDto> pageBean = executor.query(context);
        return buildSucResp(pageBean);
    }

    /**
    * 新增模板厂商
    * @param cmd 新增实体
    * @return 操作结果
    */
    @OptLog(module="sysSmsTemplateStatus",title="新增模板厂商",optType = LogOptTypeEnum.add)
    @Operation(summary = "新增模板厂商",tags = {"sysSmsTemplateStatus"})
    @PostMapping("/add")
    public ResultBean<String> add(@Validated @RequestBody SysSmsTemplateStatusCmd cmd) {
        String id = executor.insert(convert.toContext(cmd));
        return buildSucResp(id);
    }

    /**
    * 根据id查询模板厂商明细
    * @param id 主键id
    * @return 单条记录
    */
    @OptLog(module="sysSmsTemplateStatus",title="根据id查询模板厂商明细",optType = LogOptTypeEnum.detail,persistent = false)
    @Operation(summary = "根据id查询模板厂商明细",tags = {"sysSmsTemplateStatus"})
    @PostMapping("/detail")
    public ResultBean<SysSmsTemplateStatusDto> detail(@Validated @RequestBody StrIdReq id) {
        SysSmsTemplateStatusDto dto = executor.findById(id.getId());
        return buildSucResp(dto);
    }

    /**
     * 修改模板厂商
     * @param cmd 修改实体
     * @return 操作结果
     */
    @OptLog(module="sysSmsTemplateStatus",title="修改模板厂商",optType = LogOptTypeEnum.modify)
    @Operation(summary = "修改模板厂商",tags = {"sysSmsTemplateStatus"})
    @PostMapping("/modify")
    public ResultBean modify(@Validated @RequestBody SysSmsTemplateStatusCmd cmd) {
        executor.update(convert.toContext(cmd));
        return buildSucResp();
    }
}