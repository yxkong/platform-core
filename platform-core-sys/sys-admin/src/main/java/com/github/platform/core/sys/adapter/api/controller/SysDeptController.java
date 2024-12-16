package com.github.platform.core.sys.adapter.api.controller;


import com.github.platform.core.auth.annotation.RequiredLogin;
import com.github.platform.core.common.entity.StrIdReq;
import com.github.platform.core.log.domain.constants.LogOptTypeEnum;
import com.github.platform.core.log.infra.annotation.OptLog;
import com.github.platform.core.standard.entity.dto.ResultBean;
import com.github.platform.core.standard.util.ResultBeanUtil;
import com.github.platform.core.sys.adapter.api.command.dept.SysDeptCmd;
import com.github.platform.core.sys.adapter.api.command.dept.SysDeptQuery;
import com.github.platform.core.sys.adapter.api.convert.SysDeptAdapterConvert;
import com.github.platform.core.sys.application.executor.ISysDeptExecutor;
import com.github.platform.core.sys.domain.context.SysDeptContext;
import com.github.platform.core.sys.domain.context.SysDeptQueryContext;
import com.github.platform.core.sys.domain.dto.SysDeptDto;
import com.github.platform.core.sys.domain.dto.resp.TreeSelectDto;
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
import java.util.List;
import java.util.Objects;

/**
 * 部门相关
 *
 * @author: yxkong
 * @date: 2023/2/7 5:32 下午
 * @version: 1.0
 */
@RestController
@Tag(name = "dept",description = "部门相关")
@RequestMapping(value = "/sys/dept", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class SysDeptController extends BaseController {
    @Resource
    private ISysDeptExecutor deptExecutor;

    @Resource
    private SysDeptAdapterConvert convert;

    /**
     * 部门列表查询
     *
     * @param query
     * @return
     */
    @OptLog(module = "dept",title = "部门查询",persistent = false)
    @Operation(summary = "部门查询",tags = {"dept"})
    @PostMapping("/query")
    public ResultBean<List<SysDeptDto>> query(@RequestBody SysDeptQuery query) {
        SysDeptQueryContext deptContext = convert.toQuery(query);
        List<SysDeptDto> list = deptExecutor.query(deptContext);
        return buildSucResp(list);
    }

    /**
     * 新增部门
     *
     * @param cmd
     * @return
     */
    @OptLog(module="dept",title="新增部门",optType = LogOptTypeEnum.add)
    @Operation(summary = "新增部门",tags = {"dept"})
    @PostMapping("/add")
    public ResultBean add(@RequestBody @Validated SysDeptCmd cmd) {
        SysDeptContext context = convert.toContext(cmd);
        deptExecutor.insert(context);
        return buildSucResp();
    }

    /**
     * 修改部门
     *
     * @param cmd
     * @return
     */
    @OptLog(module="dept",title="修改部门",optType = LogOptTypeEnum.modify)
    @Operation(summary = "修改部门",tags = {"dept"})
    @PostMapping("/modify")
    public ResultBean modify(@RequestBody @Validated SysDeptCmd cmd) {
        if (Objects.isNull(cmd.getId())){
            return ResultBeanUtil.fail("部门id不能为空",null);
        }
        SysDeptContext deptContext = convert.toContext(cmd);
        deptExecutor.modify(deptContext);
        return buildSucResp();
    }

    /**
     * 删除部门
     *
     * @param idReq
     * @return
     */
    @OptLog(module="dept",title="删除部门",optType = LogOptTypeEnum.delete)
    @Operation(summary = "删除部门",tags = {"dept"})
    @PostMapping("/delete")
    public ResultBean<?> delete(@RequestBody @Validated StrIdReq idReq) {
        deptExecutor.delete(idReq.getId());
        return buildSucResp();
    }

    /**
     * 获取部门树列表
     */
    @RequiredLogin
    @OptLog(module = "dept",title = "获取部门树列表",persistent = false)
    @Operation(summary = "获取部门树列表",tags = {"dept"})
    @PostMapping("/deptTree")
    public ResultBean<List<TreeSelectDto>> deptTree() {
        List<TreeSelectDto> list = deptExecutor.deptTree();
        return buildSucResp(list);
    }

}
