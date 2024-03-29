//package com.github.platform.core.gateway.filter;
//
//import com.alibaba.fastjson.JSON;
//import com.github.platform.core.auth.context.SecurityContextHolder;
//import com.github.platform.core.auth.entity.LoginUserInfo;
//import com.github.platform.core.auth.service.ITokenService;
//import com.github.platform.core.auth.util.AuthUtil;
//import com.github.platform.core.common.constant.SpringBeanOrderConstant;
//import com.github.platform.core.common.utils.StringUtils;
//import com.github.platform.core.auth.configuration.properties.AuthProperties;
//import com.github.platform.core.gateway.domain.gateway.ConfigGateway;
//import com.github.platform.core.gateway.utils.WebUtil;
//import com.github.platform.core.standard.constant.HeaderConstant;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.core.Ordered;
//import org.springframework.core.io.buffer.DataBuffer;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.http.server.reactive.ServerHttpResponse;
//import org.springframework.stereotype.Component;
//import org.springframework.util.Base64Utils;
//import org.springframework.util.CollectionUtil;
//import org.springframework.web.cors.reactive.CorsUtils;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//
//import javax.annotation.Resource;
//import java.nio.charset.StandardCharsets;
//import java.util.HashSet;
//import java.util.Objects;
//
///**
// * 全局权限拦截
// * @author: yxkong
// * @date: 2021/5/12 9:35 上午
// * @version: 1.0
// */
//@Component
//@Slf4j
//public class AuthGlobalFilter extends GatewayFilterBase implements GlobalFilter, Ordered {
//
//    @Resource
//    private ConfigGateway configGateway;
//
//    @Resource
//    private ITokenService tokenService;
//
//    @Resource
//    private AuthProperties authProperties;
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//
//        printHeader(exchange.getRequest());
//
//        /**
//         * 配置哪些接口不用鉴权直接转发
//         */
//        if (exclude(exchange)) {
//            //直接走转发逻辑
//            return chain.filter(corsConfig(exchange).mutate()
//                    .request(exchange.getRequest().mutate()
//                            .header(HeaderConstant.REQUEST_FROM, HeaderConstant.REQUEST_FROM_SOURCE)
//                            .build())
//                    .build()
//            );
//        }
//        // 网关登陆校验
//        String token = exchange.getRequest().getHeaders().getFirst(HeaderConstant.TOKEN);
//        if (StringUtils.isBlank(token)) {
//            return authFail(exchange, Boolean.TRUE);
//        }
//        String requestIp = WebUtil.getIpAddr(exchange.getRequest());
//        LoginUserInfo loginUserInfo = tokenService.getLoginUserInfo(token);
//        if (Objects.isNull(loginUserInfo)) {
//            log.error("用户token过期:{}", token);
//            return authFail(exchange, Boolean.TRUE);
//        }
//
//        //网关权限校验
//        if (CollectionUtil.isEmpty(loginUserInfo.getPerms())) {
//            loginUserInfo.setPerms(new HashSet<>());
//        }
//        loginUserInfo.getPerms().addAll(authProperties.getDefaultPerms());
//        SecurityContextHolder.setLoginUserInfo(loginUserInfo);
//        String perm = WebUtil.realPath(exchange.getRequest());
//        if (!AuthUtil.isAdmin() && !AuthUtil.hasPerms(loginUserInfo.getPerms(), perm)) {
//            log.info("{}权限验证失败,用户已有权限:{},需要权限:{}", loginUserInfo.getLoginName(), loginUserInfo.getPerms(), perm);
//            return authFail(exchange, Boolean.FALSE);
//        }
//
//        //防止权限列表太大超过header大小
//        loginUserInfo.setPerms(new HashSet<>());
//        ServerHttpRequest request = exchange.getRequest().mutate()
//                //将请求登录信息放入请求头
//                .header(HeaderConstant.LOGIN_INFO, Base64Utils.encodeToUrlSafeString(JSON.toJSONString(loginUserInfo).getBytes()))
//                .header(HeaderConstant.TOKEN, token)
//                //将用户的真实ip放入到请求头
//                .header(HeaderConstant.IP_HEADER_X_FORWARDED_FOR, requestIp)
//                //标记从网关过去的
//                .header(HeaderConstant.REQUEST_FROM, HeaderConstant.REQUEST_FROM_SOURCE)
//                //添加租户标记
//                .header(HeaderConstant.TENANT, loginUserInfo.getTenant())
//                .build();
//
//        return chain.filter(corsConfig(exchange).mutate().request(request).build());
//    }
//    /**
//     * 权限校验
//     *
//     * @param exchange
//     * @return 返回true，直接转发，返回false 拦截进行权限校验
//     */
//    private Boolean exclude(ServerWebExchange exchange) {
//        String host = exchange.getRequest().getURI().toString();
//        String url = exchange.getRequest().getURI().getPath();
//        /**
//         * 如果内网和外网用一个网关，可以根据内外网做鉴权
//         * 如果资本雄厚，就内外网分开
//         */
//
//        //判断是否有例外
//        if (configGateway.excludeHost(host) || configGateway.excludeUrl(url)) {
//            return Boolean.TRUE;
//        }
//        return Boolean.FALSE;
//    }
//
//
//
//    @Override
//    public int getOrder() {
//        return SpringBeanOrderConstant.AUTH_FILTER_ORDER;
//    }
//}