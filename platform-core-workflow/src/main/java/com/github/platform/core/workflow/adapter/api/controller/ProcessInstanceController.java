package com.github.platform.core.workflow.adapter.api.controller;

import com.github.platform.core.auth.util.LoginUserInfoUtil;
import com.github.platform.core.common.entity.StrIdReq;
import com.github.platform.core.workflow.adapter.api.command.*;
import com.github.platform.core.workflow.adapter.api.convert.ProcessInstanceAdapterConvert;
import com.github.platform.core.workflow.application.executor.IProcessInstanceExecutor;
import com.github.platform.core.workflow.domain.dto.ProcessInstanceDto;
import com.github.platform.core.log.infra.annotation.OptLog;
import com.github.platform.core.workflow.domain.context.ProcessInstanceQueryContext;
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
/**
* 流程实例
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-09-22 16:35:27.395
* @version 1.0
*/
@RestController
@Tag(name = "processInstance",description = "流程实例管理")
@RequestMapping("/api/workflow/instance")
@Slf4j
public class ProcessInstanceController extends BaseController{
    @Resource
    private IProcessInstanceExecutor executor;
    @Resource
    private ProcessInstanceAdapterConvert convert;

    /**
    * 查询流程实例列表
    * @param query 查询实体
    * @return 分页结果
    */
    @OptLog(module="processInstance",title="查询流程实例列表",persistent = false)
    @Operation(summary = "查询流程实例列表",tags = {"processInstance"})
    @PostMapping("/query")
    public ResultBean<PageBean<ProcessInstanceDto>> query(@RequestBody ProcessInstanceQuery query){
        ProcessInstanceQueryContext context = convert.toQuery(query);
        PageBean<ProcessInstanceDto> pageBean = executor.query(context);
        return buildSucResp(pageBean);
    }

    /**
    * 新增流程实例
    * @param cmd 新增实体
    * @return 操作结果
    */
    @OptLog(module="processInstance",title="新增流程实例",optType = LogOptTypeEnum.add)
    @Operation(summary = "新增流程实例",tags = {"processInstance"})
    @PostMapping("/add")
    public ResultBean add(@Validated @RequestBody ProcessInstanceCmd cmd) {
        executor.insert(convert.toContext(cmd));
        return buildSucResp();
    }

    /**
    * 根据id查询流程实例明细
    * @param id 主键id
    * @return 单条记录
    */
    @OptLog(module="processInstance",title="根据id查询流程实例明细",optType = LogOptTypeEnum.detail,persistent = false)
    @Operation(summary = "根据id查询流程实例明细",tags = {"processInstance"})
    @PostMapping("/detail")
    public ResultBean<ProcessInstanceDto> detail(@Validated @RequestBody StrIdReq id) {
        ProcessInstanceDto dto = executor.findById(id.getId());
        return ResultBeanUtil.success(dto);
    }

    /**
     * 根据id删除流程实例记录
     * @param id 主键id
     * @return 操作结果
     */
    @OptLog(module="processInstance",title="根据id删除流程实例记录",optType = LogOptTypeEnum.delete)
    @Operation(summary = "根据id删除流程实例记录",tags = {"processInstance"})
    @PostMapping("/delete")
    public ResultBean delete(@Validated @RequestBody StrIdReq id) {
        executor.delete(id.getId());
        return buildSucResp();
    }

    /**
     * 修改流程实例
     * @param cmd 修改实体
     * @return 操作结果
     */
    @OptLog(module="processInstance",title="修改流程实例",optType = LogOptTypeEnum.modify)
    @Operation(summary = "修改流程实例",tags = {"processInstance"})
    @PostMapping("/updateStatus")
    public ResultBean updateStatus(@Validated @RequestBody ProcessInstanceCmd cmd) {

        /**
         *  TODO 更新状态这里，需要看是那种状态，见InstanceStatusEnum
         *  1，挂起，不中断流程，流程暂停
         *  0，启用流程
         *  2，停用流程
         *  3，完成流程
         */

        executor.updateStatus(cmd.getId(), LoginUserInfoUtil.getLoginName()+"修改状态");
        return buildSucResp();
    }

}