package com.github.platform.core.auth.configuration.properties;

import com.github.platform.core.cache.domain.constant.CacheConstant;
import com.github.platform.core.common.constant.PropertyConstant;
import com.github.platform.core.common.utils.CollectionUtil;
import com.github.platform.core.standard.constant.SymbolConstant;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 权限解析
 * @author: yxkong
 * @date: 2021/5/12 10:47 上午
 * @version: 1.0
 */
@Data
@Configuration
@ConfigurationProperties(PropertyConstant.DATA_AUTH)
public class AuthProperties {
    /**鉴权模式,sys,api,all*/
    private String mode;
    /**api项目*/
    private Api api = new Api();
    /**后端管理项目*/
    private Sys sys = new Sys();


    @Data
    @SuperBuilder
    @NoArgsConstructor
    public static class Api extends BaseProperties {
        /**登录相关配置*/
        private Login login = new Login(CacheConstant.apiToken, CacheConstant.apiUserTokenMapping);

        /**
         * 根据token获取缓存key
         * @param token
         * @return
         */
        public String getTokenKey(String token){
            return  CacheConstant.apiToken+SymbolConstant.colon+token;
        }

        /**
         * 根据租户和登录名获取缓存key
         * @param tenantId
         * @param loginName
         * @return
         */
        public String getLoginNameKey(Integer tenantId,String loginName){
            return CacheConstant.apiUserTokenMapping+tenantId+SymbolConstant.colon+loginName;
        }
        public Long getExpire(){
            if (Objects.isNull(this.login) || Objects.isNull(this.login.getExpire())){
                return CacheConstant.defaultExpire;
            }
            return login.getExpire();
        }
    }
    @Data
    @SuperBuilder
    @NoArgsConstructor
    public static class Sys extends BaseProperties {
        /**登录相关配置*/
        private Login login = new Login(CacheConstant.sysToken, CacheConstant.sysUserTokenMapping);
        /**默认权限*/
        private List<String> defaultPerms = new ArrayList<>();
        /**内置用户名称（ldap的时候，有用）*/
        private List<String> innerUsers = new ArrayList<>();
        public List<String> getInnerUsers(){
            if (CollectionUtil.isEmpty(this.innerUsers)){
                this.innerUsers.add("admin");
            }
            return this.innerUsers;
        }
        /**
         * 根据token获取缓存key
         * @param token
         * @return
         */
        public String getTokenKey(String token){
            return CacheConstant.sysToken+ SymbolConstant.colon+token;
        }

        /**
         * 根据租户和登录名获取缓存信息
         * @param tenantId
         * @param loginName
         * @return
         */
        public String getLoginNameKey(Integer tenantId,String loginName){
            return CacheConstant.sysUserTokenMapping+tenantId+SymbolConstant.colon+loginName;
        }
        public Long getExpire(){
            if (Objects.isNull(this.login) || Objects.isNull(this.login.getExpire())){
                return CacheConstant.defaultExpire;
            }
            return login.getExpire();
        }
    }
    @Data
    @SuperBuilder
    @NoArgsConstructor
    public static class BaseProperties {
        /**可以直接转发的host*/
        private List<String> hosts = new ArrayList<>();
        /**可以直接转发的url*/
        private List<String> urls = new ArrayList<>();
    }
    /**登录相关配置*/
    @Data
    public static class Login {
        public Login(String token, String userTokenMapping) {
            this.token = token;
            this.userTokenMapping = userTokenMapping;
        }
        /**
         * 登陆过期时间 默认30分钟
         */
        private Long expire = CacheConstant.defaultExpire;
        /**
         * token缓存前缀登录redis 缓存前缀
         */
        private String token;
        /**
         * 用户token映射缓存前缀
         */
        private String userTokenMapping ;

    }
}