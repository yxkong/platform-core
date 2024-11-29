package com.github.platform.core.sys.adapter.api.controller;

import com.github.platform.core.auth.annotation.RequiredLogin;
import com.github.platform.core.auth.util.LoginUserInfoUtil;
import com.github.platform.core.common.utils.StringUtils;
import com.github.platform.core.log.infra.annotation.OptLog;
import com.github.platform.core.common.entity.StrIdReq;
import com.github.platform.core.standard.constant.ResultStatusEnum;
import com.github.platform.core.sys.adapter.api.command.SysFlowRuleCmd;
import com.github.platform.core.sys.adapter.api.command.SysFlowRuleQuery;
import com.github.platform.core.sys.adapter.api.convert.SysFlowRuleAdapterConvert;
import com.github.platform.core.sys.application.executor.ISysFlowRuleExecutor;
import com.github.platform.core.sys.domain.context.SysFlowRuleContext;
import com.github.platform.core.sys.domain.context.SysFlowRuleQueryContext;
import com.github.platform.core.sys.domain.dto.SysFlowRuleDto;
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

import java.util.List;
import java.util.Objects;

/**
 * 状态机配置规则
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author yxkong
 * @date 2024-08-29 10:25:01.691
 * @version 1.0
 */
@RestController
@Tag(name = "sysFlowRule",description = "状态机配置规则管理")
@RequestMapping("api/sys/flowRule")
@Slf4j
public class SysFlowRuleController extends BaseController{
    @Resource
    private ISysFlowRuleExecutor sysFlowRuleExecutor;
    @Resource
    private SysFlowRuleAdapterConvert sysFlowRuleConvert;

    /**
    * 查询状态机配置规则列表
    * @param query 查询实体
    * @return 分页结果
    */
    @Operation(summary = "查询状态机配置规则列表",tags = {"sysFlowRule"})
    @PostMapping("/query")
    public ResultBean<PageBean<SysFlowRuleDto>> query(@RequestBody SysFlowRuleQuery query){
        SysFlowRuleQueryContext context = sysFlowRuleConvert.toQuery(query);
        PageBean<SysFlowRuleDto> pageBean = sysFlowRuleExecutor.query(context);
        return buildSucResp(pageBean);
    }

    /**
     * 查询状态机配置规则列表
     * @param query 查询实体
     * @return 分页结果
     */
    @RequiredLogin
    @Operation(summary = "根据bizType状态映射",tags = {"sysFlowRule"})
    @PostMapping("/findByBizType")
    public ResultBean<List<SysFlowRuleDto>> findByBizType(@RequestBody SysFlowRuleQuery query){
        if (StringUtils.isEmpty(query.getBizType())){
            throw exception(ResultStatusEnum.PARAM_EMPTY.getStatus(),"bizType不能为空");
        }
        List<SysFlowRuleDto> list = sysFlowRuleExecutor.findByBizType(query.getBizType());
        return buildSucResp(list);
    }


    /**
    * 新增状态机配置规则
    * @param cmd 新增实体
    * @return 操作结果
    */
    @OptLog(module="sysFlowRule",title="新增状态机配置规则",optType = LogOptTypeEnum.add)
    @Operation(summary = "新增状态机配置规则",tags = {"sysFlowRule"})
    @PostMapping("/add")
    public ResultBean<String> add(@Validated @RequestBody SysFlowRuleCmd cmd) {
        SysFlowRuleContext context = sysFlowRuleConvert.toContext(cmd);
        String id = sysFlowRuleExecutor.insert(context);

        return buildSucResp(id);
    }

    /**
    * 根据id查询状态机配置规则明细
    * @param id 主键id
    * @return 单条记录
    */
    @OptLog(module="sysFlowRule",title="根据id查询状态机配置规则明细",optType = LogOptTypeEnum.detail,persistent = false)
    @Operation(summary = "根据id查询状态机配置规则明细",tags = {"sysFlowRule"})
    @PostMapping("/detail")
    public ResultBean<SysFlowRuleDto> detail(@Validated @RequestBody StrIdReq id) {
        SysFlowRuleDto dto = sysFlowRuleExecutor.findById(id.getId());
        return buildSucResp(dto);
    }

    /**
     * 根据id删除状态机配置规则记录
     * @param id 主键id
     * @return 操作结果
     */
    @OptLog(module="sysFlowRule",title="根据id删除状态机配置规则记录",optType = LogOptTypeEnum.delete)
    @Operation(summary = "根据id删除状态机配置规则记录",tags = {"sysFlowRule"})
    @PostMapping("/delete")
    public ResultBean delete(@Validated @RequestBody StrIdReq id) {
        sysFlowRuleExecutor.delete(id.getId());
        return buildSucResp();
    }

    /**
     * 修改状态机配置规则
     * @param cmd 修改实体
     * @return 操作结果
     */
    @OptLog(module="sysFlowRule",title="修改状态机配置规则",optType = LogOptTypeEnum.modify)
    @Operation(summary = "修改状态机配置规则",tags = {"sysFlowRule"})
    @PostMapping("/modify")
    public ResultBean modify(@Validated @RequestBody SysFlowRuleCmd cmd) {
        sysFlowRuleExecutor.update(sysFlowRuleConvert.toContext(cmd));
        return buildSucResp();
    }
}