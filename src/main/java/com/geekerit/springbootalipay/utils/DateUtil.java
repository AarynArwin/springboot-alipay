package com.geekerit.springbootalipay.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.Locale;

/**
 * 日期工具类
 * @author Aaryn
 */
public class DateUtil {

    private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);

    /**
     * 生成当前时间的年月日时分秒字符串
     * @return 当前时间的年月日时分秒格式字符串
     */
    public static String nowTimeString(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime now = LocalDateTime.now();

        String format = formatter.format(now);
        return format;
    }

    public static final LocalDateTime MAX_TIME = LocalDateTime.MAX;

    /**
     * 获取当前系统时间
     * @return 默认时区的当前时间
     */
    public static LocalDateTime getNow(){
        return  LocalDateTime.now();
    }

    /**
     * 获取指定时区的当前时间
     * @param zoneId
     * @return
     */
    public static LocalDateTime getNowTimeWithZone(ZoneId zoneId){
        LocalDateTime now = LocalDateTime.now(zoneId);
        return now;
    }

    /**
     * 获取zoneId
     * @return
     */
    public static ZoneId getZoneId(){
        ZoneOffset zoneOffset = ZoneOffset.ofHours(8);
        ZoneId zoneId = ZoneId.ofOffset("GMT", zoneOffset);
        return zoneId;
    }
//    Locale locale = Locale.CHINA;

    public static void main(String[] args) {
        LocalDateTime nowTimeWithZone = getNow();
        LocalDate localDate = nowTimeWithZone.toLocalDate();
        logger.info("当前日期为{}",localDate);
        LocalTime localTime = nowTimeWithZone.toLocalTime();
        logger.info("当前时间为{}",localTime);
        int year = nowTimeWithZone.getYear();
        logger.info("当前年份{}",year);
        // 获取月份值1-12
        int monthValue = nowTimeWithZone.getMonthValue();
        logger.info("当前月份值为{}",monthValue);
        // 获取当前时间月份的枚举值，建议用此方法
        Month month = nowTimeWithZone.getMonth();
        int value = month.getValue();
        logger.info("当前月份为{}，月份的int值为{}",month,value);
        // 获取当前日期的星期字段枚举，建议用此方法
        DayOfWeek dayOfWeek = nowTimeWithZone.getDayOfWeek();
        // 获取月份的int值
        int dayOfMonth = nowTimeWithZone.getDayOfMonth();
        // 获取当前时间在一年中的int值，从1-365（闰年1-366）
        int dayOfYear = nowTimeWithZone.getDayOfYear();
        logger.info("今天是本周的{}，本月的{}日，本年的第{}天",dayOfWeek,dayOfMonth,dayOfYear);
        int hour = nowTimeWithZone.getHour();
        int minute = nowTimeWithZone.getMinute();
        int second = nowTimeWithZone.getSecond();
        int nano = nowTimeWithZone.getNano();
        logger.info("当前时间的小时int值为{},分钟int值{},秒数int值{},纳秒int值{}",hour,minute,second,nano);

        /*
         *  传入正序数时，找到一个月的该星期，然后累加正序数个星期，得到日期值
         *  传入零序数时为找到上个月的最后一个星期
         *
         *
         */
        TemporalAdjuster temporalAdjuster1 = TemporalAdjusters.dayOfWeekInMonth(1, dayOfWeek);
        TemporalAdjuster temporalAdjuster2 = TemporalAdjusters.dayOfWeekInMonth(0, dayOfWeek);
        TemporalAdjuster temporalAdjuster3 = TemporalAdjusters.dayOfWeekInMonth(-2, dayOfWeek);

        LocalDateTime with1 = nowTimeWithZone.with(temporalAdjuster1);
        LocalDateTime with2 = nowTimeWithZone.with(temporalAdjuster2);
        LocalDateTime with3 = nowTimeWithZone.with(temporalAdjuster3);
        logger.info("本月的第一周的日期为{}",with1);
        logger.info("本月的第二周的日期为{}",with2);
        logger.info("本月的第三周的日期为{}",with3);


    }

}
