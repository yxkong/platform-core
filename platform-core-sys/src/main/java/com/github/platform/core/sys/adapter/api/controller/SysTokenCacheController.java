package com.github.platform.core.sys.adapter.api.controller;

import com.github.platform.core.log.infra.annotation.OptLog;
import com.github.platform.core.common.entity.StrIdReq;
import com.github.platform.core.sys.adapter.api.command.SysTokenCacheCmd;
import com.github.platform.core.sys.adapter.api.command.SysTokenCacheQuery;
import com.github.platform.core.sys.adapter.api.convert.SysTokenCacheAdapterConvert;
import com.github.platform.core.sys.application.executor.ISysTokenCacheExecutor;
import com.github.platform.core.sys.domain.context.SysTokenCacheQueryContext;
import com.github.platform.core.sys.domain.dto.SysTokenCacheDto;
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
/**
 * token缓存
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author yxkong
 * @date 2024-04-28 16:25:03.690
 * @version 1.0
 */
@RestController
@Tag(name = "sysTokenCache",description = "token缓存管理")
@RequestMapping("api/sys/token")
@Slf4j
public class SysTokenCacheController extends BaseController{
    @Resource
    private ISysTokenCacheExecutor sysTokenCacheExecutor;
    @Resource
    private SysTokenCacheAdapterConvert sysTokenCacheConvert;

    /**
    * 查询token缓存列表
    * @param query 查询实体
    * @return 分页结果
    */
    @OptLog(module="sysTokenCache",title="查询token缓存列表",persistent = false)
    @Operation(summary = "查询token缓存列表",tags = {"sysTokenCache"})
    @PostMapping("/query")
    public ResultBean<PageBean<SysTokenCacheDto>> query(@RequestBody SysTokenCacheQuery query){
        SysTokenCacheQueryContext context = sysTokenCacheConvert.toQuery(query);
        PageBean<SysTokenCacheDto> pageBean = sysTokenCacheExecutor.query(context);
        return buildSucResp(pageBean);
    }

    /**
    * 新增token缓存
    * @param cmd 新增实体
    * @return 操作结果
    */
    @OptLog(module="sysTokenCache",title="新增token缓存",optType = LogOptTypeEnum.add)
    @Operation(summary = "新增token缓存",tags = {"sysTokenCache"})
    @PostMapping("/add")
    public ResultBean<String> add(@Validated @RequestBody SysTokenCacheCmd cmd) {
        String id = sysTokenCacheExecutor.insert(sysTokenCacheConvert.toContext(cmd));
        return buildSucResp(id);
    }

    /**
    * 根据id查询token缓存明细
    * @param id 主键id
    * @return 单条记录
    */
    @OptLog(module="sysTokenCache",title="根据id查询token缓存明细",optType = LogOptTypeEnum.detail,persistent = false)
    @Operation(summary = "根据id查询token缓存明细",tags = {"sysTokenCache"})
    @PostMapping("/detail")
    public ResultBean<SysTokenCacheDto> detail(@Validated @RequestBody StrIdReq id) {
        SysTokenCacheDto dto = sysTokenCacheExecutor.findById(id.getId());
        return buildSucResp(dto);
    }

    /**
     * 根据id删除token缓存记录
     * @param id 主键id
     * @return 操作结果
     */
    @OptLog(module="sysTokenCache",title="根据id删除token缓存记录",optType = LogOptTypeEnum.delete)
    @Operation(summary = "根据id删除token缓存记录",tags = {"sysTokenCache"})
    @PostMapping("/delete")
    public ResultBean delete(@Validated @RequestBody StrIdReq id) {
        sysTokenCacheExecutor.delete(id.getId());
        return buildSucResp();
    }

    /**
     * 修改token缓存
     * @param cmd 修改实体
     * @return 操作结果
     */
    @OptLog(module="sysTokenCache",title="修改token缓存",optType = LogOptTypeEnum.modify)
    @Operation(summary = "修改token缓存",tags = {"sysTokenCache"})
    @PostMapping("/modify")
    public ResultBean modify(@Validated @RequestBody SysTokenCacheCmd cmd) {
        sysTokenCacheExecutor.update(sysTokenCacheConvert.toContext(cmd));
        return buildSucResp();
    }
}