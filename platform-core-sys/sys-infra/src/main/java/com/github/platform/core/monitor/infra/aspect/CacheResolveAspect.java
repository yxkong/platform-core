package com.github.platform.core.monitor.infra.aspect;//package com.github.platform.core.monitor.infra.aspect;
//
//import com.github.platform.core.cache.domain.constant.CacheNameEnum;
//import com.github.platform.core.common.utils.CollectionUtil;
//import com.github.platform.core.common.utils.StringUtils;
//import com.github.platform.core.log.infra.annotation.CacheResolve;
//import com.github.platform.core.standard.constant.SymbolConstant;
//import lombok.extern.slf4j.Slf4j;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.springframework.cache.annotation.CachePut;
//import org.springframework.cache.annotation.Cacheable;
//import org.springframework.core.annotation.AnnotationUtils;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.expression.EvaluationContext;
//import org.springframework.expression.Expression;
//import org.springframework.expression.spel.standard.SpelExpressionParser;
//import org.springframework.expression.spel.support.StandardEvaluationContext;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//import java.util.Objects;
//
///**
// * 缓存记录切面
// * @author: yxkong
// * @date: 2023/5/5 2:15 PM
// * @version: 1.0
// */
//@Aspect
//@Component
//@Slf4j
//public class CacheResolveAspect {
//    @Resource
//    private RedisTemplate<String, String> redisTemplate;
//    @Pointcut("@annotation(com.github.platform.core.log.infra.annotation.CacheResolve) || @annotation(org.springframework.cache.annotation.Cacheable) || @annotation(org.springframework.cache.annotation.CachePut)")
//    public void pointCut() {
//    }
//    @Around("pointCut()")
//    public Object around(ProceedingJoinPoint jp) throws Throwable {
//        MethodSignature signature = (MethodSignature) jp.getSignature();
//        try {
//            Object proceed = jp.proceed();
//            handlerSpringCache(proceed,jp,signature);
//            handlerCacheResolve(jp,signature);
//            return proceed;
//        } catch (Throwable e) {
//            throw e;
//        }
//    }
//    private void handlerCacheResolve(ProceedingJoinPoint jp, MethodSignature signature){
//        try {
//            CacheResolve cacheResolve = AnnotationUtils.findAnnotation(signature.getMethod(), CacheResolve.class);
//            if (cacheResolve == null){
//                return;
//            }
//            if (log.isWarnEnabled()){
//                log.warn("解析CacheResolve");
//            }
//            String key = cacheResolve.key();
//            String expire = cacheResolve.expire();
//            String type = cacheResolve.type();
//            String command = cacheResolve.command();
//            String resolvedKey = resolveSpelKey(key, jp);
//            Long expireTime = null;
//            if (StringUtils.isNotEmpty(expire)) {
//                String resolvedTime = resolveSpelKey(expire, jp);
//                if (StringUtils.isNotEmpty(resolvedTime)) {
//                    expireTime = Long.parseLong(resolvedTime);
//                } else {
//                    expireTime = CacheNameEnum.C30D.getExpireTime();
//                }
//            }
//            long currentTimeInMillis = System.currentTimeMillis();
//            long score = currentTimeInMillis + expireTime * 1000;
//            String cacheName = key.split(SymbolConstant.colon)[0];
//            if (log.isDebugEnabled()){
//                log.debug("cacheName:{},resolvedKey:{}",cacheName,resolvedKey);
//            }
//            redisTemplate.opsForZSet().add(cacheName, resolvedKey, score);
//            redisTemplate.opsForZSet().removeRangeByScore(cacheName, 0, currentTimeInMillis);
//        } catch (NumberFormatException e) {
//        }
//    }
//    private void handlerSpringCache(Object proceed,ProceedingJoinPoint jp, MethodSignature signature){
//        try {
//            Cacheable cacheable = AnnotationUtils.findAnnotation( signature.getMethod(), Cacheable.class);
//            CachePut cachePut = AnnotationUtils.findAnnotation( signature.getMethod(), CachePut.class);
//            if (cacheable == null && cachePut == null){
//                return;
//            }
//            if (log.isWarnEnabled()){
//                log.warn("解析Cacheable或CachePut");
//            }
//            if (cacheable != null && Objects.isNull(proceed)){
//                return;
//            }
//            String key = null;
//            String[] cacheNames = null;
//            if (cacheable != null){
//                cacheNames = cacheable.cacheNames();
//                key = cacheable.key();
//            }
//            if (cachePut != null){
//                cacheNames = cacheable.cacheNames();
//                key = cacheable.key();
//            }
//            String resolvedKey = resolveSpelKey(key, jp);
//            String cacheName = getCacheName(cacheNames);
//
//            long currentTimeInMillis = System.currentTimeMillis();
//            long score = currentTimeInMillis + CacheNameEnum.getExpireTime(cacheName)*1000;
//            if (log.isDebugEnabled()){
//                log.debug("cacheName:{},resolvedKey:{}",cacheName,resolvedKey);
//            }
//            redisTemplate.opsForZSet().add(cacheName, getKey(cacheName,resolvedKey), score);
//
//            // 移除score小于当前时间戳的所有元素，因为它们已经过期了
//            redisTemplate.opsForZSet().removeRangeByScore(cacheName, 0, currentTimeInMillis);
//        } catch (Exception e) {
//            log.warn("缓存处理异常",e);
//            // 异常不做任何处理
//        }
//    }
//
//    private String getKey(String cacheName,String resolvedKey){
//        return cacheName + SymbolConstant.colon+ resolvedKey;
//    }
//    private String getCacheName(String[] cacheNames){
//        if (CollectionUtil.isNotEmpty(cacheNames)){
//            return cacheNames[0];
//        }
//        return CacheNameEnum.C30M.getCacheName();
//    }
//
//    /**
//     * SPEL表达式解析器
//     * @param key
//     * @param jp
//     * @return
//     */
//    private String resolveSpelKey(String key, ProceedingJoinPoint jp) {
//        if (!key.isEmpty()) {
//            // 创建解析器和上下文
//            SpelExpressionParser parser = new SpelExpressionParser();
//            EvaluationContext context = new StandardEvaluationContext();
//
//            // 添加参数到上下文
//            Object[] args = jp.getArgs();
//            MethodSignature methodSig = (MethodSignature) jp.getSignature();
//            String[] parameterNames = methodSig.getParameterNames();
//            for (int i = 0; i < args.length; i++) {
//                context.setVariable(parameterNames[i], args[i]);
//            }
//            Expression exp = parser.parseExpression(key);
//            return exp.getValue(context, String.class);
//        }
//        return key;
//    }
//}
