package com.github.platform.core.standard.util;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.Objects;

/**
 * 日期工具类
 * @author: yxkong
 * @date: 2022/6/25 8:14 PM
 * @version: 1.0
 */
public class LocalDateUtil {
    /**
     * yyyyMMdd
     */
    public static final DateTimeFormatter COMPACT_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");
    /**
     * yyMMdd
     */
    public static final DateTimeFormatter COMPACT_SHORT_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyMMdd");
    /**
     * yyyyMM
     */
    public static final DateTimeFormatter COMPACT_MONTH_FORMATTER = DateTimeFormatter.ofPattern("yyyyMM");
    /**
     * yyyy-MM-dd
     */
    public static final DateTimeFormatter DEFAULT_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    /**yy-MM-dd*/
    public static final DateTimeFormatter DEFAULT_SHORT_DATE_FORMATTER = DateTimeFormatter.ofPattern("yy-MM-dd");
    /**yyyy-MM*/
    public static final DateTimeFormatter DEFAULT_MONTH_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM");
    /**
     * yyyy/MM/dd
     */
    public static final DateTimeFormatter SLASH_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    /**
     * yy/MM/dd
     */
    public static final DateTimeFormatter SLASH_SHORT_DATE_FORMATTER = DateTimeFormatter.ofPattern("yy/MM/dd");
    /**
     * yyyy/MM
     */
    public static final DateTimeFormatter SLASH_MONTH_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM");


    /**
     * 返回当前的日期
     *
     * @return
     */
    public static LocalDate getCurDate() {
        return LocalDate.now();
    }

    /**
     * yyyy-MM-dd
     * @return
     */
    public static String defaultStr() {
        return getDateStr(DEFAULT_DATE_FORMATTER);
    }
    /**
     * yyyyMMdd
     * @return
     */
    public static String compactStr() {
        return getDateStr(COMPACT_DATE_FORMATTER);
    }
    /**
     * yyyyMMdd
     * @return
     */
    public static String slashStr() {
        return getDateStr(SLASH_DATE_FORMATTER);
    }
    /**
     * 获取 yy-MM-dd 格式的日期
     * @return
     */
    public static String defaultShortStr() {
        return getDateStr(DEFAULT_SHORT_DATE_FORMATTER);
    }
    /**
     * 获取 yyMMdd 格式的日期
     * @return
     */
    public static String compactShortStr() {
        return getDateStr(COMPACT_SHORT_DATE_FORMATTER);
    }
    /**
     * 获取 yy/MM/dd 格式的日期
     * @return
     */
    public static String slashShortStr() {
        return getDateStr(SLASH_SHORT_DATE_FORMATTER);
    }

    /**
     * 获取yyyy-MM 格式的年月
     * @return
     */
    public static String defaultMonth() {
        return getDateStr(DEFAULT_MONTH_FORMATTER);
    }
    /**
     * 获取yyyyMM 格式的年月
     * @return
     */
    public static String compactMonth() {
        return getDateStr(COMPACT_MONTH_FORMATTER);
    }
    /**
     * 获取yyyy/MM 格式的年月
     * @return
     */
    public static String slashMonth() {
        return getDateStr(SLASH_MONTH_FORMATTER);
    }

    /**
     * 获取指定格式的当前日期
     * @param pattern
     * @return
     */
    public static String formatterCurDate(String pattern) {
        return getDateStr(DateTimeFormatter.ofPattern(pattern));
    }
    /**
     * 将date解析成默认格式 yyyy-MM-dd 的字符串
     * @param date
     * @return
     */
    public static String formatDefault(LocalDate date) {
        return date.format(DEFAULT_DATE_FORMATTER);
    }
    /**
     * 将date解析成默认格式 yyyyMMdd 的字符串
     * @param date
     * @return
     */
    public static String formatCompact(LocalDate date) {
        return date.format(COMPACT_DATE_FORMATTER);
    }
    /**
     * 将date解析成默认格式  yyyy/MM/dd 的字符串
     * @param date
     * @return
     */
    public static String formatSlash(LocalDate date) {
        return date.format(SLASH_DATE_FORMATTER);
    }
    /**
     * 解析默认格式 yyyy-MM-dd 的字符串日期
     * @param dateStr
     * @return
     */
    public static LocalDate parseDefault(String dateStr) {
        return parse(dateStr, DEFAULT_DATE_FORMATTER);
    }
    /**
     * 解析默认格式 yyyyMMdd 的字符串日期
     * @param dateStr
     * @return
     */
    public static LocalDate parseCompact(String dateStr) {
        return parse(dateStr, COMPACT_DATE_FORMATTER);
    }

    /**
     * 解析默认格式 yyyy/MM/dd 的字符串日期
     * @param dateStr
     * @return
     */
    public static LocalDate parseSlash(String dateStr) {
        return parse(dateStr, SLASH_DATE_FORMATTER);
    }
    public static LocalDate parse(String dateStr, String pattern) {
        return parse(dateStr,DateTimeFormatter.ofPattern(pattern));
    }
    public static LocalDate parse(String dateStr, DateTimeFormatter df) {
        return LocalDate.parse(dateStr, df);
    }
    /**
     * 获取几天之前
     * 给 yyyy-MM-dd 格式的当前日期，减少天数
     * @param days
     * @return
     */
    public static String minusDaysDefault(int days) {
        return minusDays(DEFAULT_DATE_FORMATTER, days);
    }
    /**
     * 获取几天之前
     * 给 yyyyMMdd 格式的当前日期，减少天数
     * @param days
     * @return
     */
    public static String minusDaysCompact(int days) {
        return minusDays(COMPACT_DATE_FORMATTER, days);
    }
    /**
     * 获取几天之前
     * 给 yyyy/MM/dd 格式的当前日期，减少天数
     * @param days
     * @return
     */
    public static String minusDaysSlash(int days) {
        return minusDays(SLASH_DATE_FORMATTER, days);
    }

    /**
     * 几天之后
     * 给 yyyy-MM-dd 格式的日期，增加天数
     * @param days
     * @return
     */
    public static String plusDaysDefault(int days) {
        return plusDays(DEFAULT_DATE_FORMATTER, days);
    }
    /**
     * 几天之后
     * 给 yyyy-MM-dd 格式的日期，增加天数
     * @param days
     * @return
     */
    public static String plusDaysCompact(int days) {
        return plusDays(COMPACT_DATE_FORMATTER, days);
    }
    /**
     * 几天之后
     * 给 yyyy/MM/dd 格式的日期，增加天数
     * @param days
     * @return
     */
    public static String plusDaysSlash(int days) {
        return plusDays(SLASH_DATE_FORMATTER, days);
    }

    /**
     * 判断当前日期是否在date之前
     * @param date
     * @return
     */
    public static boolean isBefore(LocalDate date) {
        return LocalDate.now().isBefore(date);
    }
    /**
     * 判断当前日期是否在date之后
     * @param date
     * @return
     */
    public static boolean isAfter(LocalDate date) {
        return LocalDate.now().isAfter(date);
    }
    /**
     * 是否当天
     *
     * @param date
     * @return
     */
    public static boolean isToday(LocalDate date) {
        return LocalDate.now().isEqual(date);
    }

    /**
     * 给指定格式的当前日期，减少指定天数
     * @param formatter
     * @param days
     * @return
     */
    public static String minusDays(DateTimeFormatter formatter, int days) {
        return LocalDate.now().minusDays(days).format(formatter);
    }
    /**
     * 给指定格式的日期，减少指定天数
     * @param localDate 指定时间
     * @param formatter
     * @param days
     * @return
     */
    public static String minusDays(LocalDate localDate,DateTimeFormatter formatter, int days) {
        Objects.requireNonNull(localDate, "操作日期localDate不可为空");
        return localDate.minusDays(days).format(formatter);
    }
    /**
     * 给日期，减少指定天数
     * @param days
     * @return
     */
    public static LocalDate minusDays(LocalDate localDate, int days) {
        Objects.requireNonNull(localDate, "操作日期localDate不可为空");
        return localDate.minusDays(days);
    }
    /**
     * 给日期，添加指定天数
     * @param days
     * @return
     */
    public static LocalDate plusDays(LocalDate localDate, int days) {
        Objects.requireNonNull(localDate, "操作日期localDate不可为空");
        return localDate.plusDays(days);
    }
    /**
     * 给日期，添加指定天数
     * @param formatter
     * @param days
     * @return
     */
    public static String plusDays(LocalDate localDate,DateTimeFormatter formatter, int days) {
        Objects.requireNonNull(localDate, "操作日期localDate不可为空");
        return localDate.plusDays(days).format(formatter);
    }
    /**
     * 给指定格式的当前日期，添加指定天数
     * @param formatter
     * @param days
     * @return
     */
    public static String plusDays(DateTimeFormatter formatter, int days) {
        return LocalDate.now().plusDays(days).format(formatter);
    }
    /**
     * 获取指定格式的日期
     * @param formatter 指定格式
     * @return
     */
    public static String getDateStr(DateTimeFormatter formatter) {
        return LocalDate.now().format(formatter);
    }
    public static String format(LocalDate date, String pattern) {
        return format(date,DateTimeFormatter.ofPattern(pattern));
    }
    public static String format(LocalDate date, DateTimeFormatter formatter) {
        return date.format(formatter);
    }

    /**
     * 日期相隔天数(它错月就只返回天数的差值，不会返回总的天数差)
     * @param startDate
     * @param endDate
     * @return
     */
    public static int periodDays(LocalDate startDate, LocalDate endDate) {
        return Period.between(startDate, endDate).getDays();
    }

    /**
     * 日期相隔总的天数
     * @param startDate
     * @param endDate
     * @return
     */
    public static Long difDays(LocalDate startDate, LocalDate endDate) {
        return ChronoUnit.DAYS.between(startDate, endDate);
    }

    /**
     * 判断当天是否月初
     * @return
     */
    public static boolean isMothFirst(){
        return isMothFirst(LocalDate.now());
    }

    /**
     * 判断指定日期是否月初
     * @param date
     * @return
     */
    public static boolean isMothFirst(LocalDate date){
        return date.with(TemporalAdjusters.firstDayOfMonth()).isEqual(date);
    }

    /**
     * 判断是否是周一
     * @return
     */
    public static boolean isMonday(){
        return LocalDate.now().with(DayOfWeek.MONDAY).isEqual(LocalDate.now());
    }

    /**
     * 判断指定时间是否是周一
     * @param date
     * @return
     */
    public static boolean isMonday(LocalDate date){
        return date.with(DayOfWeek.MONDAY).isEqual(date);
    }

    /**
     * 使用timestampInMillis 中的该方法
     * @param date
     * @return
     */
    @Deprecated
    public static LocalDateTime dateToLocalDateTime(Date date) {

        Instant instant = date.toInstant();

        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone);
    }
    public static LocalDate convertMillisToLocalDate(Long timestampInMillis) {
        if (Objects.isNull(timestampInMillis)){
            return null;
        }
        Instant instant = Instant.ofEpochMilli(timestampInMillis);
        return instant.atZone(ZoneId.systemDefault()).toLocalDate();
    }


}
