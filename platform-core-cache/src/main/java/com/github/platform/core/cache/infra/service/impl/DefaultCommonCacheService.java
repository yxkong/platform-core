package com.github.platform.core.cache.infra.service.impl;

import com.github.platform.core.cache.domain.entity.ConfigEntity;
import com.github.platform.core.cache.domain.entity.DictEntity;
import com.github.platform.core.cache.infra.configuration.properties.CacheProperties;
import com.github.platform.core.cache.infra.service.ICacheService;
import com.github.platform.core.cache.infra.service.ICommonCacheService;
import com.github.platform.core.common.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 缓存默认实现（这里只有初始化和新增或更新，删除操作，由sys实现）
 * @author: yxkong
 * @date: 2023/2/10 12:09 PM
 * @version: 1.0
 */
@Service
@Slf4j
public class DefaultCommonCacheService implements ICommonCacheService {
    @Resource
    protected CacheProperties properties;
    @Resource
    protected ICacheService cacheService;


    @Override
    public ConfigEntity getConfig(Integer tenantId,String key) {
        String  val = (String) cacheService.hget(getConfigKey(tenantId), key);
        return JsonUtils.fromJson(val, ConfigEntity.class);
    }

    @Override
    public String getConfigVal(Integer tenantId,String key) {
        ConfigEntity config = this.getConfig(tenantId,key);
        if (Objects.nonNull(config)){
            return config.getValue();
        }
        return null;
    }

    @Override
    public List<DictEntity> getDictList(Integer tenantId,String type) {

        List<Object> values = cacheService.hValues(getDictKey(tenantId,type));
        if (Objects.isNull(values)){
            return null;
        }
        List<DictEntity> list = new ArrayList<>();
        values.forEach(item->{
            DictEntity entity = JsonUtils.fromJson((String) item, DictEntity.class);
            if(Objects.isNull(entity.getSort())){
                entity.setSort(1);
            }
            list.add(entity);
        });
        //按照sort正序排列
        return list.stream().sorted(Comparator.comparing(DictEntity::getSort)).collect(Collectors.toList());
    }

    @Override
    public DictEntity getDict(Integer tenantId,String type, String key) {
        Object o = cacheService.hget(getDictKey(tenantId,type), key);
        if(o==null){
            return null;
        }
        return JsonUtils.fromJson((String)o,DictEntity.class);
    }

    @Override
    public String getDictLabel(Integer tenantId,String type, String key) {
        DictEntity dict = this.getDict(tenantId,type,key);
        if (Objects.nonNull(dict)){
            return dict.getLabel();
        }
        return null;
    }
    @Override
    public String getDictKey(Integer tenantId,String type){
        /**暂时租户不添加*/
        return new StringBuilder(properties.getDict()).append(type).toString();
    }

    @Override
    public String getConfigKey(Integer tenantId) {
        /**暂时租户不添加*/
        return new StringBuilder(properties.getConfig()).toString();
    }
}
