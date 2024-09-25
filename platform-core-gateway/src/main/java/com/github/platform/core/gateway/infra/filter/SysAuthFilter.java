package com.github.platform.core.gateway.infra.filter;

import com.github.platform.core.auth.configuration.properties.AuthProperties;
import com.github.platform.core.auth.entity.LoginUserInfo;
import com.github.platform.core.auth.service.IGatewayTokenService;
import com.github.platform.core.auth.util.AuthUtil;
import com.github.platform.core.auth.util.LoginUserInfoUtil;
import com.github.platform.core.common.utils.CollectionUtil;
import com.github.platform.core.common.utils.JsonUtils;
import com.github.platform.core.common.utils.StringUtils;
import com.github.platform.core.gateway.domain.gateway.IConfigGateway;
import com.github.platform.core.gateway.infra.utils.WebUtil;
import com.github.platform.core.standard.constant.HeaderConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.Base64Utils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.Objects;

/**
 * 后端管理系统权限校验
 *  要先重写完url再进来，不能添加order排序，由数据库配置控制排序
 *  实现：
 *  1，鉴权
 *  2，信息透传
 * @author: yxkong
 * @date: 2023/4/20 10:35 AM
 * @version: 1.0
 */
@Slf4j
public class SysAuthFilter extends GatewayFilterBase implements GatewayFilter {

    private IConfigGateway configGateway;
    private IGatewayTokenService tokenService;
    private AuthProperties authProperties;
    public SysAuthFilter(IConfigGateway configGateway, IGatewayTokenService tokenService, AuthProperties authProperties) {
        this.configGateway = configGateway;
        this.tokenService = tokenService;
        this.authProperties = authProperties;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        AuthProperties.Sys sys = authProperties.getSys();
        String path = exchange.getRequest().getURI().getPath();
        printHeader(exchange.getRequest());
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
            if (log.isDebugEnabled()) {
                log.debug("path:{} token is null",path);
            }
            return authFail(exchange, Boolean.TRUE);
        }

        String loginInfoStr = tokenService.getLoginInfoStr(token);
        if (Objects.isNull(loginInfoStr)) {
            log.error("path:{} token {} 已过期",path,token);
            return authFail(exchange, Boolean.TRUE);
        }
        if (log.isDebugEnabled()) {
            log.debug("path:{} token {} loginInfoStr:{}",path,token,loginInfoStr);
        }
        LoginUserInfo loginUserInfo = JsonUtils.fromJson(loginInfoStr,LoginUserInfo.class);
        //网关权限校验
        if (CollectionUtil.isEmpty(loginUserInfo.getPerms())) {
            loginUserInfo.setPerms(new HashSet<>());
        }
        loginUserInfo.getPerms().addAll(sys.getDefaultPerms());
        LoginUserInfoUtil.setLoginUserInfo(loginUserInfo);
        String perm = WebUtil.realPath(exchange.getRequest());
        if (!AuthUtil.isSuperAdmin() && !AuthUtil.hasPerms(loginUserInfo.getPerms(), perm)) {
            log.info("{}权限验证失败,用户已有权限:{},需要权限:{}", loginUserInfo.getLoginName(), loginUserInfo.getPerms(), perm);
            return authFail(exchange, Boolean.FALSE);
        }
        //此处是内网传输，一般不会太大
//        loginUserInfo.setPerms(new HashSet<>());
        String requestIp = WebUtil.getIpAddr(exchange.getRequest());
        ServerHttpRequest request = exchange.getRequest().mutate()
                //将请求登录信息放入请求头
                .header(HeaderConstant.LOGIN_INFO, Base64Utils.encodeToUrlSafeString(JsonUtils.toJson(loginUserInfo).getBytes()))
                .header(HeaderConstant.TOKEN, token)
                //将用户的真实ip放入到请求头
                .header(HeaderConstant.IP_HEADER_X_FORWARDED_FOR, requestIp)
                //标记从网关过去的
                .header(HeaderConstant.REQUEST_FROM, HeaderConstant.REQUEST_FROM_SOURCE)
                //添加租户标记
                .header(HeaderConstant.TENANT_ID, loginUserInfo.getTenantId()+"")
                .build();
        LoginUserInfoUtil.clearContext();
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
            if (log.isDebugEnabled()){
                log.debug("host:{}  url:{} 不走鉴权 ",host,url);
            }
            return Boolean.TRUE;
        }
        if (log.isDebugEnabled()){
            log.debug("host:{}  url:{} 走鉴权 ",host,url);
        }
        return Boolean.FALSE;
    }
}
