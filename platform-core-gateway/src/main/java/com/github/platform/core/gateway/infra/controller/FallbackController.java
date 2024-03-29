package com.github.platform.core.gateway.infra.controller;

import com.github.platform.core.gateway.infra.exception.GatewayException;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author: yxkong
 * @date: 2021/12/18 5:26 PM
 * @version: 1.0
 */
@RestController
public class FallbackController {
    @RequestMapping("/fallback")
    public Mono<Void> fallback(ServerHttpRequest request, ServerHttpResponse serverHttpResponse){
        serverHttpResponse.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        DataBuffer buffer = serverHttpResponse.bufferFactory().wrap(GatewayException.REFUSE.getBytes());
        return serverHttpResponse.writeWith(Mono.just(buffer));
    }

}
