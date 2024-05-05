package com.github.platform.core.dingtalk.application.executor;

import com.github.platform.core.dingtalk.domain.dto.DingDeptDto;
/**
 * 钉钉部门执行器接口
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author yxkong
 * @date 2024-01-18 15:49:32.694
 * @version 1.0
 */
public interface IDingDeptExecutor{
    /**
    * 根据id查询钉钉部门明细
    * @param id 主键
    * @return 单条记录
    */
    DingDeptDto findById(Long id);
}
