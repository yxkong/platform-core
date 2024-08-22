package com.github.platform.core.monitor.infra.websocket;

import com.github.platform.core.cache.domain.constant.CacheConstant;
import com.github.platform.core.cache.infra.service.ICacheService;
import com.github.platform.core.monitor.infra.websocket.constant.WsConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * session管理
 * @author: yxkong
 * @date: 2023/6/9 10:46 AM
 * @version: 1.0
 */
@Component
@Slf4j
public class WebSocketSessionManager {

    @Resource(name = "stringRedisTemplate")
    private StringRedisTemplate stringRedisTemplate;
    /**
     * 用来存放token对应的session对象
     */
    private static Map<String, WebSocketSession> sessionMap = new ConcurrentHashMap<>();

    @Resource
    private ICacheService cacheService;

    public void add(String key,WebSocketSession session){
        sessionMap.put(key,session);
        long max = System.currentTimeMillis();
        long min = max- WsConstant.retainMins;
        cacheService.zAdd(CacheConstant.onlineUsers,key,max);
        long l = WsConstant.counter.incrementAndGet();
        //重新初始化计数器
        if (l >= WsConstant.resetCounter){
            WsConstant.counter = new AtomicLong(0);
        }
        //每多少周期，清理一次，重置以后也清理一次
        if (l % WsConstant.period == 0 || l == 0){
            //默认清除30分钟之前的
            cacheService.zRemRangeByScore(CacheConstant.onlineUsers,0,min);
        }
        if (log.isDebugEnabled()){
            log.info("有新窗口建立链接，key为：{} ,当前在线人数为：{}",key,getOnlineCount(min,max));
        }
    }
    /**
     * 删除 session,会返回删除的 session
     *
     * @param key
     * @return
     */
    public WebSocketSession remove(String key) {
        /**从内存中移除*/
        WebSocketSession session = sessionMap.remove(key);
        cacheService.zRem(CacheConstant.onlineUsers,key);
        //TODO 发送广播，清除其他机器上残留的session
        if (log.isDebugEnabled()){
            log.info("链接断开，key为：{} ,当前在线人数为：{}",key,getOnlineCount());
        }
        return session;
    }

    /**
     * 删除并同步关闭连接
     *
     * @param key
     */
    public void removeAndClose(String key) {
        WebSocketSession session = remove(key);
        if (session != null) {
            try {
                // 关闭连接
                session.close();
            } catch (IOException e) {
                log.error("关闭key:{} ,session异常",key,e);
            }
        }
    }

    /**
     * 根据token获得 session
     * @param key
     * @return
     */
    public WebSocketSession get(String key) {
        return sessionMap.get(key);
    }


    public long getOnlineCount(){
        return getOnlineCount(null,null);
    }
    /**
     * 获得30分钟内在线人数
     * @param
     * @return
     */
    public long getOnlineCount(Long min,Long max) {
        if (max == null){
            max = System.currentTimeMillis();
        }
        if (min == null){
            min = max- WsConstant.retainMins;
        }
        return cacheService.zCount(CacheConstant.onlineUsers, min,max);
    }

    /**
     * 批量发送消息
     * @param message
     */
    public void sendAll(String message){
        sessionMap.forEach((key,session)->{
            try {
                session.sendMessage(new TextMessage(message));
            } catch (IOException e) {
                log.error("给用户 key：{},发送消息失败",key,e);
            }
        });
    }
    public boolean sendToUser(String key,String message) throws IOException {
        WebSocketSession session = get(key);
        if (null != session){
            //在本机，发送消息
            session.sendMessage(new TextMessage(message));
            return true;
        }
        return false;
    }

}
