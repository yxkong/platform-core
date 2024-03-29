package com.github.platform.core.sms.application.executor.impl;

import com.github.platform.core.common.service.BaseExecutor;
import com.github.platform.core.sms.application.executor.ISysSmsWhiteListExecutor;
import com.github.platform.core.sms.domain.context.SysSmsWhiteListContext;
import com.github.platform.core.sms.domain.context.SysSmsWhiteListQueryContext;
import com.github.platform.core.sms.domain.dto.SysSmsWhiteListDto;
import com.github.platform.core.sms.domain.gateway.ISysSmsWhiteListGateway;
import com.github.platform.core.standard.constant.ResultStatusEnum;
import com.github.platform.core.standard.entity.dto.PageBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
* 短信白名单执行器
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-14 17:39:30.643
* @version 1.0
*/
@Service
@Slf4j
public class SysSmsWhiteListExecutorImpl extends BaseExecutor implements ISysSmsWhiteListExecutor {
    @Resource
    private ISysSmsWhiteListGateway gateway;
    @Override
    public PageBean<SysSmsWhiteListDto> query(SysSmsWhiteListQueryContext context){
        return gateway.query(context);
    };
    @Override
    public void insert(SysSmsWhiteListContext context){
        SysSmsWhiteListDto record = gateway.insert(context);
        if (Objects.isNull(record.getId())){
            exception(ResultStatusEnum.COMMON_INSERT_ERROR);
        }
    }
    @Override
    public SysSmsWhiteListDto findById(Long id) {
        return gateway.findById(id);
    }
    @Override
    public void update(SysSmsWhiteListContext context) {
        Pair<Boolean, SysSmsWhiteListDto> update = gateway.update(context);
        if (!update.getKey()){
            exception(ResultStatusEnum.COMMON_UPDATE_ERROR);
        }
    }
    @Override
    public void delete(Long id) {
        int delete = gateway.delete(id);
        if (delete <=0 ){
            exception(ResultStatusEnum.COMMON_DELETE_ERROR);
        }
    }
}
