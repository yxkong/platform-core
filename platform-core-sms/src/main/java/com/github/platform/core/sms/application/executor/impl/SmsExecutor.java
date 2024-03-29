package com.github.platform.core.sms.application.executor.impl;

import com.github.platform.core.common.utils.*;
import com.github.platform.core.sms.application.executor.ISmsExecutor;
import com.github.platform.core.sms.domain.common.entity.SysSmsLogBase;
import com.github.platform.core.sms.domain.context.SendSmsContext;
import com.github.platform.core.sms.domain.context.SysSmsLogContext;
import com.github.platform.core.sms.domain.dto.SysSmsTemplateDto;
import com.github.platform.core.sms.domain.entity.SendSmsEntity;
import com.github.platform.core.sms.domain.entity.SendSmsResultEntity;
import com.github.platform.core.sms.domain.entity.SmsSpTemplateEntity;
import com.github.platform.core.sms.domain.gateway.ISysSmsLogGateway;
import com.github.platform.core.sms.domain.gateway.ISysSmsTemplateGateway;
import com.github.platform.core.sms.domain.gateway.ISysSmsWhiteListGateway;
import com.github.platform.core.sms.infra.convert.SmsInfraConvert;
import com.github.platform.core.sms.infra.service.ISmsRouterService;
import com.github.platform.core.sms.infra.service.ISmsService;
import com.github.platform.core.standard.constant.ResultStatusEnum;
import com.github.platform.core.standard.constant.StatusEnum;
import com.github.platform.core.standard.entity.dto.ResultBean;
import com.github.platform.core.standard.exception.ConfigRuntimeException;
import com.github.platform.core.standard.util.ResultBeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Objects;

/**
 *
 * @author: yxkong
 * @date: 2023/3/1 2:56 PM
 * @version: 1.0
 */
@Service
@Slf4j
public class SmsExecutor implements ISmsExecutor {
    @Resource
    private Map<String, ISmsService> sendServiceMap;
    @Resource
    private ISysSmsLogGateway sysSmsLogGateway;
    @Resource
    private ISysSmsTemplateGateway smsTemplateGateway;
    @Resource
    private ISysSmsWhiteListGateway smsWhiteListGateway;
    @Resource
    private ISmsRouterService routerService;
    @Resource
    private SmsInfraConvert convert;
    @Resource
    private IdWorker idWorker;
    @Override
    public ResultBean sendSms(SendSmsContext context) {
        /**①，幂等校验*/
        if (repeat(context.getMsgId())){
            if (log.isDebugEnabled()){
                log.debug("msgId存在重复记录，不再处理,消息内容为：{}", JsonUtils.toJson(context));
            }
            return ResultBeanUtil.result(ResultStatusEnum.REPEAT);
        }
        /**②，查找模板*/
        SysSmsTemplateDto smsTemplate = smsTemplateGateway.findByTempNo(context.getTempNo());
        if (Objects.isNull(smsTemplate) || !smsTemplate.isValid()){
            throw new ConfigRuntimeException(ResultStatusEnum.CONFIG_ERROR.getStatus(),"未找到有效的模板，请核查");
        }
        /**③，路由到可用的厂商模板*/
        SmsSpTemplateEntity smsSpTemplateEntity = routerService.router(smsTemplate);
        if (Objects.isNull(smsSpTemplateEntity) ){
            throw new ConfigRuntimeException(ResultStatusEnum.CONFIG_ERROR.getStatus(),"未找到模板对应的有效厂商");
        }

        /**获取对应厂商的实现*/
        ISmsService  smsService =sendServiceMap.get(smsSpTemplateEntity.getBeanName());
        if (smsService == null){
            throw new ConfigRuntimeException(ResultStatusEnum.CONFIG_ERROR.getStatus(), String.format(ResultStatusEnum.CONFIG_ERROR.getMessage(), "ISmsService"));
        }
        if (log.isDebugEnabled()){
            log.debug("路由后的厂商服务{}", JsonUtils.toJson(smsService.getClass()));
        }
        /**解析短信*/
        String content = PlaceholderUtil.replace(smsTemplate.getContent(),context.getParams());
        if (log.isWarnEnabled()){
            log.warn("content为{}",content);
        }
        /**保存短信发送日志*/
        SysSmsLogContext smsLog = convert.of(context, smsSpTemplateEntity.getProNo(), content, StatusEnum.OFF.getStatus(), 0);
        sysSmsLogGateway.insert(smsLog);
        /**准备发送*/

        SendSmsResultEntity send = sendSms(context,smsSpTemplateEntity,content, smsService);
        updateLogStatus(smsLog, send);
        return ResultBeanUtil.success();
    }

    /**
     * 发送短信
     * @param context  发送短信上下文
     * @param smsSpTemplateEntity  短信服务商
     * @param content 短信内容
     * @param smsService
     * @return
     */
    private SendSmsResultEntity sendSms(SendSmsContext context,  SmsSpTemplateEntity smsSpTemplateEntity,String content, ISmsService smsService) {
        // 组装发送短信上下文
        SendSmsEntity smsSend = convert.of(context,smsSpTemplateEntity,content);

        if (log.isDebugEnabled()){
            log.debug("短信发送实体SendSmsEntity："+JsonUtils.toJson(smsSend));
        }
        SendSmsResultEntity send;

        //非生产环境只能发送白名单中的
        if (ApplicationContextHolder.isProd() || isWhiteList(smsSend.getMobile())){
            send = smsService.sendSms(smsSend);
        }else{
            String msgId = idWorker.bizNo();
            if (log.isDebugEnabled()){
                log.debug("当前mobile:{} 不在白名单内,构建一条成功短信msgId为：{}",  context.getMobile(),msgId);
            }
            send =  SendSmsResultEntity.builder().status(1).msgId(msgId).build();
        }
        if (log.isDebugEnabled()){
            log.debug("短信发送结果：{}",JsonUtils.toJson(send));
        }
        return send;
    }

    /**
     * 更新
     * @param smsLog
     * @param send
     */
    private void updateLogStatus(SysSmsLogContext smsLog, SendSmsResultEntity send) {
        /**更新发送结果*/
        SysSmsLogContext updateDo = SysSmsLogContext.builder()
                .id(smsLog.getId())
                .msgId(send.getMsgId())
                .status(send.getStatus())
                .remark(getRemark(smsLog.getRemark(), send.getErrorMsg()))
                .build();
        sysSmsLogGateway.update(updateDo);
    }

    private String getRemark(String remark,String msg){
        if(StringUtils.isNotEmpty(remark)){
            return remark+",send msg:"+msg;
        }
        return "send msg:"+msg;
    }
    private Boolean isWhiteList(String mobile) {
        return smsWhiteListGateway.existMobile(mobile);
    }


    private Boolean repeat(String msgId){
        SysSmsLogBase smsLog = sysSmsLogGateway.findByMsgId(msgId);
        if (Objects.nonNull(smsLog)){
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}
