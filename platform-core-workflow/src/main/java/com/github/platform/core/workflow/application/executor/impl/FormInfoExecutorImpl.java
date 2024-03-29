package com.github.platform.core.workflow.application.executor.impl;

import com.github.platform.core.common.service.BaseExecutor;
import com.github.platform.core.workflow.application.executor.IFormInfoExecutor;
import com.github.platform.core.workflow.domain.context.FormInfoContext;
import com.github.platform.core.workflow.domain.context.FormInfoQueryContext;
import com.github.platform.core.workflow.domain.dto.FormInfoDto;
import com.github.platform.core.workflow.domain.gateway.IFormInfoGateway;
import com.github.platform.core.standard.constant.ResultStatusEnum;
import com.github.platform.core.standard.entity.dto.PageBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
/**
* 表单信息执行器
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-11-17 18:23:21.269
* @version 1.0
*/
@Service
@Slf4j
public class FormInfoExecutorImpl extends BaseExecutor implements IFormInfoExecutor {
    @Resource
    private IFormInfoGateway gateway;
    @Override
    public PageBean<FormInfoDto> query(FormInfoQueryContext context){
        return gateway.query(context);
    };
    @Override
    public void insert(FormInfoContext context){
        FormInfoDto record = gateway.insert(context);
        if (Objects.isNull(record.getId())){
            exception(ResultStatusEnum.COMMON_INSERT_ERROR);
        }
    }
    @Override
    public FormInfoDto findById(Long id) {
        return gateway.findById(id);
    }
    @Override
    public void update(FormInfoContext context) {
        Pair<Boolean, FormInfoDto> update = gateway.update(context);
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

    @Override
    public List<FormInfoDto> findByFromNo(String formNo) {
        return gateway.findByFromNo(formNo);
    }
}
