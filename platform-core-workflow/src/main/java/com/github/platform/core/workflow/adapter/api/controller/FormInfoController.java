package com.github.platform.core.workflow.adapter.api.controller;

import com.github.platform.core.common.entity.StrIdReq;
import com.github.platform.core.log.domain.constants.LogOptTypeEnum;
import com.github.platform.core.log.infra.annotation.OptLog;
import com.github.platform.core.standard.entity.dto.ResultBean;
import com.github.platform.core.web.web.BaseController;
import com.github.platform.core.workflow.application.executor.IFormInfoExecutor;
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
* 表单信息
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-11-17 18:23:21.269
* @version 1.0
*/
@RestController
@Tag(name = "formInfo",description = "表单信息管理")
@RequestMapping("api/workflow/form/info")
@Slf4j
public class FormInfoController extends BaseController{
    @Resource
    private IFormInfoExecutor executor;
    /**
     * 根据id删除表单信息记录
     * @param id 主键id
     * @return 操作结果
     */
    @OptLog(module="formInfo",title="根据id删除表单信息记录",optType = LogOptTypeEnum.delete)
    @Operation(summary = "根据id删除表单信息记录",tags = {"formInfo"})
    @PostMapping("/delete")
    public ResultBean delete(@Validated @RequestBody StrIdReq id) {
        executor.delete(id.getId());
        return buildSucResp();
    }
}