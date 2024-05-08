package com.github.platform.core.sys.application.executor.impl;

import com.github.platform.core.auth.gateway.ITokenCacheGateway;
import com.github.platform.core.common.service.BaseExecutor;
import com.github.platform.core.standard.entity.dto.PageBean;
import com.github.platform.core.sys.application.executor.ISysTokenCacheExecutor;
import com.github.platform.core.sys.domain.context.SysTokenCacheQueryContext;
import com.github.platform.core.sys.domain.dto.SysTokenCacheDto;
import com.github.platform.core.sys.domain.gateway.ISysTokenCacheGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * token缓存执行器
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author yxkong
 * @date 2024-04-28 16:25:03.690
 * @version 1.0
 */
@Service
@Slf4j
public class SysTokenCacheExecutorImpl extends BaseExecutor implements ISysTokenCacheExecutor{
    @Resource
    private ISysTokenCacheGateway sysTokenCacheGateway;
    @Resource
    private ITokenCacheGateway tokenCacheGateway;
    /**
    * 查询token缓存列表
    * @param context 查询上下文
    * @return 分页结果
    */
    public PageBean<SysTokenCacheDto> query(SysTokenCacheQueryContext context){
        return sysTokenCacheGateway.query(context);
    };

    @Override
    public SysTokenCacheDto findById(Long id) {
        return sysTokenCacheGateway.findById(id);
    }

    /**
    * 根据id删除token缓存记录
    * @param id 主键
    */
    public void expire(Long id) {
        tokenCacheGateway.expireById(id);
    }
}
