package com.geekerit.springbootalipay.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
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
     * @param zoneId 唯一时区ID
     * @return      指定时区的当前时间
     */
    public static LocalDateTime getNowTimeWithZone(ZoneId zoneId){
        LocalDateTime now = LocalDateTime.now(zoneId);
        return now;
    }

    /**
     * 获取东八区zoneId
     * @return 东八区zoneId
     */
    public static ZoneId getZoneId(){
        ZoneOffset zoneOffset = ZoneOffset.ofHours(8);
        return ZoneId.ofOffset("GMT", zoneOffset);
    }

    public static void main(String[] args) throws Exception {
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
         *  传入正序数时，找到一个月的第一个该星期日期，然后累加（正序数-1）个星期，得到日期值
         *  传入零序数时为找到上个月的最后一个该星期
         *  传入负序数时，找到一个月的最后一个该星期日期，然后减去（负序数绝对值-1）个星期，得到的日期值
         */
        TemporalAdjuster temporalAdjuster1 = TemporalAdjusters.dayOfWeekInMonth(1, dayOfWeek);
        TemporalAdjuster temporalAdjuster2 = TemporalAdjusters.dayOfWeekInMonth(0, dayOfWeek);
        TemporalAdjuster temporalAdjuster3 = TemporalAdjusters.dayOfWeekInMonth(-2, dayOfWeek);

        LocalDateTime with1 = nowTimeWithZone.with(temporalAdjuster1);
        LocalDateTime with2 = nowTimeWithZone.with(temporalAdjuster2);
        LocalDateTime with3 = nowTimeWithZone.with(temporalAdjuster3);
        logger.info("正序数的日期为{}",with1);
        logger.info("零序数的日期为{}",with2);
        logger.info("负序数的日期为{}",with3);

        // 调整日期到本月的第一天，时间为当前时间
        TemporalAdjuster temporalAdjuster = TemporalAdjusters.firstDayOfMonth();
        LocalDateTime with = nowTimeWithZone.with(temporalAdjuster);
        logger.info("本月第一天的日期为{}",with);
        // 调整日期到下个月的第一天
        TemporalAdjuster temporalAdjusterNextMonth = TemporalAdjusters.firstDayOfNextMonth();
        LocalDateTime withNext = nowTimeWithZone.with(temporalAdjusterNextMonth);
        logger.info("下月第一天的日期为{}",withNext);
        // 调整日期到下一年的第一天
        TemporalAdjuster temporalAdjusterNextYear = TemporalAdjusters.firstDayOfNextYear();
        LocalDateTime withNextYear = nowTimeWithZone.with(temporalAdjusterNextYear);
        logger.info("下年第一天的日期为{}",withNextYear);

        // 本年第一天
        TemporalAdjuster temporalAdjusterFirstDayOfYears = TemporalAdjusters.firstDayOfYear();
        LocalDateTime withFirstDays = LocalDateTime.now().with(temporalAdjusterFirstDayOfYears);
        logger.info("本年第一天的日期为{}",withFirstDays);

        // 本月第一次出现该星期的日期
        TemporalAdjuster temporalAdjusterFirstInMonth = TemporalAdjusters.firstInMonth(dayOfWeek);
        LocalDateTime withFirstMonth = nowTimeWithZone.with(temporalAdjusterFirstInMonth);
        logger.info("本月第一次出现该星期的日期为{}",withFirstMonth);

        // 本月的最后一天
        TemporalAdjuster temporalAdjusterLastDayOfMonth = TemporalAdjusters.lastDayOfMonth();
        LocalDateTime withLastDayOfMonth = nowTimeWithZone.with(temporalAdjusterLastDayOfMonth);
        logger.info("本月最后一天的日期为{}",withLastDayOfMonth);

        // 本年的最后一天
        TemporalAdjuster temporalAdjusterLastDayOfYear = TemporalAdjusters.lastDayOfMonth();
        LocalDateTime withLastDayOfYear = nowTimeWithZone.with(temporalAdjusterLastDayOfYear);
        logger.info("本年最后一天的日期为{}",withLastDayOfYear);

        // 本月最后一次出现该星期的日期
        TemporalAdjuster temporalAdjusterLastInMonth = TemporalAdjusters.lastInMonth(dayOfWeek);
        LocalDateTime withLastMonth = nowTimeWithZone.with(temporalAdjusterLastInMonth);
        logger.info("本月最后一次出现该星期的日期为{}",withLastMonth);

        // 下周的该星期的日期
        TemporalAdjuster next = TemporalAdjusters.next(dayOfWeek);
        LocalDateTime withOfNext = nowTimeWithZone.with(next);
        logger.info("下周的该星期的日期为{}",withOfNext);

        /*
         *
         * 日期已经超过该星期的情况下返回下周该星期的日期，否则返回本周的该星期的日期
         *  eg: 当前日期(12月25日--星期二) 传入参数星期一，星期二返回下周的该星期时的日期
         *      传入其他参数返回本周的该星期的日期
         *
         */
        TemporalAdjuster temporalAdjusterNextOrSame = TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY);
        LocalDateTime withNextOrSame = nowTimeWithZone.with(temporalAdjusterNextOrSame);
        logger.info("日期已经超过该星期的情况下返回下周该星期的日期，否则返回本周的该星期的日期{}",withNextOrSame);

        // 上周该星期的日期
        TemporalAdjuster previous = TemporalAdjusters.previous(dayOfWeek);
        LocalDateTime withPrevious = nowTimeWithZone.with(previous);
        logger.info("sdasd{}",withPrevious);


        /*
         * 与nextOrSame()方法相反
         * 日期已经超过该星期或者正在该星期的情况下返回本周该星期的日期，否则返回上周的该星期的日期
         *  eg：当前日期(12月25日--星期二) 传入参数星期一，星期二返回本周的该星期时的日期
         *      传入其他参数返回上周的该星期的日期
         *
         */
        TemporalAdjuster temporalAdjusterPreviousOrSame = TemporalAdjusters.previousOrSame(dayOfWeek);
        TemporalAdjuster temporalAdjusterPreviousOrSameSun = TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY);
        LocalDateTime withPreviousOrSame = nowTimeWithZone.with(temporalAdjusterPreviousOrSame);
        LocalDateTime withPreviousOrSameSun = nowTimeWithZone.with(temporalAdjusterPreviousOrSameSun);
        logger.info("上周出现{}",withPreviousOrSame);
        logger.info("上周出现{}",withPreviousOrSameSun);

        LocalDateTime withYearAndOne = nowTimeWithZone.with(ChronoField.YEAR_OF_ERA, 2019L);
        LocalDateTime withMonthAndOne = nowTimeWithZone.with(ChronoField.MONTH_OF_YEAR, 2L);
        logger.info("设置年份结果为{}",withYearAndOne);
        logger.info("设置月份结果为{}",withMonthAndOne);

        // 设置年份
        LocalDateTime localDateTime = nowTimeWithZone.withYear(2019);
        // 设置月份
        LocalDateTime localDateTimeMonth = nowTimeWithZone.withMonth(11);
        // 获取年份的第N天日期
        LocalDateTime localDateTime1 = LocalDateTime.of(2018,2,28,12,12).withDayOfYear(60);
        // 设置当前月份的日期，注意可能抛出异常（1-28,1-29,1-30,1-31）
        LocalDateTime localDateTime2 = LocalDateTime.of(2018,2,28,12,12).withDayOfMonth(27);
        logger.info("设置年份结果为{}",localDateTime);
        logger.info("设置月份结果为{}",localDateTimeMonth);
        logger.info("设置月份结果为{}",localDateTime1);
        logger.info("设置月份结果为{}",localDateTime2);

        // 设置小时0-23
        LocalDateTime localDateTimeWithHour = nowTimeWithZone.withHour(23);
        // 设置分钟数0-59
        LocalDateTime localDateTimeWithMinute = nowTimeWithZone.withMinute(59);
        // 设置秒数
        LocalDateTime localDateTimeWithSecond= nowTimeWithZone.withSecond(3);
        logger.info("设置小时数{}",localDateTimeWithHour);
        logger.info("设置分钟数{}",localDateTimeWithMinute);
        logger.info("设置秒数{}",localDateTimeWithSecond);



    }

}
