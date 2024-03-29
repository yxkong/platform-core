package com.github.platform.core.workflow.adapter.api.controller;

import com.github.platform.core.auth.annotation.RequiredLogin;
import com.github.platform.core.common.utils.StringUtils;
import com.github.platform.core.standard.entity.vue.OptionsDto;
import com.github.platform.core.workflow.adapter.api.command.ProcessDefinitionDesignCmd;
import com.github.platform.core.workflow.adapter.api.convert.ProcessDefinitionAdapterConvert;
import com.github.platform.core.workflow.application.executor.IProcessDefinitionExecutor;
import com.github.platform.core.workflow.domain.constant.ProcessTypeEnum;
import com.github.platform.core.workflow.domain.context.ProcessDefinitionContext;
import com.github.platform.core.workflow.domain.context.ProcessDefinitionQueryContext;
import com.github.platform.core.workflow.domain.dto.ProcessDefinitionDto;
import com.github.platform.core.log.infra.annotation.OptLog;
import com.github.platform.core.workflow.adapter.api.command.ProcessDefinitionCmd;
import com.github.platform.core.workflow.adapter.api.command.ProcessDefinitionQuery;
import com.github.platform.core.common.entity.StrIdReq;
import com.github.platform.core.standard.entity.dto.PageBean;
import com.github.platform.core.log.domain.constants.LogOptTypeEnum;
import com.github.platform.core.standard.entity.dto.ResultBean;
import com.github.platform.core.standard.util.ResultBeanUtil;
import com.github.platform.core.web.web.BaseController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
* 流程定义
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-09-22 16:35:24.551
* @version 1.0
*/
@RestController
@Tag(name = "processDefinition",description = "流程定义管理")
@RequestMapping("/api/workflow/definition")
@Slf4j
public class ProcessDefinitionController extends BaseController{
    @Resource
    private IProcessDefinitionExecutor executor;
    @Resource
    private ProcessDefinitionAdapterConvert convert;

    /**
    * 查询流程定义列表
    * @param query 查询实体
    * @return 分页结果
    */
    @OptLog(module="processDefinition",title="查询流程定义列表",persistent = false)
    @Operation(summary = "查询流程定义列表",tags = {"processDefinition"})
    @PostMapping("/query")
    public ResultBean<PageBean<ProcessDefinitionDto>> query(@RequestBody ProcessDefinitionQuery query){
        ProcessDefinitionQueryContext context = convert.toQuery(query);
        PageBean<ProcessDefinitionDto> pageBean = executor.query(context);
        return buildSucResp(pageBean);
    }
    @OptLog(module="processDefinition",title="查询流程历史版本",persistent = false)
    @Operation(summary = "查询流程历史版本",tags = {"processDefinition"})
    @PostMapping("/queryHistory")
    public ResultBean<PageBean<ProcessDefinitionDto>> queryHistory(@RequestBody ProcessDefinitionQuery query){
        if (StringUtils.isEmpty(query.getProcessNo())){
            return ResultBeanUtil.paramEmpty("流程编号不能为空！");
        }
        ProcessDefinitionQueryContext context = convert.toQuery(query);
        PageBean<ProcessDefinitionDto> pageBean = executor.queryHistory(context);
        return buildSucResp(pageBean);
    }

    /**
    * 新增流程定义
    * @param cmd 新增实体
    * @return 操作结果
    */
    @OptLog(module="processDefinition",title="新增流程定义",optType = LogOptTypeEnum.add)
    @Operation(summary = "新增流程定义",tags = {"processDefinition"})
    @PostMapping("/add")
    public ResultBean add(@Validated @RequestBody ProcessDefinitionCmd cmd) {
        executor.insert(convert.toContext(cmd));
        return buildSucResp();
    }
    @OptLog(module="processDefinition",title="流程内容设计",optType = LogOptTypeEnum.modify)
    @Operation(summary = "流程内容设计",tags = {"processDefinition"})
    @PostMapping("/design")
    public ResultBean design(@Validated @RequestBody ProcessDefinitionDesignCmd cmd) {
        ProcessDefinitionContext context = ProcessDefinitionContext.builder().id(cmd.getId()).processNo(cmd.getProcessNo()).processFile(cmd.getProcessFile()).build();
        executor.designAndUpdate(context);
        return buildSucResp();
    }
    /**
    * 根据id查询流程定义明细
    * @param id 流程id
    * @return 单条记录
    */
    @OptLog(module="processDefinition",title="根据id查询流程定义明细",optType = LogOptTypeEnum.detail,persistent = false)
    @Operation(summary = "根据id查询流程定义明细",tags = {"processDefinition"})
    @PostMapping("/detail")
    public ResultBean<ProcessDefinitionDto> detail(@Validated @RequestBody StrIdReq id) {
        ProcessDefinitionDto dto = executor.findById(id.getId());
        return ResultBeanUtil.success(dto);
    }

    /**
     * 根据id删除流程定义记录
     * @param id 主键id
     * @return 操作结果
     */
    @OptLog(module="processDefinition",title="根据id删除流程定义记录",optType = LogOptTypeEnum.delete)
    @Operation(summary = "根据id删除流程定义记录",tags = {"processDefinition"})
    @PostMapping("/delete")
    public ResultBean delete(@Validated @RequestBody StrIdReq id) {
        executor.delete(id.getId());
        return buildSucResp();
    }

    /**
     * 修改流程定义
     * @param cmd 修改实体
     * @return 操作结果
     */
    @OptLog(module="processDefinition",title="修改流程定义",optType = LogOptTypeEnum.modify)
    @Operation(summary = "修改流程定义",tags = {"processDefinition"})
    @PostMapping("/modify")
    public ResultBean modify(@Validated @RequestBody ProcessDefinitionCmd cmd) {
        //流程类型不允许修改
        cmd.setProcessType(null);
        executor.update(convert.toContext(cmd));
        return buildSucResp();
    }
    /**
     * 根据id启用审批流
     * @param id 主键id
     * @return 操作结果
     */
    @OptLog(module="processDefinition",title="部署流程",optType = LogOptTypeEnum.trigger)
    @Operation(summary = "部署流程",tags = {"processDefinition"})
    @PostMapping("/deployById")
    public ResultBean deployById(@Validated @RequestBody StrIdReq id) {
        executor.startProcessById(id.getId());
        return buildSucResp();
    }
    /**
     * 根据id停用审批流
     * @param id 主键id
     * @return 操作结果
     */
    @OptLog(module="processDefinition",title="停用审批流",optType = LogOptTypeEnum.trigger)
    @Operation(summary = "停用审批流",tags = {"processDefinition"})
    @PostMapping("/stopProcessById")
    public ResultBean stopProcessById(@Validated @RequestBody StrIdReq id) {
        executor.stopProcessById(id.getId());
        return buildSucResp();
    }
    @OptLog(module="processDefinition",title="重置流程",optType = LogOptTypeEnum.trigger)
    @Operation(summary = "重置流程",tags = {"processDefinition"})
    @PostMapping("/rest")
    public ResultBean rest(@Validated @RequestBody StrIdReq id) {
        executor.rest(id.getId());
        return buildSucResp();
    }
    @RequiredLogin
    @OptLog(module="processDefinition",title="流程Option",optType = LogOptTypeEnum.trigger)
    @Operation(summary = "流程Option",tags = {"processDefinition"})
    @PostMapping("/processOptions")
    public ResultBean<List<OptionsDto>> processOptions(){
        return buildSucResp(executor.getProcessOptions(ProcessTypeEnum.PM));
    }
}