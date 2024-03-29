package com.github.platform.core.schedule.application.executor.impl;

import com.github.platform.core.common.service.BaseExecutor;
import com.github.platform.core.schedule.application.executor.ISysJobLogExecutor;
import com.github.platform.core.schedule.domain.context.SysJobLogQueryContext;
import com.github.platform.core.schedule.domain.dto.SysJobLogDto;
import com.github.platform.core.schedule.domain.gateway.ISysJobLogGateway;
import com.github.platform.core.standard.entity.dto.PageBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

/**
* 任务执行日志执行器
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-09-06 18:53:10.711
* @version 1.0
*/
@Service
@Slf4j
public class SysJobLogExecutorImpl extends BaseExecutor implements ISysJobLogExecutor {
    @Resource
    private ISysJobLogGateway gateway;
    @Override
    public PageBean<SysJobLogDto> query(SysJobLogQueryContext context){
        return gateway.query(context);
    };
    @Override
    public SysJobLogDto findById(Long id) {
        return gateway.findById(id);
    }
}
