package com.github.platform.core.sys.application.executor;

import com.github.platform.core.sys.domain.dto.SysUserConfigDto;
/**
 * 用户配置执行器接口
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author yxkong
 * @date 2024-03-27 16:10:38.663
 * @version 1.0
 */
public interface ISysUserConfigExecutor{
    /**
    * 根据id查询用户配置明细
    * @param id 主键
    * @return 单条记录
    */
    public SysUserConfigDto findById(Long id);
}
