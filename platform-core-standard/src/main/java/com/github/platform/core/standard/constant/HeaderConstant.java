package com.github.platform.core.standard.constant;

/**
 * 请求头常量
 *
 * @author: yxkong
 * @date: 2021/5/12 2:31 下午
 * @version: 1.0
 */
public interface HeaderConstant {
    /**header中的token，如果不需要登录，默认值为token*/
    String TOKEN = "token";
    String ACCESS_TOKEN = "accessToken";
    /**header中的默认token*/
    String DEFAULT_TOKEN = "token";
    /**header中的系统名称*/
    String SYS = "x-sys";
    /**header中的接口版本号*/
    String VERSION = "x-version";
    /**header中的租户信息*/
    String TENANT_ID = "x-tenant-id";
    /**header中解析出来的用户登录信息，经过网关以后必须携带的信息*/
    String LOGIN_INFO = "x-login-info";
    /**header中的source来源*/
    String SOURCE = "x-source";
    /**header中的时间戳*/
    String T = "x-t";
    /**header中的tag，用于后续的灰度*/
    String TAG = "x-tag";
    /**header中的页面启动的uuid*/
    String PAGE_UUID = "x-page-uuid";
    /**header中的app启动的uuid*/
    String APP_UUID = "x-app-uuid";

    /**网关来源关键字*/
    String REQUEST_FROM = "gateway";
    String REQUEST_FROM_SOURCE = "gateway";
    String IP_UNKNOWN = "unknown";
    String IPV4_DEFAULT_LOCAL = "127.0.0.1";
    String IPV6_DEFAULT_LOCAL = "0:0:0:0:0:0:0:1";
    String IP_HEADER_REQUEST_SOURCE_IP = "requestSourceIp";
    String IP_HEADER_X_REQUESTED_FOR = "X-requested-For";
    String IP_HEADER_X_FORWARDED_FOR = "x-forwarded-for";
    String IP_HEADER_HTTP_X_FORWARDED_FOR = "http_x-forwarded-for";
    String IP_HEADER_WL_PROXY_CLIENT_IP = "wl-proxy-client-iP";
    String IP_HEADER_PROXY_CLIENT_IP = "proxy-client-ip";
    String IP_HEADER_HTTP_CLIENT_IP = "http_client_ip";
    /**
     * ingress
     */
    String IP_HEADER_X_ORIGINAL_FORWARDED_FOR = "x-original-forwarded-for";
    String CONTENT_TYPE = "Content-Type";

    String CONTENT_TYPE_JSON = "application/json";
    String GZIP = "gzip";
}