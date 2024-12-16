package com.github.platform.core.sys.infra.gateway.impl;//package com.github.platform.core.sys.infra.gateway.impl;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//import com.github.platform.core.common.utils.CollectionUtil;
//
//import javax.annotation.Resource;
//import java.text.SimpleDateFormat;
//import java.time.LocalDateTime;
//import java.util.*;
//import java.util.stream.Collectors;
//
///**
// * 缓存网关
// *
// * @author: yxkong
// * @date: 2023/3/13 2:04 PM
// * @version: 1.0
// */
//@Service
//@Slf4j
//public class CacheGatewayImpl implements CacheGateway {
//    @Resource
//    private ISysCacheLogService cacheLogService;
//    @Resource
//    private SysCacheLogMapper cacheLogMapper;
//
//
//    @Override
//    public Set<String> queryCacheKeys() {
//        Set<String> keys = null;
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Map<String, Object> params = new HashMap<>();
//        params.put("expireTime", format.format(new Date()));
//        List<SysCacheLogDO> list = cacheLogService.findList(params);
//        if (!CollectionUtil.isEmpty(list)) {
//            keys = list.stream().sorted(Comparator.comparing(l -> l.getUpdateTime(), Comparator.reverseOrder())).map(l -> l.getCacheKey()).collect(Collectors.toSet());
//        }
//        return keys;
//    }
//
//    @Override
//    public int delete(String cacheKey) {
//        return cacheLogService.deleteByCacheKey(cacheKey);
//    }
//
//    @Override
//    public int deleteKeys(String... cacheKey) {
//        return cacheLogMapper.deleteByKeys(cacheKey);
//    }
//
//    @Override
//    public int insertCacheLog(String cacheKey, long expireTime) {
//        if (Objects.isNull(cacheKey) || Objects.isNull(expireTime)) {
//            return 0;
//        }
//
//        int row = 0;
//        try {
//            SysCacheLogDO logDO = SysCacheLogDO.builder().cacheKey(cacheKey)
//                    .expireTime(LocalDateTime.now().plusSeconds(expireTime))
//                    .createBy("robot").build();
//            row = cacheLogService.insert(logDO);
//        } catch (Exception e) {
//            //新增失败代表已存在
//            cacheLogMapper.updateByCacheKey(cacheKey, LocalDateTime.now().plusSeconds(expireTime));
//        }
//        return row;
//    }
//
//    @Override
//    public int updateCacheLog(String cacheKey, long expireTime) {
//        return cacheLogMapper.updateByCacheKey(cacheKey, LocalDateTime.now().plusSeconds(expireTime));
//    }
//}
