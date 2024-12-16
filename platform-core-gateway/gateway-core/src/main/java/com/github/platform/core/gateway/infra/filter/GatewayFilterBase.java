package com.github.platform.core.gateway.infra.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.cors.reactive.CorsUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * gateway filter 基础类
 *
 * @author: yxkong
 * @date: 2023/4/20 11:12 AM
 * @version: 1.0
 */
@Slf4j
public class GatewayFilterBase {
    byte[] tokenNull = "{\"status\":\"1008\",\"message\":\"网关拦截，token无效,请重新登录！\"}".getBytes(StandardCharsets.UTF_8);
    byte[] tokenError = "{\"status\":\"1004\",\"message\":\"网关拦截，无数据权限，请联系开发人员!\"}".getBytes(StandardCharsets.UTF_8);
    private static final String ALLOWED_HEADERS = "*";
    private static final String ALLOWED_METHODS = "*";
    private static final String ALLOWED_ORIGIN = "*";
    private static final String ALLOWED_Expose = "*";
    private static final String MAX_AGE = "18000L";
    protected static final String UTF_8 = "utf-8";
    protected void printHeader(ServerHttpRequest request) {
        if (log.isDebugEnabled()){
            log.debug("请求地址:{},header:{}",request.getURI().getPath(),request.getHeaders().toString());
        }
    }

    /**
     * 返回失败
     *
     * @param exchange
     * @param type     true 表示token为空，false，表示异常
     * @return
     */
    protected Mono<Void> authFail(ServerWebExchange exchange, Boolean type) {
        ServerHttpResponse serverHttpResponse = exchange.getResponse();
        serverHttpResponse.setStatusCode(HttpStatus.OK);
        byte[] response = type ? tokenNull : tokenError;
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
        DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(response);
        return exchange.getResponse().writeWith(Flux.just(buffer));
    }
    protected ServerWebExchange corsConfig(ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        HttpHeaders headers = response.getHeaders();

        headers.remove("Access-Control-Allow-Origin");
        headers.remove("Access-Control-Allow-Methods");
        headers.remove("Access-Control-Max-Age");
        headers.remove("Access-Control-Allow-Headers");
        headers.remove("Access-Control-Expose-Headers");
        headers.remove("Access-Control-Allow-Credentials");

        headers.add("Access-Control-Allow-Origin", ALLOWED_ORIGIN);
        headers.add("Access-Control-Allow-Methods", ALLOWED_METHODS);
        headers.add("Access-Control-Max-Age", MAX_AGE);
        headers.add("Access-Control-Allow-Headers", ALLOWED_HEADERS);
        headers.add("Access-Control-Expose-Headers", ALLOWED_Expose);
        headers.add("Access-Control-Allow-Credentials", "true");

        return exchange;
    }

}
