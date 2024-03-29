package com.github.platform.core.schedule.infra.util;

import org.quartz.CronExpression;

import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Quartz cron 表达式工具类
 *
 * @author: yxkong
 * @date: 2023/9/5 10:13 AM
 * @version: 1.0
 */
public class CronUtil {
    /**
     * 校验cron表达式是否有效
     * @param cron
     * @return
     */
    public static boolean isValid(String cron){
        return CronExpression.isValidExpression(cron);
    }

    /**
     * 基于cron表达式，获取以后n个执行时间
     * @param cron
     * @param n
     * @return
     */
    public static List<LocalDateTime> getNextTimes(String cron,int n){
        CronExpression cronExpression;
        try {
            cronExpression = new CronExpression(cron);
        } catch (ParseException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        Date now = new Date();
        List<LocalDateTime> nextTimes = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            Date nextTime = cronExpression.getNextValidTimeAfter(now);
            nextTimes.add(toLocalDateTime(nextTime));
            now = nextTime;
        }
        return nextTimes;
    }
    private static LocalDateTime toLocalDateTime(Date date){
        Instant instant = date.toInstant();
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }
}
