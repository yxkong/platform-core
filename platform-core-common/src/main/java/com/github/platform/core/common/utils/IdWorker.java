package com.github.platform.core.common.utils;

import com.github.platform.core.standard.util.LocalDateTimeUtil;

import java.util.List;

/**
 * 分布式Id生成器，做到了相对有序
 * @author yxkong
 * @Description: Base64编码解码
 */
public class IdWorker {

    // ==============================Fields===========================================
    /**
     * 开始时间截 (2021-11-11)，设置一个时间戳会让id值从比较小的值开始
     */
    private final long twepoch = 1636617732604L;

    /**
     * 工作机器ID(0~31)
     */
    private long workerId;

    /**
     * 毫秒内序列(0~4095)
     */
    private long sequence = 0L;
    /**
     * 内存自增序列占4位，最大可以999，毫秒级999能满足99.9999%的需求了
     */
    private long maxSequence = 999L;

    /**
     * 上次生成ID的时间截
     */
    private long lastTimestamp = -1L;
    private  Object lock = new Object();

    private long lenCritical2 = 10L;
    private long lenCritical3 = 100L;
    private long lenCritical4 = 1000L;
    private String fill1Zero = "0";
    private String fill2Zero = "00";


    // ==============================Constructors=====================================

    /**
     * 构造函数
     *
     * @throws Exception
     */
    public IdWorker() throws Exception {
        // 获取当前服务器的机器id start
        String cluster = System.getProperty("cluster");
        String[] strings = cluster.split(",");
        String ip = "";
        ip = IPUtil.getLocalHostAddress();
        int index = 0;
        for (int j = 0; j < strings.length; j++) {
            if (ip.equals(strings[j])) {
                index = j + 1;
            }
        }
        // 获取当前服务器的机器id end

        this.workerId = index;
    }

    /**
     * 构造函数
     *
     * @throws Exception
     */
    public IdWorker(List<String> cluster, int skip) throws Exception {

        String ip =  IPUtil.getLocalHostAddress();
        int index = 0;
        for (int j = 0; j < cluster.size(); j++) {
            if (ip.equals(cluster.get(j))) {
                index = j + skip;
            }
        }
        // 获取当前服务器的机器id end

        this.workerId = index;
    }

    // ==============================Methods==========================================

    /**
     * 获得下一个ID (该方法是线程安全的)
     *   （当前时间戳-开始时间戳，17位）+3位的机器位+3位的内存序列
     *   <p>ps:17位是在2299年前，如果超过了2299年就会溢出到18位</p>
     *   <p>算出来的id，在单台机器内相对有序，如果一毫米秒整体不超过100条数据</p>
     * @return SnowflakeId
     */
    public synchronized long nextId() {
        synchronized (lock){
            before();
            StringBuilder builder = new StringBuilder();
            builder.append(workerId).append(lastTimestamp - twepoch);
            after(builder);
            return Long.parseLong(builder.toString());
        }
    }
    /**
     * 分布式业务号生成
     * 规则：毫秒级时间戳+机器位+3位内存序列
     * 如： 20220630152139123+1+001
     * @return
     */
    public String bizNo(){
        synchronized (lock){
            before();
            StringBuilder builder = new StringBuilder();
            builder.append(LocalDateTimeUtil.dateTimeCompact()).append(workerId);
            after(builder);
            return builder.toString();
        }
    }
    private void before(){
        long timestamp = timeGen();
        // 如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(String.format(
                    "Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }
        // 如果是同一时间生成的，则进行毫秒内序列
        if (lastTimestamp == timestamp) {
            sequence = sequence + 1;
            if (sequence > maxSequence) {
                // 毫秒内序列溢出,重置为0 ，等下一毫秒
                sequence = 0;
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else { // 时间戳改变，毫秒内序列重置
            sequence = 0L;
        }

        // 上次生成ID的时间截
        lastTimestamp = timestamp;
    }
    private void after(StringBuilder builder){
        // 不用String.format("%03d", sequence) 是为了更好的性能
        if (sequence < lenCritical2) {
            builder.append(fill2Zero).append(sequence);
        } else if (sequence < lenCritical3) {
            builder.append(fill1Zero).append(sequence);
        } else if (sequence < lenCritical4) {
            builder.append(sequence);
        }
    }
    /**
     * 阻塞到下一个毫秒，直到获得新的时间戳
     *
     * @param lastTimestamp 上次生成ID的时间截
     * @return 当前时间戳
     */
    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    /**
     * 返回以毫秒为单位的当前时间
     *
     * @return 当前时间(毫秒)
     */
    protected long timeGen() {
        return System.currentTimeMillis();
    }

    // ==============================test=============================================

    /**
     * 测试
     */
//    public static void main(String[] args) {
//
//        IdWorker idWorker = null;
//        try {
//            idWorker = new IdWorker();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        for (int i = 0; i < 10000; i++) {
//            //正常执的业务应该超过System.out.println的执行时间，在使用
//            long id = idWorker.nextId();
//            System.out.println(id);
//        }
//    }


}
