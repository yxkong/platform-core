package com.github.platform.core.sms.application.executor.impl;

import com.github.platform.core.cache.infra.utils.SequenceUtil;
import com.github.platform.core.common.service.BaseExecutor;
import com.github.platform.core.sms.application.executor.ISysSmsServiceProviderExecutor;
import com.github.platform.core.sms.domain.constant.SmsSequenceEnum;
import com.github.platform.core.sms.domain.context.SysSmsServiceProviderContext;
import com.github.platform.core.sms.domain.context.SysSmsServiceProviderQueryContext;
import com.github.platform.core.sms.domain.dto.SysSmsServiceProviderDto;
import com.github.platform.core.sms.domain.gateway.ISysSmsServiceProviderGateway;
import com.github.platform.core.standard.constant.ResultStatusEnum;
import com.github.platform.core.standard.entity.dto.PageBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
* 服务商执行器
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-14 17:39:29.748
* @version 1.0
*/
@Service
@Slf4j
public class SysSmsServiceProviderExecutorImpl extends BaseExecutor implements ISysSmsServiceProviderExecutor {
    @Resource
    private ISysSmsServiceProviderGateway gateway;
    @Override
    public PageBean<SysSmsServiceProviderDto> query(SysSmsServiceProviderQueryContext context){
        return gateway.query(context);
    };
    @Override
    public void insert(SysSmsServiceProviderContext context){
        String proNo = SequenceUtil.nextSequenceNum(SmsSequenceEnum.MSS_SMS_SP);
        context.setProNo(proNo);


        SysSmsServiceProviderDto record = gateway.insert(context);
        if (Objects.isNull(record.getId())){
            throw exception(ResultStatusEnum.COMMON_INSERT_ERROR);
        }
    }
    @Override
    public SysSmsServiceProviderDto findById(Long id) {
        return gateway.findById(id);
    }
    @Override
    public void update(SysSmsServiceProviderContext context) {
        context.setProNo(null);
        Pair<Boolean, SysSmsServiceProviderDto> update = gateway.update(context);
        if (!update.getKey()){
            throw exception(ResultStatusEnum.COMMON_UPDATE_ERROR);
        }
    }
    @Override
    public void delete(Long id) {
        int delete = gateway.delete(id);
        if (delete <=0 ){
            throw exception(ResultStatusEnum.COMMON_DELETE_ERROR);
        }
    }

    @Override
    public List<SysSmsServiceProviderDto> findAll() {
        return gateway.findAll();
    }
}
