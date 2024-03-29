package com.github.platform.core.gateway.infra.exception;
//
//
//import org.springframework.beans.factory.ObjectProvider;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.web.ServerProperties;
//import org.springframework.boot.autoconfigure.web.WebProperties;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.boot.web.reactive.error.ErrorAttributes;
//import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.Ordered;
//import org.springframework.core.annotation.Order;
//import org.springframework.http.codec.ServerCodecConfigurer;
//import org.springframework.web.reactive.result.view.ViewResolver;
//
//import javax.annotation.Resource;
//import java.util.Collections;
//import java.util.List;
//
///**
// * webflux 全局异常处理配置
// * 由于webflux的函数式编程方式中不能通过controllerAdvice只能通过每个RouterFunction中添加filter的方式实现异常处理，这里通过注入一个自定义ErrorWebExceptionHandler来达到全局异常处理的目的
// * @author yxk
// */
//@Configuration
//@EnableConfigurationProperties({ ServerProperties.class})
//public class ErrorHandlerConfiguration {
//
//    @Resource
//    private final ServerProperties serverProperties;
//
//    @Resource
//    private final ApplicationContext applicationContext;
//    @Resource
//    private final List<ViewResolver> viewResolvers;
//    @Resource
//    private final WebProperties.Resources resources;
//    @Resource
//    private final ServerCodecConfigurer serverCodecConfigurer;
//
//    public ErrorHandlerConfiguration(ServerProperties serverProperties,WebProperties.Resources resources,
//                                     ObjectProvider<List<ViewResolver>> viewResolversProvider, ServerCodecConfigurer serverCodecConfigurer,
//                                     ApplicationContext applicationContext) {
//        this.serverProperties = serverProperties;
//        this.resources = resources;
//        this.applicationContext = applicationContext;
//        this.viewResolvers = viewResolversProvider.getIfAvailable(Collections::emptyList);
//        this.serverCodecConfigurer = serverCodecConfigurer;
//    }
//
//    @Bean
//    @Order(Ordered.HIGHEST_PRECEDENCE)
//    public ErrorWebExceptionHandler customerErrorWebExceptionHandler(ErrorAttributes errorAttributes) {
//        GatewayGlobalException exceptionHandler = new GatewayGlobalException(errorAttributes,this.resources
//                ,this.serverProperties.getError(), this.applicationContext);
//        exceptionHandler.setViewResolvers(this.viewResolvers);
//        exceptionHandler.setMessageWriters(this.serverCodecConfigurer.getWriters());
//        exceptionHandler.setMessageReaders(this.serverCodecConfigurer.getReaders());
//        return exceptionHandler;
//    }
//}
