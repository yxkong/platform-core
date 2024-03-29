package com.github.platform.core.workflow.infra.gateway.impl;

import com.github.platform.core.persistence.mapper.workflow.FormDataMapper;
import com.github.platform.core.workflow.domain.common.entity.FormDataBase;
import com.github.platform.core.workflow.domain.dto.FormDataDto;
import com.github.platform.core.workflow.domain.gateway.ICustomFormDataGateway;
import com.github.platform.core.workflow.infra.convert.FormDataInfraConvert;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 工作流表单数据获取
 * @author: yxkong
 * @date: 2024/3/12 14:27
 * @version: 1.0
 */
@Service("sysFormDataGateway")
public class SysFormDataGatewayImpl implements ICustomFormDataGateway {
    @Resource
    private FormDataMapper formDataMapper;
    @Resource
    private FormDataInfraConvert formDataInfraConvert;
    @Override
    public List<FormDataDto> findFormData(String bizNo, String instanceNo, String formKey) {
        FormDataBase formDataBase = FormDataBase.builder().instanceNo(instanceNo).formNo(formKey).build();
        List<FormDataBase> list = formDataMapper.findListBy(formDataBase);
        return formDataInfraConvert.toDtos(list);
    }
}
