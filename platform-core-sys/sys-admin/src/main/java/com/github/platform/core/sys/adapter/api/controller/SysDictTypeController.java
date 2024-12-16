package com.github.platform.core.sys.adapter.api.controller;


import com.github.platform.core.common.entity.StrIdReq;
import com.github.platform.core.log.domain.constants.LogOptTypeEnum;
import com.github.platform.core.log.infra.annotation.OptLog;
import com.github.platform.core.standard.entity.dto.PageBean;
import com.github.platform.core.standard.entity.dto.ResultBean;
import com.github.platform.core.sys.adapter.api.command.dict.SysDictTypeCmd;
import com.github.platform.core.sys.adapter.api.command.dict.SysDictTypeQuery;
import com.github.platform.core.sys.adapter.api.convert.SysDictTypeAdapterConvert;
import com.github.platform.core.sys.application.executor.ISysDictTypeExecutor;
import com.github.platform.core.sys.domain.context.SysDictTypeContext;
import com.github.platform.core.sys.domain.context.SysDictTypeQueryContext;
import com.github.platform.core.sys.domain.dto.SysDictTypeDto;
import com.github.platform.core.web.web.BaseController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
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
 * 字典类型信息
 *
 * @author: yxkong
 * @date: 2023/2/7 5:32 下午
 * @version: 1.0
 */
@RestController
@Tag(name = "dict",description = "字典类型信息")
@RequestMapping(value = "/sys/dict/type", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class SysDictTypeController extends BaseController {
    @Resource
    private ISysDictTypeExecutor dictTypeExecutor;

    @Resource
    private SysDictTypeAdapterConvert convert;

    /**
     * 字典类型查询
     *
     * @param query
     * @return
     */
    @OptLog(module = "dict",title = "字典类型查询",persistent = false)
    @Operation(summary = "字典类型查询",tags = {"dict"})
    @PostMapping("/query")
    public ResultBean<PageBean<SysDictTypeDto>> query(@RequestBody SysDictTypeQuery query) {
        SysDictTypeQueryContext context = convert.toQuery(query);
        PageBean<SysDictTypeDto> pageBean = dictTypeExecutor.query(context);
        return buildSucResp(pageBean);
    }

    /**
     * 新增字典类型
     *
     * @param cmd
     * @return
     */
    @OptLog(module="dict",title="新增字典类型",optType = LogOptTypeEnum.add)
    @Operation(summary = "新增字典类型",tags = {"dict"})
    @PostMapping("/add")
    public ResultBean<Void> add(@RequestBody @Validated SysDictTypeCmd cmd) {
        SysDictTypeContext context = convert.toContext(cmd);
        dictTypeExecutor.insert(context);
        return buildSucResp();
    }
    /**
     * 修改字典类型
     * @param cmd
     * @return
     */
    @OptLog(module="dict",title="修改字典类型",optType = LogOptTypeEnum.modify)
    @Operation(summary = "修改字典类型，只能修改名称和备注",tags = {"dict"},
            parameters = @Parameter(name = "dictTypeCmd", description = "DictTypeCmd对象", required = true,schema = @Schema(implementation = SysDictTypeCmd.class))
    )
    @PostMapping("modify")
    public ResultBean modify(@RequestBody @Validated SysDictTypeCmd cmd) {
        SysDictTypeContext context = convert.toContext(cmd);
        dictTypeExecutor.update(context);
        return buildSucResp();
    }

    /**
     * 删除字典类型
     *
     * @param id
     * @return
     */
    @OptLog(module="dict",title="删除字典类型",optType = LogOptTypeEnum.delete)
    @Operation(summary = "删除字典类型",tags = {"dict"})
    @PostMapping("/delete")
    public ResultBean<Void> delete(@RequestBody @Validated StrIdReq id) {
        dictTypeExecutor.delete(id.getId());
        return buildSucResp();
    }

    @OptLog(module = "dict",title = "重载字典",optType = LogOptTypeEnum.modify)
    @Operation(summary = "重载字典",tags = {"dict"})
    @PostMapping("/reload")
    public ResultBean reload(@RequestBody SysDictTypeQuery query) {
        SysDictTypeQueryContext context = convert.toQuery(query);
        dictTypeExecutor.reload(context);
        return buildSucResp();
    }
}
