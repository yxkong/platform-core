package com.github.platform.core.monitor.domain.constant;

import com.github.platform.core.standard.constant.SymbolConstant;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicLong;

/**

 * @author: yxkong
 * @date: 2023/6/9 10:53 AM
 * @version: 1.0
 */
public class WsConstant {
    /**删除的时候，保留数据数，默认30分钟*/
    public static final Long retainMins = 30 * 60 * 1000L;
    /***/
    public static final Long resetCounter = Long.MAX_VALUE >> 1;
    public static final String TO_ALL = "all";
    /**消息类型：登出 logout*/
    public static final String BIZ_TYPE_LOGOUT = "wsLogout";

    /**消息类型：聊天 chat*/
    public static final String BIZ_TYPE_CHAT = "wsChat";
    /**广播*/
    public static final String MSG_TYPE_BROADCAST = "broadcast";
    /**点对点*/
    public static final String MSG_TYPE_P2P = "p2p";

    public static final String ROBOT = "robot";
    /**内存计数器*/
    public static AtomicLong counter = new AtomicLong(0);
    /**清理周期*/
    public static final Integer period = 1000;


    public static final byte[] tokenExpire = "{\"status\":\"1008\",\"message\":\"token无效,请重新登录\"}".getBytes(StandardCharsets.UTF_8);
    public static final byte[] tokenEmpty = "{\"status\":\"1001\",\"message\":\"Sec-WebSocket-Protocol参数为空\"}".getBytes(StandardCharsets.UTF_8);

    public static void response(ServerHttpResponse response, HttpStatus status, byte[] data) throws IOException {
        response.setStatusCode(status);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        response.getHeaders().set(HttpHeaders.CONTENT_ENCODING, StandardCharsets.UTF_8.name());
        response.getBody().write(data);
        response.getBody().flush();
    }
    public static String getKey(String loginName,String token){
        return loginName + SymbolConstant.colon + token;
    }
}
