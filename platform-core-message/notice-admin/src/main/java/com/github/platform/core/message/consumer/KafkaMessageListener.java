package com.github.platform.core.message.consumer;

import com.github.platform.core.common.utils.CollectionUtil;
import com.github.platform.core.common.utils.JsonUtils;
import com.github.platform.core.kafka.service.IKafkaService;
import com.github.platform.core.message.application.executor.IMessageNoticeExecutor;
import com.github.platform.core.message.domain.constant.MessageTopicConstant;
import com.github.platform.core.message.domain.context.SysNoticeEventLogContext;
import com.github.platform.core.message.domain.dto.SysNoticeChannelConfigDto;
import com.github.platform.core.message.domain.dto.SysNoticeTemplateDto;
import com.github.platform.core.message.domain.gateway.ISysNoticeChannelConfigGateway;
import com.github.platform.core.message.domain.gateway.ISysNoticeEventLogGateway;
import com.github.platform.core.message.domain.gateway.ISysNoticeTemplateGateway;
import com.github.platform.core.standard.constant.StatusEnum;
import com.github.platform.core.standard.entity.context.MessageNoticeContext;
import com.github.platform.core.standard.entity.event.DomainEvent;
import com.github.platform.core.standard.util.LocalDateTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * kafka topic消费
 * @author: yxkong
 * @date: 2024/5/12 11:40
 * @version: 1.0
 */
@Component
@Slf4j
//@ConditionalOnProperty(name = "platform.message.listener.kafka.enabled", havingValue = "true",matchIfMissing = false)
public class KafkaMessageListener {
    @Resource
    private IMessageNoticeExecutor messageNoticeExecutor;

    @KafkaListener(topics = {MessageTopicConstant.NOTICE_TOPIC_NAME}, containerFactory = "bizKafkaListenerContainerFactory")
    public void batchMessageNotice(List<String> messages) {
        processMessages(messages);
    }

    private void processMessages(List<String> messages) {
        if (CollectionUtil.isEmpty(messages)) {
            return;
        }
        for (String message : messages) {
            try {
                /**
                 * 1,查找模板，没有模板不处理
                 * 2，查找通道（消息传递有，使用消息传递的，没有使用模板默认的）
                 * 3，使用模板方法发送消息
                 */
                DomainEvent domainEvent = JsonUtils.fromJson(message, DomainEvent.class);
                messageNoticeExecutor.execute(domainEvent,false);
            } catch (Exception e) {
                log.error("接收消息异常，提示信息为：{}，明细为：", message, e);
            }
        }
    }




}
