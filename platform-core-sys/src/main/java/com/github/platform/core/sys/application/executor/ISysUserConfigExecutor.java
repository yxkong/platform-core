package com.github.platform.core.sys.application.executor;

import com.github.platform.core.sys.domain.context.SysUserConfigContext;
import com.github.platform.core.sys.domain.dto.SysUserConfigDto;

import java.util.List;

/**
 * 用户配置执行器接口
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author yxkong
 * @date 2024-03-27 16:10:38.663
 * @version 1.0
 */
public interface ISysUserConfigExecutor{
    /**
    * 更新用户配置化
    * @param context 用户配置
    * @return 单条记录
    */
    void update(SysUserConfigContext context);

    /**
     * 根据登录名称获取用户配置信息
     * @param loginName
     * @return
     */
    List<SysUserConfigDto> findByLoginName(String loginName);
}
