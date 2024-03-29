//package com.github.platform.core.sys.infra.configuration;
//
//import org.slf4j.MDC;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.Ordered;
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.UUID;
//
///**
// * @author Qiuxinchao
// * @date 2023/2/21 17:34
// * @describe
// */
//@Configuration
//public class TraceIdConfig implements WebMvcConfigurer {
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(interceptor()).addPathPatterns("/**").order(Ordered.HIGHEST_PRECEDENCE);
//    }
//
//    private HandlerInterceptor interceptor(){
//        return new HandlerInterceptor() {
//            @Override
//            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//                MDC.put("traceId", UUID.randomUUID().toString().replaceAll("-", "").substring(0,10));
//                return HandlerInterceptor.super.preHandle(request, response, handler);
//            }
//
//            @Override
//            public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//                HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
//                MDC.clear();
//            }
//        };
//    }
//
//
//}
