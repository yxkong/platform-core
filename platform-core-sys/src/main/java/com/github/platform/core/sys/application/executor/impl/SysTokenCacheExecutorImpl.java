package com.github.platform.core.sys.application.executor.impl;

import com.github.platform.core.common.service.BaseExecutor;
import com.github.platform.core.sys.application.executor.ISysTokenCacheExecutor;
import com.github.platform.core.sys.domain.context.SysTokenCacheContext;
import com.github.platform.core.sys.domain.context.SysTokenCacheQueryContext;
import com.github.platform.core.sys.domain.dto.SysTokenCacheDto;
import com.github.platform.core.sys.domain.gateway.ISysTokenCacheGateway;
import com.github.platform.core.standard.constant.ResultStatusEnum;
import com.github.platform.core.standard.entity.dto.PageBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Objects;
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
    /**
    * 查询token缓存列表
    * @param context 查询上下文
    * @return 分页结果
    */
    public PageBean<SysTokenCacheDto> query(SysTokenCacheQueryContext context){
        return sysTokenCacheGateway.query(context);
    };
    /**
    * 新增token缓存
    * @param context 新增上下文
    */
    public String insert(SysTokenCacheContext context){
        SysTokenCacheDto record = sysTokenCacheGateway.insert(context);
        if (Objects.isNull(record.getId())){
            exception(ResultStatusEnum.COMMON_INSERT_ERROR);
        }
        return record.getStrId();
    }
    /**
    * 根据id查询token缓存明细
    * @param id 主键
    * @return 单条记录
    */
    public SysTokenCacheDto findById(Long id) {
        return sysTokenCacheGateway.findById(id);
    }
    /**
    * 修改token缓存
    * @param context 更新上下文
    */
    public void update(SysTokenCacheContext context) {
        sysTokenCacheGateway.update(context);
    }
    /**
    * 根据id删除token缓存记录
    * @param id 主键
    */
    public void delete(Long id) {
        /**此处是为了再gateway上做多条件缓存，如果有必要，先查，后设置值*/
        SysTokenCacheContext context = SysTokenCacheContext.builder().id(id).build();
        int d = sysTokenCacheGateway.expire(context);
        if (d <=0 ){
            exception(ResultStatusEnum.COMMON_DELETE_ERROR);
        }
    }
}
