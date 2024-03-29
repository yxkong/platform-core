package com.github.platform.core.workflow.adapter.api.controller;

import com.github.platform.core.auth.annotation.RequiredLogin;
import com.github.platform.core.common.entity.StrIdReq;
import com.github.platform.core.log.domain.constants.LogOptTypeEnum;
import com.github.platform.core.log.infra.annotation.OptLog;
import com.github.platform.core.standard.entity.dto.PageBean;
import com.github.platform.core.standard.entity.dto.ResultBean;
import com.github.platform.core.standard.entity.vue.OptionsDto;
import com.github.platform.core.web.web.BaseController;
import com.github.platform.core.workflow.adapter.api.command.ProcessConditionCmd;
import com.github.platform.core.workflow.adapter.api.command.ProcessConditionQuery;
import com.github.platform.core.workflow.adapter.api.convert.ProcessConditionAdapterConvert;
import com.github.platform.core.workflow.application.executor.IProcessConditionExecutor;
import com.github.platform.core.workflow.domain.context.ProcessConditionQueryContext;
import com.github.platform.core.workflow.domain.dto.ProcessConditionDto;
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
* 流程条件
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-11-30 18:17:25.794
* @version 1.0
*/
@RestController
@Tag(name = "processCondition",description = "流程条件管理")
@RequestMapping("api/workflow/condition")
@Slf4j
public class ProcessConditionController extends BaseController{
    @Resource
    private IProcessConditionExecutor executor;
    @Resource
    private ProcessConditionAdapterConvert convert;

    /**
    * 查询流程条件列表
    * @param query 查询实体
    * @return 分页结果
    */
    @OptLog(module="processCondition",title="查询流程条件列表",persistent = false)
    @Operation(summary = "查询流程条件列表",tags = {"processCondition"})
    @PostMapping("/query")
    public ResultBean<PageBean<ProcessConditionDto>> query(@RequestBody ProcessConditionQuery query){
        ProcessConditionQueryContext context = convert.toQuery(query);
        PageBean<ProcessConditionDto> pageBean = executor.query(context);
        return buildSucResp(pageBean);
    }

    /**
    * 新增流程条件
    * @param cmd 新增实体
    * @return 操作结果
    */
    @OptLog(module="processCondition",title="新增流程条件",optType = LogOptTypeEnum.add)
    @Operation(summary = "新增流程条件",tags = {"processCondition"})
    @PostMapping("/add")
    public ResultBean<String> add(@Validated @RequestBody ProcessConditionCmd cmd) {
        String id = executor.insert(convert.toContext(cmd));
        return buildSucResp(id);
    }

    /**
    * 根据id查询流程条件明细
    * @param id 主键id
    * @return 单条记录
    */
    @OptLog(module="processCondition",title="根据id查询流程条件明细",optType = LogOptTypeEnum.detail,persistent = false)
    @Operation(summary = "根据id查询流程条件明细",tags = {"processCondition"})
    @PostMapping("/detail")
    public ResultBean<ProcessConditionDto> detail(@Validated @RequestBody StrIdReq id) {
        ProcessConditionDto dto = executor.findById(id.getId());
        return buildSucResp(dto);
    }

    /**
     * 根据id删除流程条件记录
     * @param id 主键id
     * @return 操作结果
     */
    @OptLog(module="processCondition",title="根据id删除流程条件记录",optType = LogOptTypeEnum.delete)
    @Operation(summary = "根据id删除流程条件记录",tags = {"processCondition"})
    @PostMapping("/delete")
    public ResultBean delete(@Validated @RequestBody StrIdReq id) {
        executor.delete(id.getId());
        return buildSucResp();
    }

    /**
     * 修改流程条件
     * @param cmd 修改实体
     * @return 操作结果
     */
    @OptLog(module="processCondition",title="修改流程条件",optType = LogOptTypeEnum.modify)
    @Operation(summary = "修改流程条件",tags = {"processCondition"})
    @PostMapping("/modify")
    public ResultBean modify(@Validated @RequestBody ProcessConditionCmd cmd) {
        executor.update(convert.toContext(cmd));
        return buildSucResp();
    }

    @OptLog(module="processCondition",title="获取流程条件选项",optType = LogOptTypeEnum.query)
    @Operation(summary = "获取流程条件选项",tags = {"processCondition"})
    @PostMapping("/getByType")
    @RequiredLogin
    public ResultBean<List<OptionsDto>> getByType(ProcessConditionQuery query){
        List<OptionsDto> list = executor.getByType(query.getProcessType());
        return buildSucResp(list);
    }
}