package com.github.platform.core.workflow.application.executor.impl;

import com.github.platform.core.cache.infra.constant.SequenceEnum;
import com.github.platform.core.cache.infra.utils.SequenceUtil;
import com.github.platform.core.common.service.BaseExecutor;
import com.github.platform.core.common.utils.CollectionUtil;
import com.github.platform.core.standard.constant.ResultStatusEnum;
import com.github.platform.core.standard.constant.StatusEnum;
import com.github.platform.core.standard.entity.dto.PageBean;
import com.github.platform.core.standard.entity.vue.OptionsDto;
import com.github.platform.core.workflow.application.executor.IFormExecutor;
import com.github.platform.core.workflow.domain.context.FormContext;
import com.github.platform.core.workflow.domain.context.FormInfoContext;
import com.github.platform.core.workflow.domain.context.FormInfoWrapContext;
import com.github.platform.core.workflow.domain.context.FormQueryContext;
import com.github.platform.core.workflow.domain.dto.FormDetailDto;
import com.github.platform.core.workflow.domain.dto.FormDto;
import com.github.platform.core.workflow.domain.gateway.ICustomFormDataGateway;
import com.github.platform.core.workflow.domain.gateway.IFormGateway;
import com.github.platform.core.workflow.domain.gateway.IFormInfoGateway;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
* 表单配置执行器
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-11-17 18:23:18.130
* @version 1.0
*/
@Service
@Slf4j
public class FormExecutorImpl extends BaseExecutor implements IFormExecutor {
    @Resource
    private IFormGateway gateway;
    @Resource
    private IFormInfoGateway formInfoGateway;

    @Override
    public PageBean<FormDto> query(FormQueryContext context){
        return gateway.query(context);
    };
    @Override
    public void insert(FormInfoWrapContext context){
        FormContext basic = context.getBasic();
        String formNo = SequenceUtil.nextSequenceNum(SequenceEnum.FORM);
        basic.setFormNo(formNo);
        FormDto record = gateway.insert(basic);
        if (Objects.isNull(record.getId())){
            exception(ResultStatusEnum.COMMON_INSERT_ERROR);
        }
        List<FormInfoContext> infos = context.getInfos();
        if (CollectionUtil.isEmpty(infos)){
            return ;
        }
        formInfoGateway.insertList(infos,formNo);
    }
    @Override
    public FormDetailDto findById(Long id) {
        FormDetailDto formDetailDto = new FormDetailDto();
        FormDto formDto = gateway.findById(id);
        formDetailDto.setBasic(formDto);
        formDetailDto.setInfos(formInfoGateway.findByFromNo(formDto.getFormNo()));
        return formDetailDto;
    }
    @Override
    public void update(FormInfoWrapContext context) {
        //不能更新formNo
        FormContext basic = context.getBasic();
        String formNo = basic.getFormNo();
        basic.setFormNo(null);
        Pair<Boolean, FormDto> update = gateway.update(basic);
        if (!update.getKey()){
            throw exception(ResultStatusEnum.COMMON_UPDATE_ERROR);
        }
        List<FormInfoContext> infos = context.getInfos();
        if (CollectionUtil.isNotEmpty(infos)){
            List<FormInfoContext> updates = infos.stream().filter(s -> Objects.nonNull(s.getId())).collect(Collectors.toList());
            if (CollectionUtil.isNotEmpty(updates)){
                formInfoGateway.updateList(updates);
            }
            List<FormInfoContext> inserts = infos.stream().filter(s -> Objects.isNull(s.getId())).collect(Collectors.toList());
            if (CollectionUtil.isNotEmpty(inserts)){
                formInfoGateway.insertList(inserts,formNo);
            }
        }
    }
    @Override
    public void delete(Long id) {
        int d = gateway.delete(id);
        if (d <=0 ){
            throw exception(ResultStatusEnum.COMMON_DELETE_ERROR);
        }
    }

    @Override
    public void updateStatus(Long id) {
        FormDto formDto = gateway.findById(id);
        Integer status = StatusEnum.ON.getStatus();
        if (StatusEnum.ON.getStatus().equals(formDto.getStatus())){
            status = StatusEnum.OFF.getStatus();
        }
        FormContext context = FormContext.builder().id(id).status(status).build();
        gateway.update(context);
    }

    @Override
    public List<OptionsDto> allForms() {
        List<OptionsDto> rst = new ArrayList<>();
        List<FormDto> formDtos = gateway.allForms(StatusEnum.ON);
        if (CollectionUtil.isNotEmpty(formDtos)){
            formDtos.forEach(s->{
                rst.add(new OptionsDto(s.getFormNo(),s.getFormChannel(),s.getFormName()));
            });
        }
        return rst;
    }
}
