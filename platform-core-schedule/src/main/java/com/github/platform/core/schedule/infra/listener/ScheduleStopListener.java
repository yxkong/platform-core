//package com.github.platform.core.schedule.infra.listener;
//
//import com.github.platform.core.persistence.mapper.schedule.SysJobMapper;
//import com.github.platform.core.schedule.domain.common.entity.SysJobBase;
//import com.github.platform.core.schedule.infra.configuration.ScheduleManager;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.NoSuchBeanDefinitionException;
//import org.springframework.context.ApplicationListener;
//import org.springframework.context.event.ContextClosedEvent;
//import com.github.platform.core.common.utils.CollectionUtil;
//
//import java.util.List;
//
///**
// * 任务关闭监听,数据库不需要
// * @author: yxkong
// * @date: 2023/3/6 3:14 PM
// * @version: 1.0
// */
//@Slf4j
//public class ScheduleStopListener implements ApplicationListener<ContextClosedEvent> {
//    private SysJobMapper sysJobMapper;
//    private  ScheduleManager scheduleManager;
//    private  Boolean isCluster;
//
//    public ScheduleStopListener(SysJobMapper sysJobMapper, ScheduleManager scheduleManager, Boolean isCluster) {
//        this.sysJobMapper = sysJobMapper;
//        this.scheduleManager = scheduleManager;
//        this.isCluster = isCluster;
//    }
//
//    @Override
//    public void onApplicationEvent(ContextClosedEvent event) {
//        try {
//            if (isCluster){
//                return;
//            }
//            if( log.isWarnEnabled()){
//                log.warn("定时任务关闭中");
//            }
//            List<SysJobBase> list = sysJobMapper.findListBy(null);
//            if (CollectionUtil.isEmpty(list)){
//                if (log.isWarnEnabled()){
//                    log.warn("未配置定时任务");
//                }
//                return ;
//            }
//            list.forEach(s ->{
//                try {
//                    scheduleManager.pauseJob(s.getBeanName());
//                } catch (Exception e) {
//                    log.error("job name:{}  beanName:{} 加载异常",s.getName(),s.getBeanName(),e);
//                }
//            });
//        }catch (NoSuchBeanDefinitionException e){
//            //没开启不处理
//            log.error("scheduleJob not enabled",e);
//        } catch (Exception e) {
//            log.error("start scheduleJob is error",e);
//        }
//    }
//}
