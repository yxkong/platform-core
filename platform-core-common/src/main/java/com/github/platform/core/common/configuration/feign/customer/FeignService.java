package com.github.platform.core.common.configuration.feign.customer;

import com.github.platform.core.common.constant.RequestTypeEnum;
import com.github.platform.core.standard.entity.dto.ResultBean;
import com.github.platform.core.standard.util.ResultBeanUtil;
import feign.Feign;
import feign.Logger;
import feign.Response;
import feign.Target;
import feign.codec.Decoder;
import feign.codec.Encoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.cloud.openfeign.FeignLoggerFactory;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 *
 * @author: yxkong
 * @date: 2023/5/24 8:31 PM
 * @version: 1.0
 */
@Component
@Import(FeignClientsConfiguration.class)
@Slf4j
public class FeignService {
    private PlatformFeignClient feignClient;
    private String url;
    private RequestTypeEnum requestType;
    private Map<String, Object> header;
    private Map<String, Object> params;

    /**
     * 在构造函数中用 @Autowired 引入自定义参数和编解码器
     * @param encoder
     * @param decoder
     */
    @Autowired
    public FeignService(FeignLoggerFactory loggerFactory, Encoder encoder, Decoder decoder) {
        feignClient = Feign.builder()
                .logger(loggerFactory.create(PlatformFeignClient.class))
                .encoder(encoder).decoder(decoder)
                .target(Target.EmptyTarget.create(PlatformFeignClient.class));
    }
    public FeignService url(String url) {
        if (url == null) {
            throw new RuntimeException("uri == null");
        } else {
            this.url = url;
        }
        return this;
    }
    @Deprecated
    public FeignService request(Map<String, Object> request) {
        this.params = request;
        return this;
    }
    public FeignService params(Map<String, Object> params) {
        this.params = params;
        return this;
    }
    public FeignService header(Map<String, Object> header) {
        this.header = header;
        return this;
    }
    public FeignService requestType(RequestTypeEnum requestType) {
        this.requestType = requestType;
        return this;
    }
    public FeignService get() {
        this.requestType = RequestTypeEnum.GET;
        return this;
    }
    public FeignService post() {
        this.requestType = RequestTypeEnum.POST;
        return this;
    }
    public FeignService restful() {
        this.requestType = RequestTypeEnum.RESTFUL;
        return this;
    }
    private void before(){
        if (Objects.isNull(this.header)){
            this.header = new LinkedHashMap<>();
        }
        if (Objects.isNull(this.params)){
            this.params = new LinkedHashMap<>();
        }
        if (Objects.isNull(requestType)){
            this.requestType = RequestTypeEnum.RESTFUL;
        }
    }

    /**
     * 执行远程调用，返回ResultBean
     * @return
     */
    public ResultBean resultBean(){
        this.before();
        switch (requestType) {
            case GET:
                return feignClient.getResultBean(getUri(),this.header,this.params);
            case POST:
                return feignClient.postResultBean(getUri(),this.header,this.params);
            case RESTFUL:
                return feignClient.restfulResultBean(getUri(), this.header, this.params);
            default:
                String msg = String.format("未找到请求类型：%s 对应的实现",requestType );
                return ResultBeanUtil.fail(msg);
        }
    }

    /**
     * 执行调用方法返回Response
     * @return
     */
    public Response execute() {
        this.before();
        switch (requestType) {
            case GET:
                return feignClient.get(getUri(),this.header,this.params);
            case POST:
                return feignClient.post(getUri(),this.header,this.params);
            case RESTFUL:
                return feignClient.restful(getUri(), this.header, this.params);
            default:
                throw new RuntimeException("");
        }
    }
    private URI getUri(){
        try {
            return new URI(this.url);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

}
