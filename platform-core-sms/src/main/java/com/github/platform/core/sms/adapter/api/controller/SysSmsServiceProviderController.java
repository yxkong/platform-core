package com.github.platform.core.sms.adapter.api.controller;

import com.github.platform.core.auth.annotation.RequiredLogin;
import com.github.platform.core.common.utils.CollectionUtil;
import com.github.platform.core.common.utils.EncryptUtil;
import com.github.platform.core.log.domain.constants.LogOptTypeEnum;
import com.github.platform.core.log.infra.annotation.OptLog;
import com.github.platform.core.sms.adapter.api.command.SysSmsServiceProviderCmd;
import com.github.platform.core.sms.adapter.api.command.SysSmsServiceProviderQuery;
import com.github.platform.core.sms.adapter.api.convert.SysSmsServiceProviderAdapterConvert;
import com.github.platform.core.sms.application.executor.ISysSmsServiceProviderExecutor;
import com.github.platform.core.sms.domain.context.SysSmsServiceProviderContext;
import com.github.platform.core.sms.domain.context.SysSmsServiceProviderQueryContext;
import com.github.platform.core.sms.domain.dto.SysSmsServiceProviderDto;
import com.github.platform.core.standard.entity.IdReq;
import com.github.platform.core.standard.entity.dto.PageBean;
import com.github.platform.core.standard.entity.dto.ResultBean;
import com.github.platform.core.standard.entity.vue.OptionsDto;
import com.github.platform.core.web.web.BaseController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
* 服务商
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-14 17:39:29.748
* @version 1.0
*/
@RestController
@Tag(name = "sysSmsServiceProvider",description = "服务商管理")
@RequestMapping("/sys/sms/sp")
@Slf4j
public class SysSmsServiceProviderController extends BaseController{
    @Resource
    private ISysSmsServiceProviderExecutor executor;
    @Resource
    private SysSmsServiceProviderAdapterConvert convert;

    /**
    * 查询服务商列表
    * @param query 查询实体
    * @return 分页结果
    */
    @Operation(summary = "查询服务商列表",tags = {"sysSmsServiceProvider"})
    @PostMapping("/query")
    public ResultBean<PageBean<SysSmsServiceProviderDto>> query(@RequestBody SysSmsServiceProviderQuery query){
        SysSmsServiceProviderQueryContext context = convert.toQuery(query);
        PageBean<SysSmsServiceProviderDto> pageBean = executor.query(context);
        return buildSucResp(pageBean);
    }

    /**
    * 新增服务商
    * @param cmd 新增实体
    * @return 操作结果
    */
    @OptLog(module="sysSmsServiceProvider",title="新增服务商",optType = LogOptTypeEnum.add)
    @Operation(summary = "新增服务商",tags = {"sysSmsServiceProvider"})
    @PostMapping("/add")
    public ResultBean add(@Validated @RequestBody SysSmsServiceProviderCmd cmd) {
        SysSmsServiceProviderContext context = convert.toContext(cmd);
        // 密码加密
        String pwd = cmd.getPwd();
        Pair<String, String> pair = EncryptUtil.getInstance().md5(pwd);
        context.setSalt(pair.getKey());
        context.setEncryptPwd(pair.getValue());
        executor.insert(context);
        return buildSucResp();
    }

    /**
    * 根据id查询服务商明细
    * @param id 主键id
    * @return 单条记录
    */
    @Operation(summary = "根据id查询服务商明细",tags = {"sysSmsServiceProvider"})
    @PostMapping("/detail")
    public ResultBean<SysSmsServiceProviderDto> detail(@Validated @RequestBody IdReq id) {
        SysSmsServiceProviderDto dto = executor.findById(id.getId());
        return buildSucResp(dto);
    }
    @RequiredLogin
    @Operation(summary = "查询所有启用的厂商",tags = {"sysSmsServiceProvider"})
    @PostMapping("/allOptions")
    public ResultBean<List<OptionsDto>> allOptions() {
        List<SysSmsServiceProviderDto> list = executor.findAll();
        List<OptionsDto> rst = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(list)){
            list.forEach(s-> {
                rst.add(new OptionsDto(s.getProNo(),s.getProName()));
            });
        }
        return buildSucResp(rst);
    }

    /**
     * 修改服务商
     * @param cmd 修改实体
     * @return 操作结果
     */
    @OptLog(module="sysSmsServiceProvider",title="修改服务商",optType = LogOptTypeEnum.modify)
    @Operation(summary = "修改服务商",tags = {"sysSmsServiceProvider"})
    @PostMapping("/modify")
    public ResultBean modify(@Validated @RequestBody SysSmsServiceProviderCmd cmd) {
        SysSmsServiceProviderContext context = convert.toContext(cmd);
        executor.update(context);
        return buildSucResp();
    }
}