package com.github.platform.core.monitor.application.executor;

import com.github.platform.core.common.service.BaseExecutor;
import com.github.platform.core.monitor.domain.context.SysOptLogContext;
import com.github.platform.core.monitor.domain.context.SysOptLogQueryContext;
import com.github.platform.core.monitor.domain.dto.SysOptLogDto;
import com.github.platform.core.monitor.domain.gateway.ISysOptLogGateway;
import com.github.platform.core.standard.entity.dto.PageBean;
import com.github.platform.core.standard.entity.dto.ResultBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* 操作日志执行器
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-15 10:55:05.960
* @version 1.0
*/
@Service
@Slf4j
public class SysOptLogExecutor extends BaseExecutor {
    @Resource
    private ISysOptLogGateway gateway;
    /**
    * 查询操作日志列表
    * @param context 查询上下文
    * @return 分页结果
    */
    public PageBean<SysOptLogDto> query(SysOptLogQueryContext context){
        return gateway.query(context);
    };
    /**
    * 根据id查询操作日志明细
    * @param id 主键
    * @return 单条记录
    */
    public SysOptLogDto findById(Long id) {
        return gateway.findById(id);
    }

}
