package com.github.platform.core.loadbalancer.adapter.api.controller;

import com.github.platform.core.common.entity.StrIdReq;
import com.github.platform.core.loadbalancer.adapter.api.command.GrayRuleCmd;
import com.github.platform.core.loadbalancer.adapter.api.command.GrayRuleQuery;
import com.github.platform.core.loadbalancer.adapter.api.convert.GrayRuleAdapterConvert;
import com.github.platform.core.loadbalancer.application.executor.IGrayRuleExecutor;
import com.github.platform.core.loadbalancer.domain.context.GrayRuleContext;
import com.github.platform.core.loadbalancer.domain.context.GrayRuleQueryContext;
import com.github.platform.core.loadbalancer.domain.dto.GrayRuleDto;
import com.github.platform.core.standard.entity.IdReq;
import com.github.platform.core.standard.entity.dto.PageBean;
import com.github.platform.core.standard.entity.dto.ResultBean;
import com.github.platform.core.web.web.BaseController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 灰度规则管理
 * @author: yxkong
 * @date: 2023/4/14 9:50 下午
 * @version: 1.0
 */
@RestController
@Tag(name = "grayRule",description = "灰度规则相关")
@RequestMapping(value = "/sys/gray", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class GrayController extends BaseController {

    @Resource
    private GrayRuleAdapterConvert convert;
    @Resource
    private IGrayRuleExecutor executor;
    @Operation(summary = "灰度规则查询",tags = {"grayRule"})
    @PostMapping("/query")
    public ResultBean<PageBean<GrayRuleDto>> query(@RequestBody GrayRuleQuery grayRuleQuery) {
        GrayRuleQueryContext context = convert.toQuery(grayRuleQuery);
        PageBean<GrayRuleDto> pageBean = executor.query(context);
        return buildSucResp(pageBean);
    }
    @Operation(summary = "新增灰度规则",tags = {"grayRule"})
    @PostMapping("/add")
    public ResultBean add(@Validated @RequestBody GrayRuleCmd cmd) {
        GrayRuleContext context = convert.toContext(cmd);
        executor.insert(context);
        return buildSucResp();
    }
    @Operation(summary = "修改灰度规则",tags = {"grayRule"})
    @PostMapping("/modify")
    public ResultBean modify(@Validated @RequestBody GrayRuleCmd cmd) {
        GrayRuleContext context = convert.toContext(cmd);
        executor.update(context);
        return buildSucResp();
    }
    @Operation(summary = "删除灰度规则",tags = {"grayRule"})
    @PostMapping("/delete")
    public ResultBean delete(@Validated @RequestBody StrIdReq idReq) {
        executor.delete(idReq.getId());
        return buildSucResp();
    }
}
