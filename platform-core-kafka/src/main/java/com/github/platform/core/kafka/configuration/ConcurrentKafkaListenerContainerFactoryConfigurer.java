package com.github.platform.core.kafka.configuration;

import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.converter.RecordMessageConverter;

import java.time.Duration;

/**
 * @author: yxkong
 * @date: 2021/6/15 2:37 下午
 * @version: 1.0
 */
public class ConcurrentKafkaListenerContainerFactoryConfigurer {
    private KafkaProperties properties;

    private RecordMessageConverter messageConverter;

    private KafkaTemplate<Object, Object> replyTemplate;

    /**
     * Set the {@link KafkaProperties} to use.
     *
     * @param properties the properties
     */
    void setKafkaProperties(KafkaProperties properties) {
        this.properties = properties;
    }

    /**
     * Set the {@link RecordMessageConverter} to use.
     *
     * @param messageConverter the message converter
     */
    void setMessageConverter(RecordMessageConverter messageConverter) {
        this.messageConverter = messageConverter;
    }

    /**
     * Set the {@link KafkaTemplate} to use to send replies.
     *
     * @param replyTemplate the reply template
     */
    void setReplyTemplate(KafkaTemplate<Object, Object> replyTemplate) {
        this.replyTemplate = replyTemplate;
    }

    /**
     * Configure the specified Kafka listener container factory. The factory can be further tuned and default settings
     * can be overridden.
     *
     * @param listenerFactory the {@link ConcurrentKafkaListenerContainerFactory} instance to configure
     * @param consumerFactory the {@link ConsumerFactory} to use
     */
    public void configure(ConcurrentKafkaListenerContainerFactory<Object, Object> listenerFactory,
                          ConsumerFactory<Object, Object> consumerFactory) {
        listenerFactory.setConsumerFactory(consumerFactory);
        configureListenerFactory(listenerFactory);
        configureContainer(listenerFactory.getContainerProperties());
    }

    private void configureListenerFactory(ConcurrentKafkaListenerContainerFactory<Object, Object> factory) {
        PropertyMapper map = PropertyMapper.get();
        KafkaProperties.Listener properties = this.properties.getListener();
        map.from(properties::getConcurrency).whenNonNull().to(factory::setConcurrency);
        map.from(() -> this.messageConverter).whenNonNull().to(factory::setMessageConverter);
        map.from(() -> this.replyTemplate).whenNonNull().to(factory::setReplyTemplate);
        map.from(properties::getType).whenEqualTo(KafkaProperties.Listener.Type.BATCH).toCall(() -> factory.setBatchListener(true));
    }

    private void configureContainer(ContainerProperties container) {
        PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
        KafkaProperties.Listener properties = this.properties.getListener();
        map.from(properties::getAckMode).to(container::setAckMode);
        map.from(properties::getClientId).to(container::setClientId);
        map.from(properties::getAckCount).to(container::setAckCount);
        map.from(properties::getAckTime).as(Duration::toMillis).to(container::setAckTime);
        map.from(properties::getPollTimeout).as(Duration::toMillis).to(container::setPollTimeout);
        map.from(properties::getNoPollThreshold).to(container::setNoPollThreshold);
        map.from(properties::getIdleEventInterval).as(Duration::toMillis).to(container::setIdleEventInterval);
        map.from(properties::getMonitorInterval).as(Duration::getSeconds).as(Number::intValue)
                .to(container::setMonitorInterval);
        map.from(properties::getLogContainerConfig).to(container::setLogContainerConfig);
        map.from(properties::isMissingTopicsFatal).to(container::setMissingTopicsFatal);
    }
}