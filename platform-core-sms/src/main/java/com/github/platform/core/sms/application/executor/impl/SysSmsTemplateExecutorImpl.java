package com.github.platform.core.sms.application.executor.impl;

import com.github.platform.core.cache.infra.utils.SequenceUtil;
import com.github.platform.core.common.service.BaseExecutor;
import com.github.platform.core.sms.application.executor.ISysSmsTemplateExecutor;
import com.github.platform.core.sms.domain.constant.SmsSequenceEnum;
import com.github.platform.core.sms.domain.context.SysSmsTemplateContext;
import com.github.platform.core.sms.domain.context.SysSmsTemplateQueryContext;
import com.github.platform.core.sms.domain.dto.SysSmsTemplateDto;
import com.github.platform.core.sms.domain.gateway.ISysSmsTemplateGateway;
import com.github.platform.core.standard.constant.ResultStatusEnum;
import com.github.platform.core.standard.entity.dto.PageBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
* 短信模板执行器
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-14 17:39:30.124
* @version 1.0
*/
@Service
@Slf4j
public class SysSmsTemplateExecutorImpl extends BaseExecutor implements ISysSmsTemplateExecutor {
    @Resource
    private ISysSmsTemplateGateway gateway;
    @Override
    public PageBean<SysSmsTemplateDto> query(SysSmsTemplateQueryContext context){
        return gateway.query(context);
    };
    @Override
    public void insert(SysSmsTemplateContext context){
        String tempNo = SequenceUtil.nextSequenceNum(SmsSequenceEnum.MSS_SMS_TEMPLATE);
        context.setTempNo(tempNo);
        SysSmsTemplateDto record = gateway.insert(context);
        if (Objects.isNull(record.getId())){
            throw exception(ResultStatusEnum.COMMON_INSERT_ERROR);
        }
    }
    @Override
    public SysSmsTemplateDto findById(Long id) {
        return gateway.findById(id);
    }
    @Override
    public void update(SysSmsTemplateContext context) {
        context.setTempNo(null);
        Pair<Boolean, SysSmsTemplateDto> update = gateway.update(context);
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
}
