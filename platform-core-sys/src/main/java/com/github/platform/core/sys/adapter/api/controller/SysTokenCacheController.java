package com.github.platform.core.sys.adapter.api.controller;

import com.github.platform.core.auth.service.ITokenService;
import com.github.platform.core.cache.domain.constant.CacheConstant;
import com.github.platform.core.common.entity.StrIdReq;
import com.github.platform.core.common.utils.StringUtils;
import com.github.platform.core.log.domain.constants.LogOptTypeEnum;
import com.github.platform.core.log.infra.annotation.OptLog;
import com.github.platform.core.standard.entity.dto.PageBean;
import com.github.platform.core.standard.entity.dto.ResultBean;
import com.github.platform.core.standard.util.LocalDateTimeUtil;
import com.github.platform.core.sys.adapter.api.command.SysTokenCacheQuery;
import com.github.platform.core.sys.adapter.api.constant.SysAdapterResultEnum;
import com.github.platform.core.sys.adapter.api.convert.SysTokenCacheAdapterConvert;
import com.github.platform.core.sys.application.executor.ISysTokenCacheExecutor;
import com.github.platform.core.sys.domain.context.SysTokenCacheQueryContext;
import com.github.platform.core.sys.domain.dto.SysTokenCacheDto;
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
    @Resource(name = CacheConstant.sysTokenService)
    private ITokenService tokenService;
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
        context.setSearchStartTime(LocalDateTimeUtil.dateTimeDefaultShort());
        PageBean<SysTokenCacheDto> pageBean = sysTokenCacheExecutor.query(context);
        return buildSucResp(pageBean);
    }

    /**
     * 过期token
     * @param query
     * @return 操作结果
     */
    @OptLog(module="sysTokenCache",title="根据token过期登录信息",optType = LogOptTypeEnum.delete)
    @Operation(summary = "根据token过期登录信息",tags = {"sysTokenCache"})
    @PostMapping("/expire")
    public ResultBean expire(@Validated @RequestBody SysTokenCacheQuery query) {
        if (StringUtils.isEmpty(query.getToken())){
            exception(SysAdapterResultEnum.token_is_empty);
        }
        tokenService.expireByToken(query.getToken());
        return buildSucResp();
    }

}