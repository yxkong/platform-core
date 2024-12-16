package com.github.platform.core.monitor.infra.websocket.configuration;

import com.github.platform.core.monitor.infra.websocket.handler.OnLineUserHandler;
import com.github.platform.core.monitor.infra.websocket.interceptor.WebSocketInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import javax.annotation.Resource;

/**
 * webSocket配置
 * @author: yxkong
 * @date: 2023/6/8 10:46 AM
 * @version: 1.0
 */
@Configuration
@EnableWebSocket
public class WebSocketConfiguration implements WebSocketConfigurer{
    @Resource
    private OnLineUserHandler onLineUserHandler;
    @Resource
    private WebSocketInterceptor webSocketInterceptor;
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        //websocket请求映射
        registry.addHandler(onLineUserHandler, "/websocket")
                //添加拦截器,在拦截器中验证token
                .addInterceptors(webSocketInterceptor)
                // 关闭跨域
                .setAllowedOrigins("*")
        ;
    }
}
