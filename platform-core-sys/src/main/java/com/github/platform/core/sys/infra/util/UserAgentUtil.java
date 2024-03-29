package com.github.platform.core.sys.infra.util;

import com.github.platform.core.web.util.RequestHolder;
import eu.bitwalker.useragentutils.UserAgent;
import org.apache.commons.lang3.tuple.Pair;

import javax.servlet.http.HttpServletRequest;

/**
 * UserAgent 工具类
 * @author: yxkong
 * @date: 2023/6/8 5:42 PM
 * @version: 1.0
 */
public class UserAgentUtil {

    public static Pair<String,String> getOsAndBrowser(){
        HttpServletRequest request = RequestHolder.getRequest();
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        // 获取客户端操作系统
        String os = userAgent.getOperatingSystem().getName();
        // 获取客户端浏览器
        String browser = userAgent.getBrowser().getName();
        return Pair.of(os,browser);
    }
}
