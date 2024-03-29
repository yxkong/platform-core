package com.github.platform.core.dingtalk.application.executor.impl;

import com.github.platform.core.common.service.BaseExecutor;
import com.github.platform.core.dingtalk.application.executor.IDingDeptExecutor;
import com.github.platform.core.dingtalk.domain.dto.DingDeptDto;
import com.github.platform.core.dingtalk.domain.gateway.IDingDeptGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
/**
 * 钉钉部门执行器
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author yxkong
 * @date 2024-01-18 15:49:32.694
 * @version 1.0
 */
@Service
@Slf4j
public class DingDeptExecutorImpl extends BaseExecutor implements IDingDeptExecutor{
    @Resource
    private IDingDeptGateway dingDeptgateway;
    /**
    * 根据id查询钉钉部门明细
    * @param id 主键
    * @return 单条记录
    */
    public DingDeptDto findById(Long id) {
        return dingDeptgateway.findById(id);
    }
}
