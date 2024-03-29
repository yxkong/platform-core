package com.github.platform.core.sys.infra.service.impl.sys;

import com.github.platform.core.cache.domain.entity.ConfigEntity;
import com.github.platform.core.cache.domain.entity.DictEntity;
import com.github.platform.core.cache.infra.service.impl.DefaultCommonCacheService;
import com.github.platform.core.common.utils.JsonUtils;
import com.github.platform.core.sys.infra.service.sys.ISysCommonCacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * sys系统
 * @author: yxkong
 * @date: 2023/4/18 11:00 AM
 * @version: 1.0
 */
@Service("sysCommonCacheService")
@Slf4j
public class SysCommonCacheService extends DefaultCommonCacheService implements ISysCommonCacheService {

    @Override
    public boolean initConfig(Integer tenantId,List<ConfigEntity> list) {
        if (Objects.isNull(list) || list.isEmpty()){
            return false;
        }
        cacheService.hPutAll(getConfigKey(tenantId),getConfigMap(list));
        return true;
    }

    @Override
    public boolean putConfig(Integer tenantId,ConfigEntity config) {
        if(Objects.isNull(config)){
            return false;
        }
        cacheService.hSet(getConfigKey(tenantId),config.getKey(), JsonUtils.toJson(config));
        return true;
    }
    @Override
    public boolean initDict(Integer tenantId,String type, List<DictEntity> list) {
        if (Objects.isNull(list) || list.isEmpty()){
            return false;
        }
        cacheService.hPutAll(getDictKey(tenantId,type),getDictMap(list));
        return true;
    }
    @Override
    public boolean putDict(Integer tenantId,String type, DictEntity dict) {
        cacheService.hSet(getDictKey(tenantId,type),dict.getKey(),JsonUtils.toJson(dict));
        return true;
    }

    @Override
    public Boolean deleteConfig(Integer tenantId,String key) {
        cacheService.hDel(getConfigKey(tenantId),key);
        return true;
    }

    @Override
    public boolean deleteDict(Integer tenantId,String type) {
        cacheService.del(getDictKey(tenantId,type));
        return true;
    }

    @Override
    public boolean deleteDict(Integer tenantId,String type, String key) {
        cacheService.hDel(getDictKey(tenantId,type),key);
        return true;
    }
}
