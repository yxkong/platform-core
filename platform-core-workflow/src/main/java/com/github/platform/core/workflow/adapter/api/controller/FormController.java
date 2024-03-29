package com.github.platform.core.workflow.adapter.api.controller;

import com.github.platform.core.auth.annotation.RequiredLogin;
import com.github.platform.core.common.entity.StrIdReq;
import com.github.platform.core.log.domain.constants.LogOptTypeEnum;
import com.github.platform.core.log.infra.annotation.OptLog;
import com.github.platform.core.standard.entity.dto.PageBean;
import com.github.platform.core.standard.entity.dto.ResultBean;
import com.github.platform.core.standard.entity.vue.OptionsDto;
import com.github.platform.core.standard.util.ResultBeanUtil;
import com.github.platform.core.web.web.BaseController;
import com.github.platform.core.workflow.adapter.api.command.FormCmd;
import com.github.platform.core.workflow.adapter.api.command.FormInfoCmd;
import com.github.platform.core.workflow.adapter.api.command.FormInfoWrapCmd;
import com.github.platform.core.workflow.adapter.api.command.FormQuery;
import com.github.platform.core.workflow.adapter.api.convert.FormAdapterConvert;
import com.github.platform.core.workflow.application.executor.IFormExecutor;
import com.github.platform.core.workflow.domain.context.FormInfoWrapContext;
import com.github.platform.core.workflow.domain.context.FormQueryContext;
import com.github.platform.core.workflow.domain.dto.FormDetailDto;
import com.github.platform.core.workflow.domain.dto.FormDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
* 表单配置
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-11-17 18:23:18.130
* @version 1.0
*/
@RestController
@Tag(name = "form",description = "表单配置管理")
@RequestMapping("api/workflow/form")
@Slf4j
public class FormController extends BaseController{
    @Resource
    private IFormExecutor executor;
    @Resource
    private FormAdapterConvert convert;

    /**
    * 查询表单配置列表
    * @param query 查询实体
    * @return 分页结果
    */
    @OptLog(module="form",title="查询表单配置列表",persistent = false)
    @Operation(summary = "查询表单配置列表",tags = {"form"})
    @PostMapping("/query")
    public ResultBean<PageBean<FormDto>> query(@RequestBody FormQuery query){
        FormQueryContext context = convert.toQuery(query);
        PageBean<FormDto> pageBean = executor.query(context);
        return ResultBeanUtil.succ(pageBean);
    }

    /**
    * 新增表单配置
    * @param cmd 新增实体
    * @return 操作结果
    */
    @OptLog(module="form",title="新增表单配置",optType = LogOptTypeEnum.add)
    @Operation(summary = "新增表单配置",tags = {"form"})
    @PostMapping("/add")
    public ResultBean add(@Validated @RequestBody FormInfoWrapCmd cmd) {
        FormCmd basic = cmd.getBasic();
        List<FormInfoCmd> infos = cmd.getInfos();
        FormInfoWrapContext context = new FormInfoWrapContext();
        context.setBasic(convert.toContext(basic));
        context.setInfos(convert.toInfos(infos));
        executor.insert(context);
        return buildSucResp();
    }

    /**
    * 根据id查询表单配置明细
    * @param id 主键id
    * @return 单条记录
    */
    @OptLog(module="form",title="根据id查询表单配置明细",optType = LogOptTypeEnum.detail,persistent = false)
    @Operation(summary = "根据id查询表单配置明细",tags = {"form"})
    @PostMapping("/detail")
    public ResultBean<FormDetailDto> detail(@Validated @RequestBody StrIdReq id) {
        FormDetailDto dto = executor.findById(id.getId());
        return ResultBeanUtil.success(dto);
    }

    /**
     * 根据id删除表单配置记录
     * @param id 主键id
     * @return 操作结果
     */
    @OptLog(module="form",title="根据id删除表单配置记录",optType = LogOptTypeEnum.delete)
    @Operation(summary = "根据id删除表单配置记录",tags = {"form"})
    @PostMapping("/delete")
    public ResultBean delete(@Validated @RequestBody StrIdReq id) {
        executor.delete(id.getId());
        return buildSucResp();
    }

    /**
     * 修改表单配置
     * @param cmd 修改实体
     * @return 操作结果
     */
    @OptLog(module="form",title="修改表单配置",optType = LogOptTypeEnum.modify)
    @Operation(summary = "修改表单配置",tags = {"form"})
    @PostMapping("/modify")
    public ResultBean modify(@Validated @RequestBody FormInfoWrapCmd cmd) {
        FormCmd basic = cmd.getBasic();
        List<FormInfoCmd> infos = cmd.getInfos();
        FormInfoWrapContext context = new FormInfoWrapContext();
        context.setBasic(convert.toContext(basic));
        context.setInfos(convert.toInfos(infos));
        executor.update(context);
        return buildSucResp();
    }
    /**
     * 修改表单状态
     * @param id 修改实体
     * @return 操作结果
     */
    @OptLog(module="form",title="修改表单状态",optType = LogOptTypeEnum.modify)
    @Operation(summary = "修改表单状态",tags = {"form"})
    @PostMapping("/updateStatus")
    public ResultBean updateStatus(@Validated @RequestBody StrIdReq id) {
        executor.updateStatus(id.getId());
        return buildSucResp();
    }

    @OptLog(module="form",title="获取表单选项",optType = LogOptTypeEnum.query)
    @Operation(summary = "获取表单选项",tags = {"form"})
    @PostMapping("/allForms")
    @RequiredLogin
    public ResultBean<List<OptionsDto>> allForms(){
        List<OptionsDto> list = executor.allForms();
        return buildSucResp(list);
    }
}