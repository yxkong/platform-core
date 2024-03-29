package com.github.platform.core.common.configuration.feign.customer;

import com.github.platform.core.standard.entity.dto.ResultBean;
import feign.HeaderMap;
import feign.QueryMap;
import feign.RequestLine;
import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.URI;
import java.util.Map;

/**
 * 平台自定义feign 客户端
 * @author: yxkong
 * @date: 2023/5/24 8:34 PM
 * @version: 1.0
 */
@FeignClient(name = "platformFeignClient")
public interface PlatformFeignClient {


    /**
     * get请求，返回Response
     * @param baseUri 请求url
     * @param headerMap  请求头
     * @param queryMap 请求参数
     * @return
     */
    @RequestLine("GET")
    Response get(URI baseUri, @HeaderMap Map<String, Object> headerMap, @QueryMap Map<String, Object> queryMap);

    /**
     * get请求，返回ResultBean
     * @param baseUri
     * @param headerMap
     * @param queryMap
     * @return
     */
    @RequestLine("GET")
    ResultBean getResultBean(URI baseUri, @HeaderMap Map<String, Object> headerMap, @QueryMap Map<String, Object> queryMap);
    /**
     * post请求Response
     * @param baseUri
     * @param headerMap
     * @param requestMap
     * @return
     */
    @RequestLine("POST")
    Response post(URI baseUri, @HeaderMap Map<String, Object> headerMap, @QueryMap Map<String, Object> requestMap);

    /**
     * post请求返回ResultBean
     * @param baseUri
     * @param headerMap
     * @param requestMap
     * @return
     */
    @RequestLine("POST")
    ResultBean postResultBean(URI baseUri, @HeaderMap Map<String, Object> headerMap, @QueryMap Map<String, Object> requestMap);
    /**
     * restful请求返回Response
     * @param baseUri
     * @param headerMap
     * @param requestBody
     * @return
     */
    @RequestLine("POST")
    Response restful(URI baseUri, @HeaderMap Map<String, Object> headerMap, @RequestBody Map<String, Object> requestBody);
    /**
     * restful请求返回ResultBean
     * @param baseUri
     * @param headerMap
     * @param requestBody
     * @return
     */
    @RequestLine("POST")
    ResultBean restfulResultBean(URI baseUri, @HeaderMap Map<String, Object> headerMap, @RequestBody Map<String, Object> requestBody);
}
