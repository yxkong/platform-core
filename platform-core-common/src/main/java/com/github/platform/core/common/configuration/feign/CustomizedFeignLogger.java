package com.github.platform.core.common.configuration.feign;

import com.github.platform.core.common.utils.StringUtils;
import feign.Feign;
import feign.Request;
import feign.Response;
import feign.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static feign.Util.*;

/**
 * 自定义feignLogger
 *
 * @author yxkong
 * @date 2019/7/16-17:37
 */
@FeignLog
public class CustomizedFeignLogger extends feign.Logger {

    private static final Map<String, FeignLogType> REQUEST_FEIGN_LOG_MAP = new HashMap<>();
    private static final Map<String, FeignLogType> RESPONSE_FEIGN_LOG_MAP = new HashMap<>();
    private final String FEIGN_ID = "feignId";
    private final Logger logger;
    private FeignLog classFeignLog;

    CustomizedFeignLogger(Class<?> type) {
        // 使用type作为日志记录器
        logger = LoggerFactory.getLogger(type);

        classFeignLog = type.getAnnotation(FeignLog.class);
        if (Objects.isNull(classFeignLog)) {
            classFeignLog = CustomizedFeignLogger.class.getAnnotation(FeignLog.class);
        }

        for (Method method : type.getMethods()) {
            String configKey = Feign.configKey(type, method);
            String keyWord = "";
            FeignLog.FeignLogType requestFeignLogType;
            FeignLog.FeignLogType responseFeignLogType;
            // 获取方法上的注解
            FeignLog methodFeignLog = method.getAnnotation(FeignLog.class);
            if (Objects.isNull(methodFeignLog)) {
                requestFeignLogType = classFeignLog.request();
                responseFeignLogType = classFeignLog.response();
            } else {
                keyWord = methodFeignLog.keyWord();
                requestFeignLogType = methodFeignLog.request();
                responseFeignLogType = methodFeignLog.response();
            }
            REQUEST_FEIGN_LOG_MAP.put(configKey, new FeignLogType(requestFeignLogType, keyWord));
            RESPONSE_FEIGN_LOG_MAP.put(configKey, new FeignLogType(responseFeignLogType, keyWord));
        }
    }

    @Override
    protected void logRequest(String configKey, Level logLevel, Request request) {
        FeignLogType feignLogType = REQUEST_FEIGN_LOG_MAP.get(configKey);
        if (!feignLogType.isRecordFlag()) {
            return;
        }
        if (logger.isInfoEnabled()) {
            MDC.remove(FEIGN_ID);
            // 增加日志标志
            MDC.put(FEIGN_ID, StringUtils.uuidRmLine());
            // 记录请求日志
            StringBuilder loggerStr = new StringBuilder(String.format("feign请求,%s,%s,url:%s;", feignLogType.getKeyWord(), configKey, request.url()));
            if (feignLogType.isHeadersFlag()) {
                StringBuilder headersStr = new StringBuilder();
                Set<String> excludeHeaders = feignLogType.getExcludeHeaders();
                Map<String, Collection<String>> headers = request.headers();
                for (String field : headers.keySet()) {
                    for (String value : valuesOrEmpty(headers, field)) {
                        if (!excludeHeaders.contains(StringUtils.lowerCase(field))) {
                            headersStr.append(field).append(":").append(value).append(";");
                        }
                    }
                }
                loggerStr.append(String.format("headers:[%s];", headersStr.toString()));
            }
            if (feignLogType.isBodyFlag()) {
                StringBuilder bodyStr = new StringBuilder();
                byte[] body = request.body();
                Charset charset = request.charset();
                if (body != null) {
                    bodyStr.append(charset != null ? new String(body, charset) : "二进制数据");
                }
                loggerStr.append(String.format("body:%s", bodyStr.toString()));
            }
            logger.info(loggerStr.toString());
        }
    }

    @Override
    protected Response logAndRebufferResponse(String configKey, Level logLevel, Response response, long elapsedTime)
            throws IOException {
        String protocolVersion = resolveProtocolVersion(response.protocolVersion());
        // 统一读取body，并改成可重复读
        byte[] bodyData = null;
        if (response.body() != null && isBodyAllowed(response.status())){
            bodyData = Util.toByteArray(response.body().asInputStream());
        }
        // 将Body数据回写，并改成可重复读
        if (bodyData != null) {
            response = response.toBuilder().body(bodyData).build();
        }
        // 如果未开启日志记录或者日志级别不为INFO，直接返回
        if (!classFeignLog.value() || !logger.isInfoEnabled()) {
            return response;
        }

        FeignLogType feignLogType = RESPONSE_FEIGN_LOG_MAP.get(configKey);
        if (!feignLogType.isRecordFlag()) {
            return response;
        }
        // 构造基本日志信息
        String reason = buildReason(logLevel, response);
        StringBuilder loggerStr = buildBaseLog(configKey, protocolVersion, response, elapsedTime, reason, feignLogType);

        // 记录Headers信息
        if (feignLogType.isHeadersFlag()) {
            appendHeaders(loggerStr, response, feignLogType.getExcludeHeaders());
        }

        if (feignLogType.isBodyFlag()) {
            appendBody(loggerStr, bodyData);
        }
        // 打印日志
        logger.info("{}耗时:{}ms;reason:{}", loggerStr, elapsedTime, reason);



        MDC.remove(FEIGN_ID);
        return response;
    }

    private String buildReason(Level logLevel, Response response) {
        return response.reason() != null && logLevel.compareTo(Level.NONE) > 0 ? " " + response.reason() : "";
    }

    private StringBuilder buildBaseLog(String configKey, String protocolVersion, Response response, long elapsedTime,
                                       String reason, FeignLogType feignLogType) {
        return new StringBuilder(String.format("feign响应,%s,%s,%s %s %s,%s,耗时:%sms;",
                feignLogType.getKeyWord(), configKey, protocolVersion, response.status(), reason,
                response.request().url(), elapsedTime));
    }

    private void appendHeaders(StringBuilder loggerStr, Response response, Set<String> excludeHeaders) {
        StringBuilder headersStr = new StringBuilder();
        for (String field : response.headers().keySet()) {
            for (String value : valuesOrEmpty(response.headers(), field)) {
                if (!excludeHeaders.contains(field)) {
                    headersStr.append(field).append(":").append(value).append(";");
                }
            }
        }
        loggerStr.append(String.format("headers:[%s];", headersStr));
    }

    private void appendBody(StringBuilder loggerStr, byte[] bodyData) {
        if (bodyData != null && bodyData.length > 0) {
            String bodyContent = decodeOrDefault(bodyData, UTF_8, "二进制数据");
            loggerStr.append(String.format("body:%s;", bodyContent));
        }
    }

    private boolean isBodyAllowed(int status) {
        // 判断是否允许包含Body
        return !(status == 204 || status == 205);
    }


    @Override
    protected IOException logIOException(String configKey, Level logLevel, IOException ioe, long elapsedTime) {
        IOException ioException = super.logIOException(configKey, logLevel, ioe, elapsedTime);
        MDC.remove(FEIGN_ID);
        return ioException;
    }

    @Override
    protected void log(String s, String s1, Object... objects) {

    }
}
