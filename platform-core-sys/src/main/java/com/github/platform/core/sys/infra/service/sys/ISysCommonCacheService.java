package com.github.platform.core.sys.infra.service.sys;

import com.github.platform.core.cache.domain.entity.ConfigEntity;
import com.github.platform.core.cache.domain.entity.DictEntity;
import com.github.platform.core.cache.infra.service.ICommonCacheService;

import java.util.List;

/**
 * 系统公共缓存操作
 * @author: yxkong
 * @date: 2023/4/2 2:20 下午
 * @version: 1.0
 */
public interface ISysCommonCacheService extends ICommonCacheService {
    /**
     * 初始化config
     * @param tenantId
     * @param list
     * @return
     */
    boolean initConfig(Integer tenantId,List<ConfigEntity> list);

    /**
     * 添加或更新config配置
     * @param config
     * @return
     */
    boolean putConfig(Integer tenantId,ConfigEntity config);
    /**
     * 初始化字典
     * @param type
     * @param list
     * @return
     */
    boolean initDict(Integer tenantId,String type,List<DictEntity> list);

    /**
     * insert或update type对应的单条dict
     * @param type
     * @param dict
     * @return
     */
    boolean putDict(Integer tenantId,String type, DictEntity dict);

    /**
     * 删除config中的某个key
     * @param key
     * @return
     */
    Boolean deleteConfig(Integer tenantId,String key);

    /**
     * 删除一个字典类型
     * @param type
     * @return
     */
    boolean deleteDict(Integer tenantId,String type);
    /**
     * 删除指定字典类型中的某个key
     * @param type
     * @param key
     * @return
     */
    boolean deleteDict(Integer tenantId,String type,String key);

}
