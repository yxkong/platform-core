package com.github.platform.core.gateway.infra.filter;

import brave.Tracing;
import com.alibaba.fastjson2.JSONObject;
import com.github.platform.core.common.constant.SpringBeanOrderConstant;
import com.github.platform.core.common.utils.CollectionUtil;
import com.github.platform.core.common.utils.StringUtils;
import com.github.platform.core.gateway.infra.constants.SkipHeaderEnum;
import com.github.platform.core.standard.constant.HeaderConstant;
import com.github.platform.core.standard.entity.dto.ResultBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.sleuth.CurrentTraceContext;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.cloud.sleuth.instrument.web.WebFluxSleuthOperators;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.GZIPInputStream;

/**
 * 链路追踪
 * @author: yxkong
 * @date: 2023/8/30 4:07 PM
 * @version: 1.0
 */
@Component
@Slf4j
public class GatewayTracingFilter implements GlobalFilter, Ordered {
    public static final String IGNORE_CONTENT_TYPE = "multipart/form-data";
    public static final Set<String> EXCLUDE_URI_SET = new HashSet<>(Arrays.asList(
            "actuator/health", "html", "css", "js", "png", "gif", "configure",
            "v2/api-docs", "swagger", "swagger-resources", "csrf", "webjars"));
    @Autowired
    private Tracer tracer;
    @Autowired
    private CurrentTraceContext currentTraceContext;
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        if (shouldExclude(request)){
            return chain.filter(exchange);
        }
        processRequest(exchange,request);
        String token = exchange.getRequest().getHeaders().getFirst(HeaderConstant.TOKEN);
        String url = exchange.getRequest().getPath().toString();
        if (isTracing(request)) {
            DataBufferFactory dataBufferFactory = exchange.getResponse().bufferFactory();
            return DataBufferUtils.join(exchange.getRequest().getBody())
                    .flatMap(dataBuffer -> {
                        try {
                            byte[] requestBodyBytes = dataBufferToBytes(dataBuffer);
                            String requestBody = newString(requestBodyBytes);
                            putSpan(exchange,"request.body", requestBody);
                            ServerHttpRequest newRequest = new ServerHttpRequestDecorator(request) {
                                @Override
                                public Flux<DataBuffer> getBody() {
                                    // 在这里对请求体进行传递，如果修改，需要修改header
                                    DataBuffer buffer = dataBufferFactory.wrap(requestBodyBytes);
                                    return Flux.just(buffer);
                                }
                            };

                            ServerHttpResponse response = exchange.getResponse();
                            ServerHttpResponse decoratedResponse = new ServerHttpResponseDecorator(response) {
                                /**
                                 * 此方法在等调用完转发的接口后才会执行
                                 * @param body the body content publisher
                                 * @return
                                 */
                                @Override
                                public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                                    if (body instanceof Flux) {
                                        Flux<? extends DataBuffer> fluxBody = (Flux<? extends DataBuffer>) body;
                                        return super.writeWith(
                                                fluxBody.buffer().map(dataBuffers -> {
                                                    DataBuffer joinedBuffer = dataBufferFactory.join(dataBuffers);
                                                    byte[] contentBytes = dataBufferToBytes(joinedBuffer);
                                                    String result = newString(requestBodyBytes);
                                                    // 处理响应结果
                                                    putResult(exchange,result);
                                                    //如果对响应结果有改动，需要重新载入
//                                                    byte[] uppedContent = new String(result.getBytes(), StandardCharsets.UTF_8).getBytes();
//                                                    response.getHeaders().setContentLength(uppedContent.length);
                                                    return dataBufferFactory.wrap(contentBytes);
                                                })
                                        );
                                    }
                                    return super.writeWith(body);
                                }
                                @Override
                                public Mono<Void> writeAndFlushWith(Publisher<? extends Publisher<? extends DataBuffer>> body) {
                                    return writeWith(Flux.from(body).flatMapSequential(p -> p));
                                }
                            };
                            return chain.filter(exchange.mutate().request(newRequest).response(decoratedResponse).build());
                        } catch (Exception e) {
                            log.error("token:{} url:{} 链路追踪异常",token,url,e);
                        } finally {
                            DataBufferUtils.release(dataBuffer);
                        }
                        //异常以后原路执行
                        return chain.filter(exchange);
                    });
        }
        return chain.filter(exchange);
    }
    protected byte[] dataBufferToBytes(DataBuffer dataBuffer) {
        byte[] content = new byte[dataBuffer.readableByteCount()];
        dataBuffer.read(content);
        //释放掉内存
        DataBufferUtils.release(dataBuffer);
        return content;
    }
    protected boolean shouldExclude(ServerHttpRequest request) {
        String url = request.getURI().toString();
        String contentType = request.getHeaders().getFirst("Content-Type");
        Set<String> excludeSet = EXCLUDE_URI_SET.stream().filter(url::contains).collect(Collectors.toSet());
        return !excludeSet.isEmpty() || Objects.equals(IGNORE_CONTENT_TYPE, contentType);
    }

    /**
     * 判断是否需要追踪
     * @param request
     * @return
     */
    private boolean isTracing(ServerHttpRequest request){
        String contentType = request.getHeaders().getFirst(HttpHeaders.CONTENT_TYPE);
        return  (HttpMethod.POST.equals(request.getMethod()) || HttpMethod.PUT.equals(request.getMethod()))
                && (StringUtils.isNotEmpty(contentType) && contentType.contains(HeaderConstant.CONTENT_TYPE_JSON));
    }
    /**
     * 解析contentBytes
     * @param contentBytes
     * @return
     */
    private String newString(byte[] contentBytes) {
        return new String(contentBytes, StandardCharsets.UTF_8);
    }

    /**
     * 解析response
     * @param response
     * @param contentBytes
     * @return
     */
    private String parseResponse(ServerHttpResponse response,byte[] contentBytes) {
        List<String> contentEncodingHeaders = response.getHeaders().get(HttpHeaders.CONTENT_ENCODING);
        if (CollectionUtil.isNotEmpty(contentEncodingHeaders) && contentEncodingHeaders.contains(HeaderConstant.GZIP)) {
            try (GZIPInputStream gzipInputStream = new GZIPInputStream(new ByteArrayInputStream(contentBytes),contentBytes.length)) {
                Reader reader = new InputStreamReader(gzipInputStream, StandardCharsets.UTF_8);
                return IOUtils.toString(reader);
            } catch (IOException e) {
                // Handle the exception if needed
            }
        }
        return new String(contentBytes, StandardCharsets.UTF_8);
    }

    /**
     * 将标准响应体放入到追踪中
     * @param exchange
     * @param result
     */
    private void putResult(ServerWebExchange exchange,String result){
        try {
            JSONObject jsonObject = JSONObject.parseObject(result);
            if (jsonObject.containsKey(ResultBean.STATUS)){
                putSpan(exchange,"response.status", jsonObject.getString(ResultBean.STATUS));
            }
            if (jsonObject.containsKey(ResultBean.MESSAGE)){
                putSpan(exchange,"response.message", jsonObject.getString(ResultBean.MESSAGE));
            }
            if (jsonObject.containsKey(ResultBean.TIMESTAMP)){
                putSpan(exchange,"response.timestamp", jsonObject.getString(ResultBean.TIMESTAMP));
            }
            if (jsonObject.containsKey(ResultBean.DATA)){
                putSpan(exchange,"response.data", jsonObject.getString(ResultBean.DATA));
            }
        } catch (Exception e) {
        }
    }
    protected void putSpan(ServerWebExchange exchange,String key, String value) {
        // 不为空
        Optional<Span> currentSpan = Optional.ofNullable(tracer.currentSpan());
        currentSpan.ifPresent(span -> span.tag(key, value));

        /**
         *
         */
        if (!currentSpan.isPresent()) {
            WebFluxSleuthOperators.withSpanInScope(tracer, currentTraceContext, exchange, () -> {
                try {
                    Tracing.currentTracer().currentSpan().tag(key, value);
                } catch (Exception e) {
                }
            });
        }
    }
    /**
     * 处理请求
     * @param request
     */
    private void processRequest(ServerWebExchange exchange,ServerHttpRequest request){
        processRequestHeaders(exchange,request.getHeaders());
        processQueryParameters(exchange,request);
    }

    /**
     * 处理请求头
     * @param headers
     */
    private void processRequestHeaders(ServerWebExchange exchange,HttpHeaders headers) {
        headers.entrySet().stream()
                .filter(entry -> !SkipHeaderEnum.contains(entry.getKey()))
                .forEach(entry -> {putSpan(exchange,"request.headers." + entry.getKey(), String.join(",", entry.getValue()));});
    }
    /**
     * 处理query
     * @param request
     */
    private void processQueryParameters(ServerWebExchange exchange,ServerHttpRequest request) {
        String queryStr = request.getURI().getQuery();
        if (StringUtils.isNotEmpty(queryStr)) {
            putSpan(exchange,"request.queryString", queryStr);
        }
    }
    @Override
    public int getOrder() {
        return SpringBeanOrderConstant.GATEWAY_TRACING;
    }
}
