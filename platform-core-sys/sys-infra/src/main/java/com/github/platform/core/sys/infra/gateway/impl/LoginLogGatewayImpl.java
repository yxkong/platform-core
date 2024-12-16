package com.github.platform.core.sys.infra.gateway.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.platform.core.persistence.mapper.sys.LoginLogMapper;
import com.github.platform.core.standard.entity.dto.PageBean;
import com.github.platform.core.sys.domain.common.entity.SysLoginLogBase;
import com.github.platform.core.sys.domain.context.SysLoginLogQueryContext;
import com.github.platform.core.sys.domain.dto.SysLoginLogDto;
import com.github.platform.core.sys.domain.gateway.ILoginLogGateway;
import com.github.platform.core.sys.infra.convert.SysLoginLogInfraConvert;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* 登录日志网关层实现
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @time 2023-06-07 14:37:49.624
* @version 1.0
*/
@Service
public class LoginLogGatewayImpl implements ILoginLogGateway {

    @Resource
    private LoginLogMapper loginLogMapper;
    @Resource
    private SysLoginLogInfraConvert convert;
    @Override
    public PageBean<SysLoginLogDto> query(SysLoginLogQueryContext context) {
        SysLoginLogBase sysLoginLog = convert.toSysLoginLogBase(context);
        PageHelper.startPage(context.getPageNum(),context.getPageSize());
        List<SysLoginLogBase> list = loginLogMapper.findListBy(sysLoginLog);
        return convert.ofPageBean(new PageInfo<>(list));
    }



    @Override
    public SysLoginLogDto findById(Long id) {
        SysLoginLogBase sysLoginLog = loginLogMapper.findById(id);
        return convert.toDto(sysLoginLog);
    }


}
