package com.github.platform.core.message.adapter.api.controller;

import com.github.platform.core.log.infra.annotation.OptLog;
import com.github.platform.core.common.entity.StrIdReq;
import com.github.platform.core.message.adapter.api.command.SysNoticeChannelConfigCmd;
import com.github.platform.core.message.adapter.api.command.SysNoticeChannelConfigQuery;
import com.github.platform.core.message.adapter.api.convert.SysNoticeChannelConfigAdapterConvert;
import com.github.platform.core.message.application.executor.ISysNoticeChannelConfigExecutor;
import com.github.platform.core.message.domain.context.SysNoticeChannelConfigContext;
import com.github.platform.core.message.domain.context.SysNoticeChannelConfigQueryContext;
import com.github.platform.core.message.domain.dto.SysNoticeChannelConfigDto;
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
 * 通知通道配置
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author yxkong
 * @date 2024-12-04 13:32:28.892
 * @version 1.0
 */
@RestController
@Tag(name = "sysNoticeChannelConfig",description = "通知通道配置管理")
@RequestMapping("api/message/notice/channel")
@Slf4j
public class SysNoticeChannelConfigController extends BaseController{
    @Resource
    private ISysNoticeChannelConfigExecutor sysNoticeChannelConfigExecutor;
    @Resource
    private SysNoticeChannelConfigAdapterConvert sysNoticeChannelConfigConvert;

    /**
    * 查询通知通道配置列表
    * @param query 查询实体
    * @return 分页结果
    */
    @OptLog(module="sysNoticeChannelConfig",title="查询通知通道配置列表",persistent = false)
    @Operation(summary = "查询通知通道配置列表",tags = {"sysNoticeChannelConfig"})
    @PostMapping("/query")
    public ResultBean<PageBean<SysNoticeChannelConfigDto>> query(@RequestBody SysNoticeChannelConfigQuery query){
        SysNoticeChannelConfigQueryContext context = sysNoticeChannelConfigConvert.toQuery(query);
        PageBean<SysNoticeChannelConfigDto> pageBean = sysNoticeChannelConfigExecutor.query(context);
        return buildSucResp(pageBean);
    }

    /**
    * 新增通知通道配置
    * @param cmd 新增实体
    * @return 操作结果
    */
    @OptLog(module="sysNoticeChannelConfig",title="新增通知通道配置",optType = LogOptTypeEnum.add)
    @Operation(summary = "新增通知通道配置",tags = {"sysNoticeChannelConfig"})
    @PostMapping("/add")
    public ResultBean<String> add(@Validated @RequestBody SysNoticeChannelConfigCmd cmd) {
        SysNoticeChannelConfigContext context= sysNoticeChannelConfigConvert.toContext(cmd);
        String id = sysNoticeChannelConfigExecutor.insert(context);
        return buildSucResp(id);
    }

    /**
    * 根据id查询通知通道配置明细
    * @param id 主键id
    * @return 单条记录
    */
    @OptLog(module="sysNoticeChannelConfig",title="根据id查询通知通道配置明细",optType = LogOptTypeEnum.detail,persistent = false)
    @Operation(summary = "根据id查询通知通道配置明细",tags = {"sysNoticeChannelConfig"})
    @PostMapping("/detail")
    public ResultBean<SysNoticeChannelConfigDto> detail(@Validated @RequestBody StrIdReq id) {
        SysNoticeChannelConfigDto dto = sysNoticeChannelConfigExecutor.findById(id.getId());
        return buildSucResp(dto);
    }

    /**
     * 根据id删除通知通道配置记录
     * @param id 主键id
     * @return 操作结果
     */
    @OptLog(module="sysNoticeChannelConfig",title="根据id删除通知通道配置记录",optType = LogOptTypeEnum.delete)
    @Operation(summary = "根据id删除通知通道配置记录",tags = {"sysNoticeChannelConfig"})
    @PostMapping("/delete")
    public ResultBean delete(@Validated @RequestBody StrIdReq id) {
        sysNoticeChannelConfigExecutor.delete(id.getId());
        return buildSucResp();
    }

    /**
     * 修改通知通道配置
     * @param cmd 修改实体
     * @return 操作结果
     */
    @OptLog(module="sysNoticeChannelConfig",title="修改通知通道配置",optType = LogOptTypeEnum.modify)
    @Operation(summary = "修改通知通道配置",tags = {"sysNoticeChannelConfig"})
    @PostMapping("/modify")
    public ResultBean modify(@Validated @RequestBody SysNoticeChannelConfigCmd cmd) {
        sysNoticeChannelConfigExecutor.update(sysNoticeChannelConfigConvert.toContext(cmd));
        return buildSucResp();
    }
}