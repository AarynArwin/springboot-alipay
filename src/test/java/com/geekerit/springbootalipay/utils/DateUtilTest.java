package com.geekerit.springbootalipay.utils;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class DateUtilTest {
    private static final Logger logger = LoggerFactory.getLogger(DateUtilTest.class);

    @Test
    void getNowWithDate() {
        logger.info("当前日期为{}", LocalDate.now());
    }

    @Test
    void getNowWithTime() {
        logger.info("当前时间为{}", LocalDateTime.now());
    }

    @Test
    void formatNowTime() {
        logger.info("格式化当前时间为{}",formatTime(LocalDateTime.now()));
    }

    @Test
    String formatTime(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String format = formatter.format(localDateTime);
        logger.info("格式化后时间为{}",format);
        return format;
    }
}