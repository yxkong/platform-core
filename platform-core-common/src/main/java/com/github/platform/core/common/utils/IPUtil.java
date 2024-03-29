package com.github.platform.core.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 获取本机ip工具类
 *
 * @author: yxkong
 * @date: 2022/12/5 3:03 PM
 * @version: 1.0
 */
public class IPUtil {
    private static final Logger logger = LoggerFactory.getLogger(IPUtil.class);

    private static long PIP_VALID_TIME = TimeUnit.MINUTES.toMillis(10);


    /**
     * 获取本机ip
     *
     * @return
     */
    public static String getLocalHostAddress() {
        final InetAddress inetAddress = getLocalHostLanAddress();
        if (Objects.nonNull(inetAddress)) {
            return inetAddress.getHostAddress();
        }
        return null;
    }

    public static InetAddress getLocalHostLanAddress() {
        try {
            InetAddress candidateAddress = null;
            // 遍历所有的网络接口
            for (Enumeration<?> ifaces = NetworkInterface.getNetworkInterfaces(); ifaces.hasMoreElements(); ) {
                NetworkInterface iface = (NetworkInterface) ifaces.nextElement();
                // 在所有的接口下再遍历IP
                for (Enumeration<?> inetAddrs = iface.getInetAddresses(); inetAddrs.hasMoreElements(); ) {
                    InetAddress inetAddr = (InetAddress) inetAddrs.nextElement();
                    // 排除loopback类型地址
                    if (!inetAddr.isLoopbackAddress()) {
                        if (inetAddr.isSiteLocalAddress()) {
                            // 如果是site-local地址，就是它了
                            return inetAddr;
                        } else if (candidateAddress == null) {
                            // site-local类型的地址未被发现，先记录候选地址
                            candidateAddress = inetAddr;
                        }
                    }
                }
            }
            if (candidateAddress != null) {
                return candidateAddress;
            }
            // 如果没有发现 non-loopback地址.只能用最次选的方案
            InetAddress jdkSuppliedAddress = InetAddress.getLocalHost();
            return jdkSuppliedAddress;
        } catch (Exception e) {

        }
        return null;
    }
}
