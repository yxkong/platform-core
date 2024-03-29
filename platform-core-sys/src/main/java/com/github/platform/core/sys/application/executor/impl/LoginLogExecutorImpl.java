package com.github.platform.core.sys.application.executor.impl;

import com.github.platform.core.common.service.BaseExecutor;
import com.github.platform.core.sys.application.executor.ILoginLogExecutor;
import com.github.platform.core.sys.domain.context.SysLoginLogQueryContext;
import com.github.platform.core.sys.domain.dto.SysLoginLogDto;
import com.github.platform.core.sys.domain.gateway.ILoginLogGateway;
import com.github.platform.core.standard.entity.dto.PageBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

/**
* 登录日志执行器
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @time 2023-06-07 14:37:49.624
* @version 1.0
*/
@Service
@Slf4j
public class LoginLogExecutorImpl extends BaseExecutor implements ILoginLogExecutor {
    @Resource
    private ILoginLogGateway gateway;
    /**
    * 查询登录日志列表
    * @param context
    * @return
    */

    @Override
    public PageBean<SysLoginLogDto> query(SysLoginLogQueryContext context){
        return gateway.query(context);
    };

    /**
    * 根据id查询登录日志明细
    * @param id
    * @return
    */
    @Override
    public SysLoginLogDto findById(Long id) {
        return gateway.findById(id);
    }
}
