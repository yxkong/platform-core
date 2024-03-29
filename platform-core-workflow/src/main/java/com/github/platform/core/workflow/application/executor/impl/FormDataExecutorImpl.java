package com.github.platform.core.workflow.application.executor.impl;

import com.github.platform.core.common.service.BaseExecutor;
import com.github.platform.core.workflow.application.executor.IFormDataExecutor;
import com.github.platform.core.workflow.domain.context.FormDataContext;
import com.github.platform.core.workflow.domain.context.FormDataQueryContext;
import com.github.platform.core.workflow.domain.dto.FormDataDto;
import com.github.platform.core.workflow.domain.gateway.IFormDataGateway;
import com.github.platform.core.standard.constant.ResultStatusEnum;
import com.github.platform.core.standard.entity.dto.PageBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Objects;
/**
* 表单数据执行器
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-11-17 18:23:23.341
* @version 1.0
*/
@Service
@Slf4j
public class FormDataExecutorImpl extends BaseExecutor implements IFormDataExecutor {
    @Resource
    private IFormDataGateway gateway;
    @Override
    public PageBean<FormDataDto> query(FormDataQueryContext context){
        return gateway.query(context);
    };
    @Override
    public void insert(FormDataContext context){
        FormDataDto record = gateway.insert(context);
        if (Objects.isNull(record.getId())){
            exception(ResultStatusEnum.COMMON_INSERT_ERROR);
        }
    }
    @Override
    public FormDataDto findById(Long id) {
        return gateway.findById(id);
    }
    @Override
    public void update(FormDataContext context) {
        Pair<Boolean, FormDataDto> update = gateway.update(context);
        if (!update.getKey()){
            exception(ResultStatusEnum.COMMON_UPDATE_ERROR);
        }
    }
    @Override
    public void delete(Long id) {
        int d = gateway.delete(id);
        if (d <=0 ){
            exception(ResultStatusEnum.COMMON_DELETE_ERROR);
        }
    }
}
