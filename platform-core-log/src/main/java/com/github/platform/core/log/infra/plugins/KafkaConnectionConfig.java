//package com.github.platform.core.log.infra.plugins;
//
//import org.apache.logging.log4j.core.config.plugins.Plugin;
//import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
//import org.apache.logging.log4j.core.config.plugins.PluginFactory;
//import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;
//
///**
// * kafka log4j2配置
// *
// * @author: yxkong
// * @date: 2024/3/5 11:26
// * @version: 1.0
// */
//@Plugin(name = "KafkaConnectionConfig", category = "Core", elementType = "config", printObject = true)
//public class KafkaConnectionConfig  {
//    private final String bootstrapServers;
//    private final String compressionType;
//    private final int timeoutMs;
//
//    // 私有构造函数，只能通过PluginFactory方法创建实例
//    private KafkaConnectionConfig(String bootstrapServers, String compressionType, int timeoutMs) {
//        this.bootstrapServers = bootstrapServers;
//        this.compressionType = compressionType;
//        this.timeoutMs = timeoutMs;
//    }
//
//    public String getBootstrapServers() {
//        return bootstrapServers;
//    }
//
//    public String getCompressionType() {
//        return compressionType;
//    }
//
//    public int getTimeoutMs() {
//        return timeoutMs;
//    }
//
//    @PluginFactory
//    public static KafkaConnectionConfig createConnectionConfig(
//            @PluginAttribute(value = "bootstrapServers") @Required final String bootstrapServers,
//            @PluginAttribute(value = "compressionType",defaultString = "gzip") final String compressionType,
//            @PluginAttribute(value = "timeoutMs", defaultLong = 5000) final long timeoutMs) {
//
//        if (timeoutMs < 0) {
//            throw new IllegalArgumentException("Timeout value must be non-negative");
//        }
//
//        return new KafkaConnectionConfig(bootstrapServers, compressionType, Math.toIntExact(timeoutMs));
//    }
//}