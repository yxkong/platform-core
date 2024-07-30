package com.github.platform.core.workflow.application.executor.impl;

import com.github.platform.core.auth.util.LoginUserInfoUtil;
import com.github.platform.core.common.service.BaseExecutor;
import com.github.platform.core.common.utils.CollectionUtil;
import com.github.platform.core.standard.constant.ResultStatusEnum;
import com.github.platform.core.standard.entity.dto.PageBean;
import com.github.platform.core.standard.util.LocalDateTimeUtil;
import com.github.platform.core.workflow.application.executor.IFormDataExecutor;
import com.github.platform.core.workflow.domain.constant.FlwConstant;
import com.github.platform.core.workflow.domain.constant.ProcessTypeEnum;
import com.github.platform.core.workflow.domain.context.FormDataContext;
import com.github.platform.core.workflow.domain.context.FormDataQueryContext;
import com.github.platform.core.workflow.domain.dto.FormDataDto;
import com.github.platform.core.workflow.domain.dto.FormInfoDto;
import com.github.platform.core.workflow.domain.dto.ProcessInstanceDto;
import com.github.platform.core.workflow.domain.gateway.ICustomFormDataGateway;
import com.github.platform.core.workflow.domain.gateway.IFormDataGateway;
import com.github.platform.core.workflow.infra.util.BpmnModelUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
    @Resource
    private Map<String,ICustomFormDataGateway> formDataGatewayMap;
    @Override
    public PageBean<FormDataDto> query(FormDataQueryContext context){
        return gateway.query(context);
    };
    @Override
    public void insert(FormDataContext context){
        FormDataDto record = gateway.insert(context);
        if (Objects.isNull(record.getId())){
            throw exception(ResultStatusEnum.COMMON_INSERT_ERROR);
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
            throw exception(ResultStatusEnum.COMMON_UPDATE_ERROR);
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
    public void formDataHandler(List<FormDataContext> taskFormInfo, String instanceNo, String formInstNo) {
        List<FormDataContext> inserts = new ArrayList<>();
        List<FormDataContext> updates = new ArrayList<>();

        if (CollectionUtil.isNotEmpty(taskFormInfo)){
            taskFormInfo.forEach(item -> {
                LocalDateTime now = LocalDateTimeUtil.dateTime();
                item.setUpdateTime(now);
                item.setUpdateBy(LoginUserInfoUtil.getLoginName());
                item.setRemark(formInstNo);
                if (Objects.isNull(item.getId())){
                    item.setCreateTime(now);
                    item.setCreateBy(LoginUserInfoUtil.getLoginName());
                    inserts.add(item);
                }else {
                    updates.add(item);
                }
            });
            if (CollectionUtil.isNotEmpty(inserts)) {
                gateway.insertList(inserts, instanceNo);
            }
            if (CollectionUtil.isNotEmpty(updates)){
                gateway.updateList(updates,instanceNo);
            }
        }
    }

    /**
     * 获取表单数据
     * @param instanceDto
     * @param formKey
     * @return
     */
    @Override
    public List<FormInfoDto> getFormInfoWithData(ProcessInstanceDto instanceDto, boolean isMain, String formKey){
        ProcessTypeEnum processTypeEnum = ProcessTypeEnum.get(instanceDto.getProcessType());
        String formNo =  BpmnModelUtils.getFormNo(formKey);
        if (instanceDto.isPm() && isMain){
            formNo = FlwConstant.PM_FORM_KEY ;
        }

        String beanName = ProcessTypeEnum.OA.getFormBean();
        if (isMain){
            beanName = processTypeEnum.getFormBean();
        }
        ICustomFormDataGateway formDataGateway = formDataGatewayMap.get(beanName);

        return formDataGateway.getFormViewWithData(instanceDto.getBizNo(),instanceDto.getInstanceNo(),formNo);
    }


}
