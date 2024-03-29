package com.github.platform.core.gateway.infra.utils;

import com.github.platform.core.common.utils.StringUtils;
import com.github.platform.core.standard.constant.HeaderConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.net.InetSocketAddress;

/**
 * @author: yxkong
 * @date: 2021/5/12 12:19 下午
 * @version: 1.0
 */
public class WebUtil {
    private static final Logger logger = LoggerFactory.getLogger(WebUtil.class);

    private static final String IPV4_DEFAULT_LOCAL = "127.0.0.1";
    private static final String IPV6_DEFAULT_LOCAL = "0:0:0:0:0:0:0:1";
    private static final String SPLIT = ",";
    private static final String SPLIT_URL = "/";
    private static final Integer PARTS = 1;


    /**
     * 获取客户端的请求ip
     *
     * @param request
     * @return
     */
    public static String getIpAddr(ServerHttpRequest request) {
        /**
         * 在转发的时候，将原客户的请求ip放入到header中 字段reuestSourceIp
         * 如果有该reuestSourceIp，直接使用这个ip，（慎用）
         */
        try {
            HttpHeaders httpHeaders = request.getHeaders();
            String requestIp = httpHeaders.getFirst(HeaderConstant.IP_HEADER_REQUEST_SOURCE_IP);
            if (!StringUtils.isEmpty(requestIp)) {
                if (requestIp.contains(SPLIT)) {
                    return requestIp.split(SPLIT)[0];
                }
                return requestIp;
            }
            String ip = httpHeaders.getFirst(HeaderConstant.IP_HEADER_X_ORIGINAL_FORWARDED_FOR);
            if (StringUtils.isEmpty(ip) || HeaderConstant.IP_UNKNOWN.equalsIgnoreCase(ip)) {
                ip = httpHeaders.getFirst(HeaderConstant.IP_HEADER_X_FORWARDED_FOR);
            }
            if (StringUtils.isEmpty(ip) || HeaderConstant.IP_UNKNOWN.equalsIgnoreCase(ip)) {
                ip = httpHeaders.getFirst(HeaderConstant.IP_HEADER_PROXY_CLIENT_IP);
            }
            if (StringUtils.isEmpty(ip) || HeaderConstant.IP_UNKNOWN.equalsIgnoreCase(ip)) {
                ip = httpHeaders.getFirst(HeaderConstant.IP_HEADER_WL_PROXY_CLIENT_IP);
            }
            if (StringUtils.isEmpty(ip) || HeaderConstant.IP_UNKNOWN.equalsIgnoreCase(ip)) {
                InetSocketAddress inetSocketAddress = request.getRemoteAddress();
                ip = inetSocketAddress.getHostString();
            }
            if (StringUtils.isEmpty(ip)) {
                return IPV6_DEFAULT_LOCAL;
            }
            return ip;
        } catch (Exception e) {
            logger.error("[网关][getIpAddr]发生异常", e);
        }
        return IPV6_DEFAULT_LOCAL;
    }

    public static String realPath(ServerHttpRequest request, Integer parts) {
        String path = request.getURI().getPath();
        String[] originalParts = org.springframework.util.StringUtils.tokenizeToStringArray(path, SPLIT_URL);
        StringBuilder newPath = new StringBuilder();
        for (int i = 0; i < originalParts.length; i++) {
            if (i >= parts) {
                // only append slash if this is the second part or greater
                if (newPath.length() > 1) {
                    newPath.append(SPLIT_URL);
                }
                newPath.append(originalParts[i]);
            }
        }
        if (newPath.length() > 1 && path.endsWith(SPLIT_URL)) {
            newPath.append(SPLIT_URL);
        }
        return newPath.toString();
    }

    public static String realPath(ServerHttpRequest request) {
        String path = request.getURI().getPath();
        if (StringUtils.isNotEmpty(path) && path.startsWith(SPLIT_URL)){
            return path.substring(1);
        }
        return path;
    }
}