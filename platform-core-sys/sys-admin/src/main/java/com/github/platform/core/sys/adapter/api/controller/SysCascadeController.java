package com.github.platform.core.sys.adapter.api.controller;

import com.github.platform.core.auth.annotation.RequiredLogin;
import com.github.platform.core.common.entity.StrIdReq;
import com.github.platform.core.common.utils.StringUtils;
import com.github.platform.core.log.domain.constants.LogOptTypeEnum;
import com.github.platform.core.log.infra.annotation.OptLog;
import com.github.platform.core.standard.entity.dto.PageBean;
import com.github.platform.core.standard.entity.dto.ResultBean;
import com.github.platform.core.sys.adapter.api.command.SysCascadeCmd;
import com.github.platform.core.sys.adapter.api.command.SysCascadeQuery;
import com.github.platform.core.sys.adapter.api.convert.SysCascadeAdapterConvert;
import com.github.platform.core.sys.application.executor.ISysCascadeExecutor;
import com.github.platform.core.sys.domain.constant.DeptConstant;
import com.github.platform.core.sys.domain.context.SysCascadeContext;
import com.github.platform.core.sys.domain.context.SysCascadeQueryContext;
import com.github.platform.core.sys.domain.dto.SysCascadeDto;
import com.github.platform.core.sys.domain.dto.resp.TreeSelectDto;
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
import java.util.List;
import java.util.Objects;

/**
 * 级联表
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author yxkong
 * @date 2024-12-01 20:57:44.667
 * @version 1.0
 */
@RestController
@Tag(name = "sysCascade",description = "级联表管理")
@RequestMapping("sys/cascade")
@Slf4j
public class SysCascadeController extends BaseController{
    @Resource
    private ISysCascadeExecutor sysCascadeExecutor;
    @Resource
    private SysCascadeAdapterConvert sysCascadeConvert;

    /**
    * 查询级联表列表
    * @param query 查询实体
    * @return 分页结果
    */
    @OptLog(module="sysCascade",title="查询级联表列表",persistent = false)
    @Operation(summary = "查询级联表列表",tags = {"sysCascade"})
    @PostMapping("/query")
    public ResultBean<PageBean<SysCascadeDto>> query(@RequestBody SysCascadeQuery query){
        SysCascadeQueryContext context = sysCascadeConvert.toQuery(query);
        //默认只查顶级节点
        if (StringUtils.isEmpty(context.getCode()) && Objects.isNull(context.getParentId())){
            context.setParentId(0L);
        }
        PageBean<SysCascadeDto> pageBean = sysCascadeExecutor.query(context);
        return buildSucResp(pageBean);
    }

    /**
     * 查询级联表列表
     * @param query 查询实体
     * @return 分页结果
     */
    @OptLog(module="sysCascade",title="查询级联表列表",persistent = false)
    @Operation(summary = "查询级联表列表",tags = {"sysCascade"})
    @PostMapping("/list")
    public ResultBean<List<SysCascadeDto>> list(@RequestBody SysCascadeQuery query){
        SysCascadeQueryContext context = sysCascadeConvert.toQuery(query);
        //默认只查顶级节点
        if (StringUtils.isEmpty(context.getCode()) && Objects.isNull(context.getParentId())){
            context.setParentId(0L);
        }
        List<SysCascadeDto> list = sysCascadeExecutor.list(context);
        return buildSucResp(list);
    }

    @RequiredLogin
    @OptLog(module="sysCascade",title="查询指定id或code的树形结构",persistent = false)
    @Operation(summary = "查询指定id或code的树形结构",tags = {"sysCascade"})
    @PostMapping("/tree")
    public ResultBean<List<TreeSelectDto>> tree(@RequestBody SysCascadeQuery query) {
        SysCascadeQueryContext context = sysCascadeConvert.toQuery(query);
        List<TreeSelectDto> treeSelectDtos = sysCascadeExecutor.tree(context);
        return buildSucResp(treeSelectDtos);
    }
    /**
    * 新增级联表
    * @param cmd 新增实体
    * @return 操作结果
    */
    @OptLog(module="sysCascade",title="新增级联表",optType = LogOptTypeEnum.add)
    @Operation(summary = "新增级联表",tags = {"sysCascade"})
    @PostMapping("/add")
    public ResultBean<SysCascadeDto> add(@Validated @RequestBody SysCascadeCmd cmd) {
        SysCascadeContext context= sysCascadeConvert.toContext(cmd);
        if (Objects.isNull(context.getParentId())){
            context.setParentId(DeptConstant.ROOT_ID);
        }
        SysCascadeDto dto = sysCascadeExecutor.insert(context);
        return buildSucResp(dto);
    }

    /**
    * 根据id查询级联表明细
    * @param id 主键id
    * @return 单条记录
    */
    @OptLog(module="sysCascade",title="根据id查询级联表明细",optType = LogOptTypeEnum.detail,persistent = false)
    @Operation(summary = "根据id查询级联表明细",tags = {"sysCascade"})
    @PostMapping("/detail")
    public ResultBean<SysCascadeDto> detail(@Validated @RequestBody StrIdReq id) {
        SysCascadeDto dto = sysCascadeExecutor.findById(id.getId());
        return buildSucResp(dto);
    }

    /**
     * 根据id删除级联表记录
     * @param id 主键id
     * @return 操作结果
     */
    @OptLog(module="sysCascade",title="根据id删除级联表记录",optType = LogOptTypeEnum.delete)
    @Operation(summary = "根据id删除级联表记录",tags = {"sysCascade"})
    @PostMapping("/delete")
    public ResultBean delete(@Validated @RequestBody StrIdReq id) {
        sysCascadeExecutor.delete(id.getId());
        return buildSucResp();
    }

    /**
     * 修改级联表
     * @param cmd 修改实体
     * @return 操作结果
     */
    @OptLog(module="sysCascade",title="修改级联表",optType = LogOptTypeEnum.modify)
    @Operation(summary = "修改级联表",tags = {"sysCascade"})
    @PostMapping("/modify")
    public ResultBean modify(@Validated @RequestBody SysCascadeCmd cmd) {
        sysCascadeExecutor.update(sysCascadeConvert.toContext(cmd));
        return buildSucResp();
    }
}