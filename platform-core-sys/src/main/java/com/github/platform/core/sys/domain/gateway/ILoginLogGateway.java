package com.github.platform.core.sys.domain.gateway;

import com.github.platform.core.standard.entity.dto.PageBean;
import com.github.platform.core.sys.domain.context.SysLoginLogQueryContext;
import com.github.platform.core.sys.domain.dto.LoginLogDto;
import com.github.platform.core.sys.domain.dto.SysLoginLogDto;

/**
* 登录日志网关层，隔离模型和实现
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @time 2023-06-07 14:37:49.624
* @version 1.0
*/
public interface ILoginLogGateway {
    /**
    * 查询登录日志列表
    * @param context
    * @return
    */
    PageBean<SysLoginLogDto> query(SysLoginLogQueryContext context);
    /**
    * 根据id查询登录日志明细
    * @param id
    * @return
    */
    SysLoginLogDto findById(Long id);
}
