package com.github.platform.core.sms.adapter.api.controller;

import com.github.platform.core.log.domain.constants.LogOptTypeEnum;
import com.github.platform.core.log.infra.annotation.OptLog;
import com.github.platform.core.sms.adapter.api.command.SysSmsWhiteListCmd;
import com.github.platform.core.sms.adapter.api.command.SysSmsWhiteListQuery;
import com.github.platform.core.sms.adapter.api.convert.SysSmsWhiteListAdapterConvert;
import com.github.platform.core.sms.application.executor.ISysSmsWhiteListExecutor;
import com.github.platform.core.sms.domain.context.SysSmsWhiteListContext;
import com.github.platform.core.sms.domain.context.SysSmsWhiteListQueryContext;
import com.github.platform.core.sms.domain.dto.SysSmsWhiteListDto;
import com.github.platform.core.standard.entity.IdReq;
import com.github.platform.core.standard.entity.dto.PageBean;
import com.github.platform.core.standard.entity.dto.ResultBean;
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
/**
* 短信白名单
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-14 17:39:30.643
* @version 1.0
*/
@RestController
@Tag(name = "sysSmsWhiteList",description = "短信白名单管理")
@RequestMapping("sys/sms/whitelist")
@Slf4j
public class SysSmsWhiteListController extends BaseController{
    @Resource
    private ISysSmsWhiteListExecutor executor;
    @Resource
    private SysSmsWhiteListAdapterConvert convert;

    /**
    * 查询短信白名单列表
    * @param query 查询实体
    * @return 分页结果
    */
    @Operation(summary = "查询短信白名单列表",tags = {"sysSmsWhiteList"})
    @PostMapping("/query")
    public ResultBean<PageBean<SysSmsWhiteListDto>> query(@RequestBody SysSmsWhiteListQuery query){
        SysSmsWhiteListQueryContext context = convert.toQuery(query);
        PageBean<SysSmsWhiteListDto> pageBean = executor.query(context);
        return buildSucResp(pageBean);
    }

    /**
    * 新增短信白名单
    * @param cmd 新增实体
    * @return 操作结果
    */
    @OptLog(module="sysSmsWhiteList",title="新增短信白名单",optType = LogOptTypeEnum.add)
    @Operation(summary = "新增短信白名单",tags = {"sysSmsWhiteList"})
    @PostMapping("/add")
    public ResultBean add(@Validated @RequestBody SysSmsWhiteListCmd cmd) {
        SysSmsWhiteListContext context = convert.toContext(cmd);
        executor.insert(context);
        return buildSucResp();
    }

    /**
    * 根据id查询短信白名单明细
    * @param id 主键id
    * @return 单条记录
    */
    @Operation(summary = "根据id查询短信白名单明细",tags = {"sysSmsWhiteList"})
    @PostMapping("/detail")
    public ResultBean<SysSmsWhiteListDto> detail(@Validated @RequestBody IdReq id) {
        SysSmsWhiteListDto dto = executor.findById(id.getId());
        return buildSucResp(dto);
    }

    /**
     * 根据id删除短信白名单记录
     * @param id 主键id
     * @return 操作结果
     */
    @OptLog(module="sysSmsWhiteList",title="根据id删除短信白名单记录",optType = LogOptTypeEnum.delete)
    @Operation(summary = "根据id删除短信白名单记录",tags = {"sysSmsWhiteList"})
    @PostMapping("/delete")
    public ResultBean<?> delete(@Validated @RequestBody IdReq id) {
        executor.delete(id.getId());
        return buildSucResp();
    }

    /**
     * 修改短信白名单
     * @param cmd 修改实体
     * @return 操作结果
     */
    @OptLog(module="sysSmsWhiteList",title="修改短信白名单",optType = LogOptTypeEnum.modify)
    @Operation(summary = "修改短信白名单",tags = {"sysSmsWhiteList"})
    @PostMapping("/modify")
    public ResultBean<?> modify(@Validated @RequestBody SysSmsWhiteListCmd cmd) {
        SysSmsWhiteListContext context = convert.toContext(cmd);
        executor.update(context);
        return buildSucResp();
    }
}