package com.github.platform.core.standard.util;

import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.util.Date;
import java.util.Objects;

/**
 * 日期时间Utils
 *
 * @author yxkong
 */
public class LocalDateTimeUtil {
    /**
     * yyyyMMddHHmmss 格式
     */
    public static final DateTimeFormatter  COMPACT_DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
    /**
     * yyMMddHHmmss
     */
    public static final DateTimeFormatter  COMPACT_SHORT_DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyMMddHHmmss");
    /**
     * HHmmss
     */
    public static final DateTimeFormatter COMPACT_TIME_FORMATTER = DateTimeFormatter.ofPattern("HHmmss");
    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public static final DateTimeFormatter DEFAULT_SHORT_DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter DEFAULT_CN_SHORT_DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH时mm分ss秒");
    /**
     * yyyy-MM-dd
     */
    public static final DateTimeFormatter DEFAULT_SHORT_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    /**
     * yyyy-MM-dd HH:mm:ss.SSS
     */
    public static final DateTimeFormatter DEFAULT_DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    /**
     * HH:mm:ss
     */
    public static final DateTimeFormatter DEFAULT_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
    /**
     * yyyy/MM/dd HH/mm/ss
     */
    public static final DateTimeFormatter SLASH_SHORT_DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd HH/mm/ss");
    /**
     * yy/MM/dd
     */
    public static final DateTimeFormatter SLASH_DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd HH/mm/ss/SSS");
    /**
     * HH/mm/ss
     */
    public static final DateTimeFormatter SLASH_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH/mm/ss");


    /**
     * 返回当前时间
     *
     * @return
     */
    public static LocalTime time() {
        return LocalTime.now();
    }

    public static String timeDefault(LocalTime time) {
        return timeFormat(time,DEFAULT_TIME_FORMATTER);
    }
    public static String compactTime(LocalTime time) {
        return timeFormat(time,COMPACT_TIME_FORMATTER);
    }
    /**
     * 返回当前时间格式: HHmmss
     *
     * @return
     */
    public static String timeCompact() {
        return dateTime(COMPACT_TIME_FORMATTER);
    }
    /**
     * 返回当前时间格式： HH:mm:ss
     *
     * @return
     */
    public static String timeDefault() {
        return timeFormat(DEFAULT_TIME_FORMATTER);
    }
    /**
     * 指定时间格式
     * @param pattern
     * @return
     */
    public static String timeStr(String pattern) {
        return timeFormat(LocalTime.now(),DateTimeFormatter.ofPattern(pattern));
    }

    /**
     *
     * @param formatter
     * @return
     */
    public static String timeFormat(DateTimeFormatter formatter) {
        return timeFormat(LocalTime.now(),formatter);
    }
    public static String timeFormat(LocalTime time, String pattern) {
        return timeFormat(time,DateTimeFormatter.ofPattern(pattern));
    }
    /**
     * 指定时间格式
     * @param formatter
     * @return
     */
    public static String timeFormat(LocalTime time, DateTimeFormatter formatter) {
        if (time == null || formatter == null){
            return  null;
        }
        return time.format(formatter);
    }

    /**
     * 返回当前日期时间
     *
     * @return
     */
    public static LocalDateTime dateTime() {
        return LocalDateTime.now();
    }
    /**
     * 指定返回当前时间格式：
     * @param pattern
     * @return
     */
    public static String dateTime(String pattern) {
        return dateTime(DateTimeFormatter.ofPattern(pattern));
    }


    /**
     * 将时间戳转成字符串时间
     * @param currentTimeMillis
     * @return
     */
    public static String formatTimeStamp(long currentTimeMillis){
        // 将时间戳转换为Instant对象
        Instant instant = Instant.ofEpochMilli(currentTimeMillis);
        // 指定时区，例如使用系统默认时区
        ZoneId zoneId = ZoneId.systemDefault();
        return dateTimeDefault(LocalDateTime.ofInstant(instant, zoneId));
    }

    /**
     * 将时间戳转成字符串yyyy-MM-dd时间
     * @param currentTimeMillis
     * @return
     */
    public static String formatYYYYMMDDTimeStamp(long currentTimeMillis){
        // 将时间戳转换为Instant对象
        Instant instant = Instant.ofEpochMilli(currentTimeMillis);
        // 指定时区，例如使用系统默认时区
        ZoneId zoneId = ZoneId.systemDefault();
        return dateDefault(LocalDateTime.ofInstant(instant, zoneId));
    }
    /**
     * yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String dateTimeDefaultShort() {
        return dateTime( DEFAULT_SHORT_DATETIME_FORMATTER);
    }

    /**
     * 返回当前时间格式：yyyy-MM-dd kk:mm:ss.SSS
     * @return
     */
    public static String dateTimeDefault() {
        return dateTime( DEFAULT_DATETIME_FORMATTER);
    }

    /**
     * 返回当前时间格式: yyyyMMddHHmmssSSS
     * @return
     */
    public static String dateTimeCompact() {
        return dateTime( COMPACT_DATETIME_FORMATTER);
    }
    /**
     * 返回当前时间格式: yyyyMMddHHmmss
     *
     * @return
     */
    public static String dateTimeCompactShort() {
        return dateTime( COMPACT_SHORT_DATETIME_FORMATTER);
    }
    /**
     * 返回当前时间格式: yyyy/MM/dd HH/mm/ss
     *
     * @return
     */
    public static String dateTimeSlashShort() {
        return dateTime( SLASH_SHORT_DATETIME_FORMATTER);
    }


    /**
     * 指定返回当前时间格式：
     * @param formatter
     * @return
     */
    public static String dateTime(DateTimeFormatter formatter) {
        return dateTime(LocalDateTime.now(),formatter);
    }

    /**
     * 格式化LocalDateTime
     * @param datetime
     * @param pattern
     * @return
     */
    public static String dateTime(LocalDateTime datetime, String pattern) {
        return dateTime(datetime,DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 格式化LocalDateTime
     * @param datetime
     * @param formatter
     * @return
     */
    public static String dateTime(LocalDateTime datetime, DateTimeFormatter formatter) {
        if (datetime == null || formatter == null){
            return  null;
        }
        return datetime.format(formatter);
    }

    /**
     * 格式化LocalDateTime为 yyyy-MM-dd HH:mm:ss 的字符串
     * @param time
     * @return
     */
    public static String dateTimeDefault(LocalDateTime time) {
        return dateTime(time,DEFAULT_SHORT_DATETIME_FORMATTER);
    }

    /**
     * 格式化LocalDateTime为 yyyy-MM-dd的字符串
     * @param time
     * @return
     */
    public static String dateDefault(LocalDateTime time) {
        return dateTime(time,DEFAULT_SHORT_DATE_FORMATTER);
    }

    /**
     * 格式化LocalDateTime为yyMMddHHmmss 字符串
     * @param time
     * @return
     */
    public static String dateTimeCompact(LocalDateTime time) {
        return dateTime(time,COMPACT_SHORT_DATETIME_FORMATTER);
    }


    public static LocalDateTime parseCompact(String dateTimeStr) {
        return parseDateTime(dateTimeStr,  COMPACT_DATETIME_FORMATTER);
    }
    public static LocalDateTime parseDefault(String dateTimeStr) {
        return parseDateTime(dateTimeStr, DEFAULT_DATETIME_FORMATTER);
    }
    public static LocalDateTime parseSlash(String dateTimeStr) {
        return parseDateTime(dateTimeStr, SLASH_DATETIME_FORMATTER);
    }
    public static LocalDateTime parseCompactShort(String dateTimeStr) {
        return parseDateTime(dateTimeStr,  COMPACT_SHORT_DATETIME_FORMATTER);
    }
    public static LocalDateTime parseDefaultShort(String dateTimeStr) {
        return parseDateTime(dateTimeStr, DEFAULT_SHORT_DATETIME_FORMATTER);
    }
    public static LocalDateTime parseSlashShort(String dateTimeStr) {
        return parseDateTime(dateTimeStr, SLASH_SHORT_DATETIME_FORMATTER);
    }
    public static LocalDateTime parseDateTime(String dateTimeStr, String pattern) {
        return parseDateTime(dateTimeStr, DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 将指定日期的
     * @param dateStr
     * @param formatter
     * @return
     */
    public static LocalDateTime parseDateTime(String dateStr,DateTimeFormatter formatter){
        if (dateStr == null || formatter == null){
            return  null;
        }
        return LocalDateTime.parse(dateStr,formatter);
    }

    /**
     * 将指定时间'16:17:18'字符串转成 HH:mm:ss 格式
     * @param timeStr 16:17:18
     * @return
     */
    public static LocalTime parseDefaultTime(String timeStr) {
        return parseTime(timeStr, DEFAULT_TIME_FORMATTER);
    }

    /**
     * 将指定时间字符串转成指定格式的的LocalTime
     * @param timeStr 16:17:18
     * @param pattern
     * @return
     */
    public static LocalTime parseTime(String timeStr, String pattern) {
        return parseTime(timeStr,DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 指定时间字符串'16:17:18'，按格式转成LocalTime
     * @param timeStr 16:17:18
     * @param formatter
     * @return
     */
    public static LocalTime parseTime(String timeStr, DateTimeFormatter formatter) {
        if (timeStr == null || formatter == null){
            return  null;
        }
        return LocalTime.parse(timeStr,formatter );
    }

    /**
     * 两个时间间隔的天数<br>
     * 统一命名，这个方法后续废弃，
     * 见{@link  com.github.platform.core.standard.util.LocalDateTimeUtil#durationDays(LocalDateTime, LocalDateTime)}
     * @param startDate
     * @param endDate
     * @return
     */
    @Deprecated
    public static Long betweenDays(LocalDateTime startDate, LocalDateTime endDate) {
        return Duration.between(startDate, endDate).toDays();
    }
  public static Long durationDays(LocalDateTime startDate, LocalDateTime endDate) {
        return Duration.between(startDate, endDate).toDays();
    }

    /**
     * 日期相隔小时
     *
     * @param startInclusive 开始时间
     * @param endExclusive 结束时间
     * @return
     */
    public static long durationHours(Temporal startInclusive, Temporal endExclusive) {
        return Duration.between(startInclusive, endExclusive).toHours();
    }

    /**
     * 日期相隔分钟
     *
     * @param startInclusive 开始时间
     * @param endExclusive 结束时间
     * @return
     */
    public static long durationMinutes(Temporal startInclusive, Temporal endExclusive) {
        return Duration.between(startInclusive, endExclusive).toMinutes();
    }
    /**
     * 日期相隔秒数(startInclusive 到 endExclusive 之间的间隔)
     *
     * @param startInclusive  开始时间
     * @param endExclusive   结束时间
     * @return
     */
    public static long durationSeconds(Temporal startInclusive, Temporal endExclusive) {
        return Duration.between(startInclusive, endExclusive).getSeconds();
    }
    /**
     * 日期相隔毫秒数
     * @param startInclusive 开始时间
     * @param endExclusive 结束时间
     * @return
     */
    public static long durationMillis(Temporal startInclusive, Temporal endExclusive) {
        return Duration.between(startInclusive, endExclusive).toMillis();
    }


    public static String formatCurrentDurationAsString(LocalDateTime startTime){
        return formatDurationAsString(startTime,LocalDateTime.now());
    }
    /**
     * 计算两个时间的时间差
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return  x天x小时x分钟
     */
    public static String formatDurationAsString(LocalDateTime startTime,LocalDateTime endTime){
        if (Objects.isNull(startTime)){
            startTime = LocalDateTime.now();
        }
        if (Objects.isNull(endTime)){
            endTime = LocalDateTime.now();
        }
        // 计算时间差
        Duration duration = Duration.between(startTime, endTime);

        // 获取天、小时和分钟
        long days = duration.toDays();
        long hours = duration.toHours() % 24;
        long minutes = duration.toMinutes() % 60;
        return String.format("%d天 %d小时 %d分钟",days,hours,minutes );
    }

    /**
     *当前时间增加年数
     * @param year
     * @return
     */
    public static LocalDateTime plusYears( long year) {
        return LocalDateTimeUtil.dateTime().plusYears(year);
    }

    /**
     * 当前时间增加月数
     * @param month
     * @return
     */
    public static LocalDateTime plusMonth( long month) {
        return LocalDateTimeUtil.dateTime().plusMonths(month);
    }

    /**
     * 当前时间增加天数
     * @param day
     * @return
     */
    public static LocalDateTime plusDays( long day) {
        return LocalDateTimeUtil.dateTime().plusDays(day);
    }

    /**
     * 指定时间段duration 增加天数
     * @param duration
     * @param day
     * @return
     */
    public static Duration plusDays(Duration duration, long day) {
        return duration.plusDays(day);
    }

    /**
     * 当前时间增加小时数
     * @param hours
     * @return
     */
    public static LocalDateTime plusHours( long hours) {
        return LocalDateTimeUtil.dateTime().plusHours(hours);
    }

    /**
     * 指定Duration增加小时数
     * @param duration 指定时间段
     * @param hours
     * @return
     */
    public static Duration plusHours(Duration duration, long hours) {
        return duration.plusHours(hours);
    }

    /**
     * 增加分钟数
     * @param minutes
     * @return
     */
    public static LocalDateTime plusMinute( long minutes) {
        return LocalDateTimeUtil.dateTime().plusMinutes(minutes);
    }

    /**
     * 当前时间增加秒数
     * @param seconds 秒数
     * @return
     */
    public static LocalDateTime plusSecond( long seconds) {
        return LocalDateTimeUtil.dateTime().plusSeconds(seconds);
    }

    /**
     * 将LocalDateTime 转换为毫秒时间戳
     * @param dateTime
     * @return
     */
    public static Long toEpochMilli(LocalDateTime dateTime) {
        return dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * Date 时间转LocalDateTime
     * @param date
     * @return
     */
    public static LocalDateTime dateToLocalDateTime(Date date) {
        if (date==null){
            return null;
        }
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }
    /**
     * LocalDateTime 转 Date
     * @param localDateTime
     * @return
     */
    public static Date localDateTimeToDate(LocalDateTime localDateTime){
        if (localDateTime == null){
            return null;
        }
        return new Date(localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
    }


    /**
     * 当前时间是否在指定的时间范围内
     * 1, startTime 和 endTime 都为空，则返回true
     * 2，startTime 非空，endTime 为空，只要当前时间在startTime 之后，就返回true
     * 3，startTime 为空，endTime 非空，只要当前时间在endTime 之前，就返回true
     * 4，startTime 非空，endTime 非空，在startTime 和endTime 之间，返回true
     * @param startTime
     * @param endTime
     * @return
     */
    public static boolean isBetween(LocalDateTime startTime, LocalDateTime endTime){
        LocalDateTime now = LocalDateTime.now();
        if (Objects.isNull(startTime) && Objects.isNull(endTime)){
            return true;
        }
        if (Objects.nonNull(startTime) && Objects.isNull(endTime)){
            return now.isAfter(startTime);
        }
        if (Objects.isNull(startTime) && Objects.nonNull(endTime)){
            return now.isBefore(endTime);
        }
        return now.isAfter(startTime) && now.isBefore(endTime);

    }
    /**
     * 将字符串时间 转换为秒时间戳
     * @param dateTime yyyy-mm-dd 的时间
     * @return
     */
    public static Long stryyyymmddToSecond(String dateTime) {
        LocalDate time = LocalDateUtil.parseDefault(dateTime);
        long timestamp = Timestamp.valueOf(time.atStartOfDay()).getTime();
        return timestamp/1000;
    }
    /**
     * 将字符串时间 转换为豪秒时间戳
     * @param dateTime yyyy-mm-dd 的时间
     * @return
     */
    public static Long stryyyymmddToEpochMilli(String dateTime) {
        LocalDate time = LocalDateUtil.parseDefault(dateTime);
        long timestamp = Timestamp.valueOf(time.atStartOfDay()).getTime();
        return timestamp;
    }

}
