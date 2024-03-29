package com.github.platform.core.gateway.domain.gateway;

import java.util.List;

/**
 * 配置网关
 *
 * @author: yxkong
 * @date: 2021/12/6 11:47 AM
 * @version: 1.0
 */
public interface ConfigGateway {
    /**
     * 判断host是否在放行列表里，用于后端请求时，只是添加白名单放行
     * @param hosts
     * @param host
     * @return
     */
    default boolean hostEquals(List<String> hosts,String host){
        if(hosts!= null
                && hosts.stream().anyMatch(s -> host.equals(s))){
            return Boolean.TRUE;
        }
        return  Boolean.FALSE;
    }

    /**
     * 解决前缀的问题
     * @param hosts
     * @param url
     * @return
     */
    default boolean urlEndWith(List<String> hosts,String url){
        if(hosts!= null
                && hosts.stream().anyMatch(s -> url.endsWith(s))){
            return Boolean.TRUE;
        }
        return  Boolean.FALSE;
    }
    /**
     * 判断请求的url是否在放行列表里
     * @param urls
     * @param url
     * @return
     */
    default boolean urlStartWith(List<String> urls,String url){
        if(urls != null
                && urls.stream().anyMatch(s -> url.startsWith(s))){
            return Boolean.TRUE;
        }
        return  Boolean.FALSE;
    }

    /**
     * 哪些host不能走网关
     * @param host
     * @return 走网关返回true，不走，返回false
     */
    boolean excludeHost(String host);

    /**
     * 哪些url不能走网关
     * @param url
     * @return 走网关返回true，不走，返回false
     */
    boolean excludeUrl(String url);
}
