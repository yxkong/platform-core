package com.github.platform.core.cache.infra.service;

import com.github.platform.core.cache.domain.entity.ConfigEntity;
import com.github.platform.core.cache.domain.entity.DictEntity;
import com.github.platform.core.common.utils.JsonUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 通用缓存
 * @author: yxkong
 * @date: 2023/2/10 11:29 AM
 * @version: 1.0
 */
public interface ICommonCacheService {


    /**
     * 获取config的实体
     * @param key
     * @return
     */
    ConfigEntity getConfig(Integer tenantId,String key);

    /**
     * 获取config的value值
     * @param key
     * @return
     */
    String getConfigVal(Integer tenantId,String key);



    /**
     * 根据字典类型获取字典列表
     * @param type
     * @return
     */
    List<DictEntity> getDictList(Integer tenantId,String type);

    /**
     * 根据字典类型和键值 获取对应的字典明细
     * @param type
     * @param key
     * @return
     */
    DictEntity getDict(Integer tenantId,String type,String key);

    /**
     * 根据字典类型和value 获取字典的label
     * @param type
     * @param key
     * @return
     */
    String getDictLabel(Integer tenantId,String type, String key);

    /**
     * 获取字典key
     * @param type
     * @return
     */
    String getDictKey(Integer tenantId,String type);

    /**
     * 获取config 的key
     * @param tenant
     * @return
     */
    String getConfigKey(Integer tenantId);

    /**
     * 转map
     * @param list
     * @return
     */
    default Map<String,String> getConfigMap(List<ConfigEntity> list){
//        Map<String, ConfigEntity> map = list.stream().collect(Collectors.toMap(ConfigEntity::getKey, config -> JsonUtils.toJson(config), (k1, k2) -> k2));
        Map<String,String> map = new HashMap<>();
        list.stream().forEach(l -> {
            map.put(l.getKey(),JsonUtils.toJson(l));
        });
        return map;
    }
    /**
     * 转map
     * @param list
     * @return
     */
    default Map<String,String> getDictMap(List<DictEntity> list){
        Map<String,String> map = new HashMap<>();
        list.stream().forEach(l -> {
            map.put(l.getKey(),JsonUtils.toJson(l));
        });
        return map;
    }

}
