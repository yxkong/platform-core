package com.github.platform.core.monitor.infra.websocket.interceptor;

import com.github.platform.core.auth.entity.LoginUserInfo;
import com.github.platform.core.auth.service.ITokenService;
import com.github.platform.core.cache.domain.constant.CacheConstant;
import com.github.platform.core.common.utils.CollectionUtil;
import com.github.platform.core.common.utils.JsonUtils;
import com.github.platform.core.common.utils.StringUtils;
import com.github.platform.core.monitor.domain.ws.OutMessage;
import com.github.platform.core.monitor.domain.constant.WsConstant;
import com.github.platform.core.standard.constant.ResultStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * 握手拦截器
 * @author: yxkong
 * @date: 2023/6/9 10:27 AM
 * @version: 1.0
 */
@Component
@Slf4j
public class WebSocketInterceptor implements HandshakeInterceptor {
    @Resource(name = CacheConstant.sysTokenService)
    private ITokenService tokenService;
    /**
     * 握手前
     * @param request
     * @param response
     * @param wsHandler
     * @param attributes
     * @return
     * @throws Exception
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        // 获取前端ws请求的 protocols 数据,即token
        List<String> tokens = request.getHeaders().get("Sec-WebSocket-Protocol");
        if(CollectionUtil.isEmpty(tokens)){
            WsConstant.response(response,HttpStatus.NOT_FOUND,WsConstant.tokenEmpty);
            return false;
        }
        // token校验
        String token = tokens.get(0);
        String loginStr = tokenService.getLoginInfoStr(token);
        //已经退出了
        if (StringUtils.isEmpty(loginStr)){
            if (log.isDebugEnabled()){
                log.debug("token:{} 已过期",token);
            }
            OutMessage wsMessage = OutMessage.builder()
                    .status(ResultStatusEnum.TOKEN_INVALID.getStatus())
                    .message(ResultStatusEnum.TOKEN_INVALID.getMessage())
                    .build();
            WsConstant.response(response,HttpStatus.UNAUTHORIZED, JsonUtils.toJson(wsMessage).getBytes(StandardCharsets.UTF_8));
            return false;
        }
        LoginUserInfo loginInfo = JsonUtils.fromJson(loginStr, LoginUserInfo.class);
        // 给WebSocketSession添加参数
        attributes.put("token",loginInfo.getToken());
        attributes.put("loginName",loginInfo.getLoginName());
        attributes.put("key",WsConstant.getKey(loginInfo.getLoginName(),loginInfo.getToken()));
        response.getHeaders().put("Sec-WebSocket-Protocol",tokens);
        return true;
    }



    /**
     * 握手后
     * @param request
     * @param response
     * @param wsHandler
     * @param exception
     */
    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
        if (log.isDebugEnabled()){
            //握手后成功后，可以自定义
        }
    }
}
