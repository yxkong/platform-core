package com.github.platform.core.common.aspect;

import com.github.platform.core.common.utils.CollectionUtil;
import com.github.platform.core.common.utils.StringUtils;
import org.slf4j.Logger;

import java.util.Map;

/**
 * 全局通用异常，和用户无关，主要是捕获以后，将异常包装成对应层面的异常
 * @author: yxkong
 * @date: 2023/8/3 2:34 PM
 * @version: 1.0
 */
public abstract class ExceptionHandlerBase {

    /**
     * 日志输出
     * @param exceptionName
     * @param token
     * @param uri
     * @param method
     * @param headers
     * @param requestBody
     * @return
     */
    protected String log(String exceptionName, String token, String uri, String method, Map<String,String> headers, String requestBody, String exceptionStr){
        StringBuffer sb = new StringBuffer();
        if (StringUtils.isNotEmpty(token)){
            sb.append("用户token：").append(token).append(",");
        }
        if (StringUtils.isNotEmpty(uri)){
            sb.append("uri：").append(uri).append(",");
        }
        if (StringUtils.isNotEmpty(method)){
            sb.append("method：").append(method).append(",");
        }
        if (CollectionUtil.isNotEmpty(headers)){
            sb.append("headers：").append(headers).append(",");
        }
        if (StringUtils.isNotEmpty(requestBody)){
            sb.append("requestBody：").append(requestBody).append(",");
        }
        sb.append(" 统一拦截到").append(exceptionName).append("异常:");
        if (StringUtils.isNotEmpty(exceptionStr)){
            sb.append(exceptionStr);
        }
        return sb.toString();
    }
}
