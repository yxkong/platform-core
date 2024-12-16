package com.github.platform.core.sys.infra.configuration;

import com.github.platform.core.common.constant.PropertyConstant;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * ldap配置
 * @author: yxkong
 * @date: 2023/5/26 3:05 PM
 * @version: 1.0
 */
@Data
@Configuration
@ConfigurationProperties(prefix = PropertyConstant.DATA_LDAP)
public class LdapProperties {
    public final static String facotry = "com.sun.jndi.ldap.LdapCtxFactory";
    public final static String securityAuthentication = "simple";
    /**
     * ldap的url
     */
    private String url;
    /**
     * basedn
     */
    private String basedn;

    private String userName;

    private String password;


}
