package com.github.platform.core.sys.domain.constant;

/**
 * 默认缓存key,
 * ps: 尽可能压缩key的长度，减少redis的内存空间使用
 * @author: yxkong
 * @date: 2023/4/20 5:11 PM
 * @version: 1.0
 */
public interface SysCacheConstant {
    /**字典类型前缀*/
    String DICT_TYPE_PREFIX = "p:s:dt";
    /**字典前缀*/
    String DICT_PREFIX = "p:s:dc";
    /**部门前缀*/
    String DEPT_PREFIX = "p:s:d";
    /**级联前缀*/
    String CASCADE_PREFIX = "p:s:cc";
    /**配置前缀*/
    String CONFIG_PREFIX = "p:s:c";
    /**角色前缀*/
    String ROLE_PREFIX = "p:s:r";
    /**token缓存前缀*/
    String TOKEN_CACHE_PREFIX = "p:s:tc";
    /**用户配置前缀*/
    String USER_CONFIG_PREFIX = "p:s:uc";
    /**用户前缀*/
    String USER_PREFIX = "p:s:u";
}
