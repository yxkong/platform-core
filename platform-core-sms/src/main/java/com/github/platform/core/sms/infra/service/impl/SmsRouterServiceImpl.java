package com.github.platform.core.sms.infra.service.impl;

import com.github.platform.core.common.utils.CollectionUtil;
import com.github.platform.core.sms.domain.dto.SysSmsServiceProviderDto;
import com.github.platform.core.sms.domain.dto.SysSmsTemplateDto;
import com.github.platform.core.sms.domain.dto.SysSmsTemplateStatusDto;
import com.github.platform.core.sms.domain.entity.SmsSpTemplateEntity;
import com.github.platform.core.sms.domain.gateway.ISysSmsServiceProviderGateway;
import com.github.platform.core.sms.domain.gateway.ISysSmsTemplateStatusGateway;
import com.github.platform.core.sms.infra.constant.SmsInfraEnum;
import com.github.platform.core.sms.infra.convert.SmsInfraConvert;
import com.github.platform.core.sms.infra.service.ISmsRouterService;
import com.github.platform.core.sms.infra.service.ISmsSpRoutingStrategy;
import com.github.platform.core.sms.infra.utils.SmsPwdUtil;
import com.github.platform.core.standard.exception.ConfigRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 默认路由服务
 * @author: yxkong
 * @date: 2022/6/23 10:29 AM
 * @version: 1.0
 */
@Service
@Slf4j
public class SmsRouterServiceImpl implements ISmsRouterService {
    @Resource
    private ISysSmsServiceProviderGateway serviceProviderGateway;
    @Resource
    private ISysSmsTemplateStatusGateway templateStatusGateway;
    @Autowired
    private Map<String, ISmsSpRoutingStrategy> routingStrategyMap;
    @Resource
    private SmsInfraConvert smsConvert;

    @Override
    public SmsSpTemplateEntity router(SysSmsTemplateDto smsTemplate) {
        String tempNo = smsTemplate.getTempNo();
        /**查询所有启用的厂商*/
        List<SysSmsTemplateStatusDto> list = templateStatusGateway.findListByTempNo(tempNo);
        if (CollectionUtil.isEmpty(list)){
            throw new ConfigRuntimeException(SmsInfraEnum.CONFIG_TEMP_SP_ERROR).format(tempNo);
        }
        ISmsSpRoutingStrategy routingStrategy = routingStrategyMap.get(smsTemplate.getRouteType()+"SmsSpRoutingStrategy");
        SysSmsTemplateStatusDto serviceProviderStatus = routingStrategy.route(smsTemplate, list);
        if (Objects.isNull(serviceProviderStatus)){
            throw new ConfigRuntimeException(SmsInfraEnum.CONFIG_ROUTE_TEMP_SP_ERROR).format(tempNo,smsTemplate.getProNo());
        }
        /**校验获取的的启用厂商的模板是否生效*/
        SmsSpTemplateEntity smsSpTemplate =  smsConvert.target(serviceProviderStatus);
        //判断该厂商是否被禁用了
        SysSmsServiceProviderDto serviceProvider = serviceProviderGateway.findByProNo(serviceProviderStatus.getProNo());
        if (Objects.isNull(serviceProvider)){
            String msg = String.format("未找到厂商编号%s对应的有效厂商", tempNo,serviceProvider.getProNo());
            throw new ConfigRuntimeException(SmsInfraEnum.CONFIG_SP_DISABLE).format();
        }
        smsSpTemplate.setBeanName(serviceProvider.getBeanName());
        smsSpTemplate.setAccountName(serviceProvider.getAccount());
        smsSpTemplate.setPassword(SmsPwdUtil.me.decode(serviceProvider.getEncryptPwd(),serviceProvider.getSalt()));
        smsSpTemplate.setTemplate(smsSpTemplate.getTemplate());
        smsSpTemplate.setTempNo(smsTemplate.getTempNo());
        return smsSpTemplate;
    }
}
