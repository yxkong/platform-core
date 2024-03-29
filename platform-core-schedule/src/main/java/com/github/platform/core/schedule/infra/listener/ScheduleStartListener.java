package com.github.platform.core.schedule.infra.listener;

import com.github.platform.core.common.utils.CollectionUtil;
import com.github.platform.core.persistence.mapper.schedule.SysJobMapper;
import com.github.platform.core.schedule.domain.common.entity.SysJobBase;
import com.github.platform.core.schedule.infra.configuration.ScheduleManager;
import com.github.platform.core.standard.constant.StatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

import java.util.List;

/**
 * 项目启动监听
 * @author: yxkong
 * @date: 2023/3/6 5:11 PM
 * @version: 1.0
 */
@Slf4j
public class ScheduleStartListener implements ApplicationListener<ApplicationReadyEvent> {

    private SysJobMapper sysJobMapper;
    private  ScheduleManager scheduleManager;

    public ScheduleStartListener(SysJobMapper sysJobMapper, ScheduleManager scheduleManager) {
        this.sysJobMapper = sysJobMapper;
        this.scheduleManager = scheduleManager;
    }
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if (log.isWarnEnabled()){
            log.warn("项目启动，加载定时任务");
        }
        try {
            //查询所有状态为1（启用的任务）
            List<SysJobBase> list = sysJobMapper.findListBy(null);
            if (CollectionUtil.isEmpty(list)){
                if (log.isWarnEnabled()){
                    log.warn("未配置定时任务");
                }
                return ;
            }
            list.forEach(s ->{
                try {
                    scheduleManager.addOrUpdateJob(s);
                } catch (Exception e) {
                    log.error("job name:{}  handlerName:{} 加载异常",s.getName(),s.getBeanName(),e);
                }
            });
        }catch (NoSuchBeanDefinitionException e){
            //没开启不处理
            log.error("scheduleJob not enabled",e);
        } catch (Exception e) {
            log.error("start scheduleJob is error",e);
        }
    }
}
