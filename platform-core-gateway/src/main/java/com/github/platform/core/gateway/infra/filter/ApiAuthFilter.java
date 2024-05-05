package com.github.platform.core.gateway.infra.filter;

import com.github.platform.core.auth.service.ITokenService;
import com.github.platform.core.auth.util.LoginInfoUtil;
import com.github.platform.core.common.constant.SpringBeanOrderConstant;
import com.github.platform.core.common.utils.JsonUtils;
import com.github.platform.core.common.utils.StringUtils;
import com.github.platform.core.gateway.domain.gateway.ConfigGateway;
import com.github.platform.core.gateway.infra.utils.WebUtil;
import com.github.platform.core.standard.constant.HeaderConstant;
import com.github.platform.core.standard.entity.common.LoginInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.Base64Utils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * c端api权限校验
 *  实现：
 *  1，鉴权
 *  2，将用户的登录信息后传
 * @author: yxkong
 * @date: 2023/4/20 10:35 AM
 * @version: 1.0
 */
@Slf4j
public class ApiAuthFilter extends GatewayFilterBase implements GatewayFilter, Ordered {

    private ConfigGateway configGateway;

    private ITokenService tokenService;
    public ApiAuthFilter(ConfigGateway configGateway, ITokenService tokenService) {
        this.configGateway = configGateway;
        this.tokenService = tokenService;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        printHeader(exchange.getRequest());
        String path = exchange.getRequest().getURI().getPath();
        /**
         * 配置哪些接口不用鉴权直接转发
         */
        if (exclude(exchange)) {
            //直接走转发逻辑
            return chain.filter(corsConfig(exchange).mutate()
                    .request(exchange.getRequest().mutate()
                            .header(HeaderConstant.REQUEST_FROM, HeaderConstant.REQUEST_FROM_SOURCE)
                            .build())
                    .build()
            );
        }
        // 网关登陆校验
        String token = exchange.getRequest().getHeaders().getFirst(HeaderConstant.TOKEN);
        if (StringUtils.isBlank(token)) {
            return authFail(exchange, Boolean.TRUE);
        }
        String requestIp = WebUtil.getIpAddr(exchange.getRequest());
        String loginInfoStr = tokenService.getLoginInfoStr(token);
        if (Objects.isNull(loginInfoStr)) {
            log.error("path:{} token {} 已过期",path,token);
            return authFail(exchange, Boolean.TRUE);
        }
        LoginInfo loginInfo = JsonUtils.fromJson(loginInfoStr,LoginInfo.class);
        LoginInfoUtil.setLoginInfo(loginInfo);
        ServerHttpRequest request = exchange.getRequest().mutate()
                //将请求登录信息放入请求头
                .header(HeaderConstant.LOGIN_INFO, Base64Utils.encodeToUrlSafeString(JsonUtils.toJson(loginInfo).getBytes()))
                .header(HeaderConstant.TOKEN, token)
                //将用户的真实ip放入到请求头
                .header(HeaderConstant.IP_HEADER_X_FORWARDED_FOR, requestIp)
                //标记从网关过去的
                .header(HeaderConstant.REQUEST_FROM, HeaderConstant.REQUEST_FROM_SOURCE)
                //添加租户标记
                .header(HeaderConstant.TENANT_ID, loginInfo.getTenantId()+"")
                .build();
        LoginInfoUtil.clearContext();
        return chain.filter(corsConfig(exchange).mutate().request(request).build());
    }

    /**
     * 权限校验
     *
     * @param exchange
     * @return 返回true，直接转发，返回false 拦截进行权限校验
     */
    private Boolean exclude(ServerWebExchange exchange) {
        String host = exchange.getRequest().getURI().toString();
        String url = exchange.getRequest().getURI().getPath();
        /**
         * 如果内网和外网用一个网关，可以根据内外网做鉴权
         * 如果资本雄厚，就内外网分开
         */

        //判断是否有例外
        if (configGateway.excludeHost(host) || configGateway.excludeUrl(url)) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    @Override
    public int getOrder() {
        return SpringBeanOrderConstant.GATEWAY_AUTH_API;
    }
}
