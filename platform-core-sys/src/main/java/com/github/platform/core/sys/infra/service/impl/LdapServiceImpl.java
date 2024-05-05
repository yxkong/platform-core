package com.github.platform.core.sys.infra.service.impl;

import com.github.platform.core.common.utils.JsonUtils;
import com.github.platform.core.common.utils.StringUtils;
import com.github.platform.core.standard.util.LocalDateUtil;
import com.github.platform.core.sys.domain.constant.UserChannelEnum;
import com.github.platform.core.sys.domain.model.user.ThirdUserEntity;
import com.github.platform.core.sys.infra.configuration.LdapProperties;
import com.github.platform.core.sys.infra.service.ILdapService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.Control;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import java.util.Hashtable;
import java.util.Objects;

/**
 * ldap登录
 * @author: yxkong
 * @date: 2023/5/26 3:03 PM
 * @version: 1.0
 */
@Service("ldapService")
@Slf4j
public class LdapServiceImpl implements ILdapService {
    @Autowired
    private LdapProperties ldapProperties;

    private LdapContext ctx = null;

    private final Control[] connCtls = null;
    @Override
    public Pair<Boolean,ThirdUserEntity> validate(String username, String password) {
        try {
            this.ldapConnect();
            ThirdUserEntity entity = this.searchDn(username);
            if (Objects.isNull(entity)) {
                return Pair.of(false,null);
            }
            this.reconnect(entity.getRemark(), password);
            if (log.isDebugEnabled()){
                log.debug("LDAP用户：{} 登录验证通过",username);
            }
            entity.setChannel(UserChannelEnum.ldap);
            return  Pair.of(true,entity);
        } catch (NamingException e) {
            log.error("LDAP用户：{}, 密码：{} 登录验证失败",username,password, e);
            return Pair.of(false,null);
        }
    }

    @Override
    public boolean isExist(String username) throws NamingException {
        String filter = "(sAMAccountName=" + username + ")";
        ThirdUserEntity thirdUserEntity = this.searchUserInfo(filter);
        if (Objects.isNull(thirdUserEntity)){
            return false;
        }
        return true;
    }

    /**
     * TODO  这里也需要根据租户路由到不同的配置,需要按租户缓存
     * @throws NamingException
     */
    private void ldapConnect() throws NamingException {
        Hashtable<String, String> env = new Hashtable<>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, LdapProperties.facotry);
        env.put(Context.PROVIDER_URL, ldapProperties.getUrl() + ldapProperties.getBasedn());
        env.put(Context.SECURITY_AUTHENTICATION, LdapProperties.securityAuthentication);
        env.put(Context.SECURITY_PRINCIPAL, ldapProperties.getUserName());
        env.put(Context.SECURITY_CREDENTIALS, ldapProperties.getPassword());
        ctx = new InitialLdapContext(env, connCtls);
    }
    private ThirdUserEntity searchDn(String uid) throws NamingException {
        String filter = "(sAMAccountName=" + uid + ")";
        ThirdUserEntity thirdUserEntity = this.searchUserInfo(filter);
        if (Objects.isNull(thirdUserEntity)){
            return null;
        }
        return thirdUserEntity;
    }
    private void reconnect(String dn, String password) throws NamingException {
        dn += ","+ldapProperties.getBasedn();
        if (log.isDebugEnabled()){
            log.debug("dn is:{}",dn);
        }
        ctx.addToEnvironment(Context.SECURITY_PRINCIPAL, dn);
        ctx.addToEnvironment(Context.SECURITY_CREDENTIALS, password);
        ctx.reconnect(connCtls);
    }
    /**
     * 中文名
     * @param name
     * @return
     * @throws NamingException
     */
    private String searchDnByName(String name) throws NamingException {
        String filter = "(cn=" + name + ")";
        ThirdUserEntity thirdUserEntity = searchUserInfo(filter);
        if (Objects.nonNull(thirdUserEntity)){

            return thirdUserEntity.getUserName();
        }
        log.warn("未根据用户名: {} 找到用户" , name);
        return null;
    }

    @Override
    public ThirdUserEntity searchUserInfo(String filter) throws NamingException {
        if (ctx == null) {
            this.ldapConnect();
        }
        SearchControls constraints = new SearchControls();
        constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);


        NamingEnumeration<SearchResult> en = ctx.search("", filter, constraints);
        if (en == null) {
            log.warn("filter: {} is null" , filter);
        } else if (!en.hasMoreElements()) {
            log.warn("未根据过滤条件{}找到用户", filter);
            return null;
        }
        ThirdUserEntity entity = new ThirdUserEntity();
        while (en != null && en.hasMoreElements()) {
            Object obj = en.nextElement();
            if (!(obj instanceof SearchResult)){
                continue;
            }
            SearchResult sr = (SearchResult) obj;
            if ( sr != null){
                if (log.isDebugEnabled()){
                    log.debug("ldap用户:{}信息:",filter, JsonUtils.toJson(sr));
                }
                entity.setRemark(sr.getName());
                String  displayName = sr.getAttributes().get("displayName").get().toString();
                entity.setNickName(displayName);
                String  name = sr.getAttributes().get("name").get().toString();
                entity.setUserName(name);
                String sAMAccountName = sr.getAttributes().get("sAMAccountName").get().toString();
                entity.setOpenId(sAMAccountName);
                entity.setLoginName(sAMAccountName);
                Attribute mail = sr.getAttributes().get("mail");
                if (Objects.nonNull(mail) && Objects.nonNull(mail.get())){
                    String  email = mail.get().toString();
                    entity.setEmail(StringUtils.trim(email));
                }
                String  whenCreated = sr.getAttributes().get("whenCreated").get().toString();
                entity.setJoinDate(LocalDateUtil.parse(whenCreated,"yyyyMMddHHmmss.SX"));
                String  distinguishedName = sr.getAttributes().get("distinguishedName").get().toString();
                entity.setUserType(1);
                if (distinguishedName.contains("OU=外包")) {
                    entity.setUserType(0);
                }
            }
        }
        return entity;
    }

}
