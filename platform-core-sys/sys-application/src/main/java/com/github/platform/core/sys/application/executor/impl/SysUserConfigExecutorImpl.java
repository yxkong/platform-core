package com.github.platform.core.sys.application.executor.impl;

import com.github.platform.core.common.service.BaseExecutor;
import com.github.platform.core.sys.application.executor.ISysUserConfigExecutor;
import com.github.platform.core.sys.domain.context.SysUserConfigContext;
import com.github.platform.core.sys.domain.dto.SysUserConfigDto;
import com.github.platform.core.sys.domain.gateway.ISysUserConfigGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

/**
 * 用户配置执行器
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author yxkong
 * @date 2024-03-27 16:10:38.663
 * @version 1.0
 */
@Service
@Slf4j
public class SysUserConfigExecutorImpl extends BaseExecutor implements ISysUserConfigExecutor{
    @Resource
    private ISysUserConfigGateway sysUserConfigGateway;

    @Override
    public void update(SysUserConfigContext context) {
        sysUserConfigGateway.update(context);
    }

    @Override
    public List<SysUserConfigDto> findByLoginName(String loginName) {
        return sysUserConfigGateway.getUserAllConfig(loginName);
    }
}
