package com.github.platform.core.web.configuration;

import com.github.platform.core.loadbalancer.interceptor.GrayClientHttpRequestInterceptor;
import com.github.platform.core.loadbalancer.interceptor.GrayRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

/**
 * 灰度注入
 * @author: yxkong
 * @date: 2023/2/28 1:31 PM
 * @version: 1.0
 */
@Configuration
public class PublicAutoConfiguration {
    private static final Integer DEFAULT_READ_TIMEOUT = 3000;
    private static final Integer DEFAULT_CONNECT_TIMEOUT = 2000;
    @Bean
    public ClientHttpRequestFactory simpleClientHttpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        // 单位为ms
        factory.setReadTimeout(DEFAULT_READ_TIMEOUT);
        // 单位为ms
        factory.setConnectTimeout(DEFAULT_CONNECT_TIMEOUT);
        return factory;
    }
    @Bean
    public RestTemplate restTemplate(ClientHttpRequestFactory factory) {
        RestTemplate restTemplate = new RestTemplate(factory);
        restTemplate.getInterceptors().add(new GrayClientHttpRequestInterceptor());
        restTemplate.getInterceptors().add(new GzipDecompressingInterceptor());
        return restTemplate;
    }
    @Bean
    public GrayRequestInterceptor grayRequestInterceptor(){
        return new GrayRequestInterceptor();
    }
    /**
     * 自定义拦截器，用于处理 GZIP 响应解压。
     */
    static class GzipDecompressingInterceptor implements ClientHttpRequestInterceptor {
        @Override
        public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
            // 执行请求
            ClientHttpResponse response = execution.execute(request, body);

            // 检查响应头是否包含 GZIP 编码
            HttpHeaders headers = response.getHeaders();
            if ("gzip".equalsIgnoreCase(headers.getFirst(HttpHeaders.CONTENT_ENCODING))) {
                return new GzipDecompressingResponse(response);
            }

            return response;
        }
    }

    /**
     * 自定义响应包装器，用于解压 GZIP 数据。
     */
    static class GzipDecompressingResponse implements ClientHttpResponse {

        private final ClientHttpResponse response;

        public GzipDecompressingResponse(ClientHttpResponse response) {
            this.response = response;
        }

        @Override
        public HttpHeaders getHeaders() {
            HttpHeaders headers = response.getHeaders();
            // 移除 GZIP 编码头
            headers.remove(HttpHeaders.CONTENT_ENCODING);
            return headers;
        }

        @Override
        public InputStream getBody() throws IOException {
            // 返回解压后的输入流
            return new GZIPInputStream(response.getBody());
        }

        @Override
        public HttpStatus getStatusCode() throws IOException {
            return response.getStatusCode();
        }

        @Override
        public int getRawStatusCode() throws IOException {
            return response.getRawStatusCode();
        }

        @Override
        public String getStatusText() throws IOException {
            return response.getStatusText();
        }

        @Override
        public void close() {
            response.close();
        }
    }
}
