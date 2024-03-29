package com.github.platform.core.sys.adapter.api.controller;


import com.github.platform.core.auth.annotation.RequiredLogin;
import com.github.platform.core.cache.domain.entity.DictEntity;
import com.github.platform.core.common.entity.StrIdReq;
import com.github.platform.core.log.domain.constants.LogOptTypeEnum;
import com.github.platform.core.log.infra.annotation.OptLog;
import com.github.platform.core.standard.entity.dto.PageBean;
import com.github.platform.core.standard.entity.dto.ResultBean;
import com.github.platform.core.sys.adapter.api.command.dict.SysDictCmd;
import com.github.platform.core.sys.adapter.api.command.dict.SysDictQuery;
import com.github.platform.core.sys.adapter.api.convert.SysDictAdapterConvert;
import com.github.platform.core.sys.application.executor.ISysDictExecutor;
import com.github.platform.core.sys.domain.context.SysDictContext;
import com.github.platform.core.sys.domain.context.SysDictQueryContext;
import com.github.platform.core.sys.domain.dto.SysDictDto;
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

/**
 * 字典数据信息
 *
 * @author: yxkong
 * @date: 2023/2/7 5:32 下午
 * @version: 1.0
 */
@RestController
@Tag(name = "dict",description = "字典数据信息")
@RequestMapping(value = "/sys/dict", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class SysDictController extends BaseController {
    @Resource
    private ISysDictExecutor dictExecutor;

    @Resource
    private SysDictAdapterConvert convert;

    /**
     * 字典数据查询
     *
     * @param query
     * @return
     */
    @OptLog(module = "dict",title = "字典数据查询",persistent = false)
    @Operation(summary = "字典数据查询",tags = {"dict"})
    @PostMapping("/query")
    public ResultBean<PageBean<SysDictDto>> query(@RequestBody @Validated SysDictQuery query) {
        SysDictQueryContext context = convert.toQuery(query);
        PageBean<SysDictDto> pageBean = dictExecutor.query(context);
        return buildSucResp(pageBean);
    }

    /**
     * 新增字典数据
     *
     * @param cmd
     * @return
     */
    @OptLog(module="dict",title="新增字典数据",optType = LogOptTypeEnum.add)
    @Operation(summary = "新增字典数据",tags = {"dict"})
    @PostMapping("/add")
    public ResultBean add(@RequestBody @Validated SysDictCmd cmd) {
        SysDictContext context = convert.toDict(cmd);
        dictExecutor.insert(context);
        return buildSucResp();
    }
    /**
     * 新增字典数据
     *
     * @param cmd
     * @return
     */
    @OptLog(module="dict",title="修改字典数据",optType = LogOptTypeEnum.modify)
    @Operation(summary = "修改字典数据",tags = {"dict"})
    @PostMapping("/modify")
    public ResultBean modify(@RequestBody @Validated SysDictCmd cmd) {
        SysDictContext context = convert.toDict(cmd);
        dictExecutor.update(context);
        return buildSucResp();
    }

    /**
     * 删除字典数据
     *
     * @param id
     * @return
     */
    @OptLog(module="dict",title="删除字典",optType = LogOptTypeEnum.delete)
    @Operation(summary = "删除字典",tags = {"dict"})
    @PostMapping("/delete")
    public ResultBean delete(@RequestBody @Validated StrIdReq id) {
        dictExecutor.delete(id.getId());
        return buildSucResp();
    }

    /**
     * 根据类型查询字典数据
     *
     * @param query
     * @return
     */
    @RequiredLogin
    @Operation(summary = "根据类型查询字典数据",tags = {"dict"})
    @PostMapping("/findByType")
    public ResultBean<List<DictEntity>> findByType(@RequestBody @Validated SysDictQuery query) {
        SysDictQueryContext context = convert.toDictQuery(query);
        List<SysDictDto> list = dictExecutor.findByType(context);
        return buildSucResp( convert.toList(list));
    }

}
